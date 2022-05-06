package pokedex;

import pokemon.Pokemon;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pokedex {
    private HashMap<String, Pokemon> pokedata = new HashMap<String, Pokemon>();

    public Pokedex() {
        String file = new File("listePokemeon1G.csv").getAbsolutePath();
        initializeFromCSV(file);

    }

    public void initializeFromCSV(String file) {
        try {
            Scanner sc = new Scanner(new File(file));
            sc.useDelimiter(",");
            sc.nextLine();
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split(";");

                pokedata.put(line[1], createPokemon(line));

            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int stockLine = 4;
        int byLine = stockLine;
        /* short hashmap by id*/

        int maxLength = 0;
        for (String i : pokedata.keySet()) {
            if (i.length() > maxLength) {
                maxLength = i.length() + 6;
            }

        }


        for (String i : pokedata.keySet()) {
            if (!Objects.equals(i, "Nom")) {
                /*String with the same width */
                if (byLine == 0) {
                    sb.append("\n");
                    byLine = stockLine;
                }
                sb.append(String.format("%1$-" + maxLength + "s", pokedata.get(i).ID + " : " + pokedata.get(i).name_of_species));
                sb.append(" | ");
                byLine--;
            }
        }

        return sb.toString();
    }

    public void searchPokemon(String name) {
        System.out.println("Searching for " + name + "...");
        try {
            System.out.println("Found !");
            for (Pokemon i : pokedata.values()) {
                if (i.name_of_species.equals(name)) {

                System.out.println(i);
                }
            }
            return;

        } catch (NumberFormatException ignored) {
        }

        System.out.println("Pokemon not found");
    }

    public void searchPokemon(int id) {
        System.out.println("Searching for " + id + "...");
        try {

            System.out.println("Found !");
            for (Pokemon i : pokedata.values()) {
                if (id == i.ID) {

                    System.out.println(pokedata.get("id") + " : " + i);
                }
            }
            return;

        } catch (NumberFormatException ignored) {

        }


        System.out.println("Pokemon not found");
    }

/*    public Pokemon[] generatePokemon(int nb) {
        Pokemon[] pokemons = new Pokemon[nb];
        for (int i = 0; i < nb; i++) {
            Random r = new Random();
            int id = r.nextInt(151) + 1;
            if (isLevelOne(id)) {
                pokemons[i] = createPokemon(id);
                if (pokemons[i] == null) i--;
            } else {
                i--;

            }
        }
        return pokemons;
    }*/

    private Pokemon createPokemon(String[] theLine) {
        try {
            ArrayList<String> types = new ArrayList<String>();
            types.add(theLine[8]);
            types.add(theLine[9]);
            ArrayList<Integer> stats = new ArrayList<Integer>();
            stats.add(Integer.parseInt(theLine[2]));
            stats.add(Integer.parseInt(theLine[3]));
            stats.add(Integer.parseInt(theLine[4]));
            stats.add(Integer.parseInt(theLine[5]));
            stats.add(Integer.parseInt(theLine[6]));
            ArrayList<String> evolutions = new ArrayList<String>();
            evolutions.add(theLine[10]);
            evolutions.add(theLine[11]);
            return new Pokemon(theLine[1], types, stats, Integer.parseInt(theLine[10]), evolutions, null, 1, 0, null, null, "name",Integer.parseInt(theLine[0]));


        } catch (NumberFormatException ignored) {


        }
        return null;
    }

/*    private boolean isLevelOne(int id) {
        for (String i : pokedata.keySet()) {

            try {
                if (Integer.parseInt(pokedata.get(i)[0]) == (id)) {
                    if (pokedata.get(i)[11].equals("1")) {
                        return true;
                    }
                }
            } catch (NumberFormatException ignored) {

            }

        }
        return false;
    }*/

/*    private String getPokemonById(int id) {
        for (String i : pokedata.keySet()) {
            if (Integer.parseInt(pokedata.get(i)[0]) == (id)) {

                return pokedata.get(i)[1];
            }
        }
        return null;
    }*/

    /*private String[] getEvolution(int id) {
        id++;

        for (String i : pokedata.keySet()) {
            String[] pokel = pokedata.get(i);
            try {


                if (Integer.parseInt(pokel[0]) == (id)) {
                    if (pokel[10].equals("1")) {
                        String[] evolutions = new String[2];
                        evolutions[0] = "";
                        evolutions[1] = "";
                        return evolutions;
                    } else {
                        String[] evolutions = new String[2];
                        evolutions[0] = pokel[1];
                        evolutions[1] = pokel[10];
                        return evolutions;
                    }

                }
            } catch (NumberFormatException ignored) {

            }

        }
        String[] evolutions = new String[2];

        evolutions[0] = "";
        evolutions[1] = "";
        return evolutions;
    }*/

    /**
     * write to a csv file all the pokemons
     *
     * @param filename the name of the file to read
     */
    void writeLevel1Pokemon(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String i : pokedata.keySet()) {
                String s = pokedata.get(i).name_of_species;
                if (pokedata.get(i).level == 1) {
                    writer.write(s + ",");
                }
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}