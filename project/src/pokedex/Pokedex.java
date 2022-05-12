package pokedex;

import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Pokedex {
    private static final TreeMap<String, String> pokemonURL = new TreeMap<>();

    public Pokedex() {

        initializeFromJSON();

    }

    public void initializeFromJSON() {

//Exercice 1
        JSONObject obj = (JSONObject) getFromAPI("https://pokeapi.co/api/v2/generation/1");
        assert obj != null;
        JSONArray pokemonSpecies = (JSONArray) obj.get("pokemon_species");
        for (Object e : pokemonSpecies) {
            JSONObject jsonObj = (JSONObject) e;
            pokemonURL.put(jsonObj.get("name").toString(), jsonObj.get("url").toString());
        }

    }

    //Exercice 2
    public static JSONObject getFromAPI(String url) {
        try {
            URL hp = new URL(url);
            HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
            hpCon.connect();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(hpCon.getInputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            inputStr = responseStrBuilder.toString();
            streamReader.close();

            return (JSONObject) new JSONParser().parse(inputStr);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void printPoke(String name) {
        for (String key : pokemonURL.keySet()) {
            if (key.equals(name)) {
                String types = "";
                String weight = "";
                String abilities = "";
                String stats = "";
                String sprites = "";
                JSONObject obj = getFromAPI(pokemonURL.get(key));
                assert obj != null;
                for (JSONObject element : returnVarieties(obj)) {
                    if (element.get("name").toString().equals(name)) {
                        types = returnTypes(element);
                        weight = element.get("weight").toString();
                        abilities = returnAbilities(element);
                        stats = returnStats(element);
                        sprites = returnSpriteLink(element);
                    }

                }
                System.out.println("#############################################################");
                System.out.println("#################### GENERAL INFORMATION ####################");
                System.out.println("#############################################################");
                System.out.println(returnBaseInfo(obj));
                JSONObject habitat = (JSONObject) obj.get("habitat");
                System.out.println("Habitat : " + habitat.get("name"));
                JSONObject evoChain = (JSONObject) obj.get("evolution_chain");
                JSONObject evoURL = getFromAPI(evoChain.get("url").toString());
                assert evoURL != null;
                JSONObject chain = (JSONObject) evoURL.get("chain");
                String EVOLIst = returnEvo(chain);
                String EVOLIstFinal = EVOLIst.substring(0, EVOLIst.length() - 1);
                System.out.println("Evolution list : " + EVOLIstFinal);
                System.out.println("Types : " + types.substring(0, types.length() - 2));
                System.out.println("Weight : " + weight);
                System.out.println("Description : " + returnDesc(obj).trim().replaceAll("[\r\n]+", " "));
                System.out.println("#############################################################");
                System.out.println("######################## ABILITIES ##########################");
                System.out.println("#############################################################");
                System.out.println(abilities.trim());
                System.out.println("#############################################################");
                System.out.println("########################### STATS ###########################");
                System.out.println("#############################################################");
                System.out.println(stats.trim());
                System.out.println("#############################################################");
                System.out.println("Sprite : " + sprites + "\n");

                Scanner scanner = new Scanner(System.in);
                System.out.print("Want to show variants and moves ? [y/n] : ");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    for (JSONObject element : returnVarieties(obj)) {
                        System.out.println("#############################################################");
                        System.out.println("############################ " + element.get("name").toString().toUpperCase() + " ########################");
                        System.out.println("#############################################################");
                        System.out.println("Name : " + element.get("name"));
                        System.out.println("Types : " + returnTypes(element).substring(0, types.length() - 2));
                        System.out.println("Weight : " + element.get("weight"));
                        System.out.println("#############################################################");
                        System.out.println("######################### ABILITIES #########################");
                        System.out.println("#############################################################");
                        System.out.println(returnAbilities(element).trim());
                        System.out.println("#############################################################");
                        System.out.println("########################### STATS ###########################");
                        System.out.println("#############################################################");
                        System.out.println(returnStats(element).trim());
                        System.out.println("#############################################################");
                        System.out.println("Sprite : " + returnSpriteLink(element));
                        System.out.println("#############################################################");
                        System.out.println("########################### MOVES ###########################");
                        System.out.println("#############################################################");
                        System.out.println(returnMoves(element).trim());
                    }
                }
                return;
            }
        }
        System.out.println("Sorry, the API doesn't have this pokemon");
    }


    private static String returnEggs(JSONObject jObj) {
        StringBuilder eggBuilder = new StringBuilder();
        JSONArray eggGroups = (JSONArray) jObj.get("egg_groups");
        for (Object element : eggGroups) {
            JSONObject elementObject = (JSONObject) element;
            eggBuilder.append(elementObject.get("name")).append(", ");
        }
        return eggBuilder.toString();

    }

    private static String returnGeneration(JSONObject obj) {
        JSONObject generation = (JSONObject) obj.get("generation");
        return generation.get("name").toString();
    }

    private static String returnGrowthRate(JSONObject obj) {
        JSONObject growthRate = (JSONObject) obj.get("growth_rate");
        return growthRate.get("name").toString();
    }

    private static String returnShapes(JSONObject obj) {
        JSONObject shape = (JSONObject) obj.get("shape");
        return shape.get("name").toString();
    }

    private static String returnPPE(JSONObject jObj) {
        JSONArray PPE = (JSONArray) jObj.get("pal_park_encounters");
        StringBuilder PPEBuilder = new StringBuilder();
        for (Object element : PPE) {
            JSONObject elementObject = (JSONObject) element;
            JSONObject area = (JSONObject) elementObject.get("area");

            PPEBuilder.append(area.get("name")).append(" | ");
        }
        return PPEBuilder.toString().trim().substring(0, PPEBuilder.toString().trim().length() - 2);
    }

    private static String returnEvolve(JSONObject jObj) {
        JSONObject evolveFrom = (JSONObject) jObj.get("evolves_from_species");
        if (evolveFrom == null) {
            return "";
        } else {
            return evolveFrom.get("name").toString();
        }
    }

    private static String returnBaseInfo(JSONObject obj) {
        return "ID : " + obj.get("id") + "\n" +
                "Name : " + obj.get("name") + "\n" +
                "Generation : " + returnGeneration(obj) + "\n" +
                "Base stat : " + obj.get("base_happiness") + "\n" +
                "Gender : " + obj.get("gender_rate") + "\n" +
                "shape : " + returnShapes(obj) + "\n" +
                "Color : " + returnColor(obj) + "\n" +
                "Evolve from : " + returnEvolve(obj) + "\n" +
                "Capture Rate : " + obj.get("capture_rate") + "\n" +
                "Egg Groups : " + returnEggs(obj).substring(0, returnEggs(obj).length() - 2) + "\n" +
                "Gender differences : " + obj.get("has_gender_differences") + "\n" +
                "Growth rate : " + returnGrowthRate(obj) + "\n" +
                "Is baby : " + obj.get("is_baby") + "\n" +
                "Is legendary : " + obj.get("is_legendary") + "\n" +
                "Is mythical : " + obj.get("is_mythical") + "\n" +
                "Height : " + returnVarieties(obj).get(0).get("height") + "\n" +
                "Base Experience : " + returnVarieties(obj).get(0).get("base_experience") + "\n" +
                "Pal Park Encounters : " + returnPPE(obj);
    }

    private static String returnColor(JSONObject obj) {
        JSONObject color = (JSONObject) obj.get("color");
        return color.get("name").toString();
    }

    private static String returnSpriteLink(JSONObject jObj) {
        try {
            JSONObject sprites = (JSONObject) jObj.get("sprites");
            JSONObject versions = (JSONObject) sprites.get("versions");
            JSONObject gen = (JSONObject) versions.get("generation-i");
            for (Object element : gen.keySet()) {
                JSONObject genObject = (JSONObject) gen.get(element);

                return genObject.get("front_default").toString();
            }
        } catch (Exception e) {
            return "Not Found !";
        }

        return "Not Found !";
    }

    private static String returnStats(JSONObject jObj) {
        StringBuilder statsBuilder = new StringBuilder();
        JSONArray statsArray = (JSONArray) jObj.get("stats");
        for (Object element : statsArray) {
            JSONObject elementObject = (JSONObject) element;
            JSONObject statElement = (JSONObject) elementObject.get("stat");
            statsBuilder.append(statElement.get("name")).append(" : ").append(elementObject.get("base_stat")).append(" | Effort : ").append(elementObject.get("effort")).append("\n");
        }
        return statsBuilder.toString().trim();
    }

    private static String returnTypes(JSONObject typeObject) {
        StringBuilder typesBuilder = new StringBuilder();
        JSONArray typesArray = (JSONArray) typeObject.get("types");
        for (Object typeElement : typesArray) {
            JSONObject typeElementObject = (JSONObject) typeElement;
            JSONObject typeObj = (JSONObject) typeElementObject.get("type");
            typesBuilder.append(typeObj.get("name")).append(", ");
        }
        return typesBuilder.toString();
    }

    public static String returnAbilities(JSONObject jObj) {
        StringBuilder abilitiesBuilder = new StringBuilder();
        JSONArray AArray = (JSONArray) jObj.get("abilities");
        for (Object element : AArray) {
            JSONObject elementObject = (JSONObject) element;
            JSONObject abilityObject = (JSONObject) elementObject.get("ability");
            JSONObject nextURLObject = getFromAPI(abilityObject.get("url").toString());
            assert nextURLObject != null;
            JSONArray FTEArray = (JSONArray) nextURLObject.get("flavor_text_entries");
            abilitiesBuilder.append(abilityObject.get("name")).append(" : ");

            for (Object languageElement : FTEArray) {
                JSONObject LEObject = (JSONObject) languageElement;
                JSONObject language = (JSONObject) LEObject.get("language");
                if (language.get("name").toString().equals("en")) {
                    abilitiesBuilder.append(LEObject.get("flavor_text").toString().replaceAll("[\r\n]+", " ")).append(" | ");
                    break;
                }
            }
            JSONArray effect = (JSONArray) nextURLObject.get("effect_entries");
            for (Object EffectElement : effect) {
                JSONObject EEObject = (JSONObject) EffectElement;
                JSONObject language = (JSONObject) EEObject.get("language");
                if (language.get("name").toString().equals("en")) {
                    abilitiesBuilder.append(EEObject.get("effect").toString().replaceAll("[\r\n]+", " ")).append("\n");
                    break;
                }
            }

        }
        return abilitiesBuilder.toString();
    }

    public static ArrayList<JSONObject> returnVarieties(JSONObject jObj) {
        ArrayList<JSONObject> VArray = new ArrayList<>();
        JSONArray varietiesArray = (JSONArray) jObj.get("varieties");
        for (Object element : varietiesArray) {
            JSONObject elementObject = (JSONObject) element;
            JSONObject pokemonObject = (JSONObject) elementObject.get("pokemon");
            VArray.add(getFromAPI(pokemonObject.get("url").toString()));
        }
        return VArray;

    }

    public static String returnDesc(JSONObject jObj) {
        JSONArray FTEArray = (JSONArray) jObj.get("flavor_text_entries");
        StringBuilder FTEBuilder = new StringBuilder();
        for (Object element : FTEArray) {
            JSONObject FTElementObject = (JSONObject) element;
            JSONObject language = (JSONObject) FTElementObject.get("language");
            JSONObject version = (JSONObject) FTElementObject.get("version");
            if (language.get("name").equals("en")) {
                return FTEBuilder.append(FTElementObject.get("flavor_text")).toString();
            }
        }
        return "No description for the selected language";
    }

    private static String returnMoves(JSONObject jObj) {
        StringBuilder movesBuilder = new StringBuilder();
        JSONArray movesArray = (JSONArray) jObj.get("moves");
        for (Object element : movesArray) {
            JSONObject jsonObj = (JSONObject) element;
            JSONObject move = (JSONObject) jsonObj.get("move");
            JSONArray VGDArray = (JSONArray) jsonObj.get("version_group_details");
            JSONObject VDGObject = (JSONObject) VGDArray.get(0);
            JSONObject MLM = (JSONObject) VDGObject.get("move_learn_method");
            JSONObject NextURL = getFromAPI(move.get("url").toString());
            assert NextURL != null;
            JSONArray FTEArray = (JSONArray) NextURL.get("flavor_text_entries");
            movesBuilder.append(move.get("name")).append(" : ").append(MLM.get("name")).append("\n");
            for (Object FTElement : FTEArray) {
                JSONObject FTEElementObject = (JSONObject) FTElement;
                JSONObject language = (JSONObject) FTEElementObject.get("language");
                if (language.get("name").toString().equals("en")) {
                    movesBuilder.append(FTEElementObject.get("flavor_text").toString().replaceAll("[\r\n]+", " ")).append(" | ");
                    break;
                }
            }
            JSONArray effect = (JSONArray) NextURL.get("effect_entries");
            for (Object effectElement : effect) {
                JSONObject EEObject = (JSONObject) effectElement;
                JSONObject language = (JSONObject) EEObject.get("language");
                if (language.get("name").toString().equals("en")) {
                    movesBuilder.append(EEObject.get("effect").toString().replaceAll("[\r\n]+", "")).append("\n--------------------------------\n");
                    break;
                }
            }

        }
        return movesBuilder.toString();
    }

    public static String returnEvo(JSONObject jObj) {
        StringBuilder evoBuilder = new StringBuilder();
        if (jObj.get("evolves_to").toString().equals("[]")) {
            return "";
        }
        JSONArray evolveTo = (JSONArray) jObj.get("evolves_to");
        JSONObject evolveToObj = null;
        for (Object element : evolveTo) {
            evolveToObj = (JSONObject) element;
            JSONObject species = (JSONObject) evolveToObj.get("species");
            evoBuilder.append(species.get("name").toString());
            evoBuilder.append(",");
        }
        assert evolveToObj != null;
        return evoBuilder + returnEvo(evolveToObj);
    }
}