package pokedex;

import pokemon.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.UUID;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokedex;
import interfaces.IPokemon;
import interfaces.IType;

public class Pokedex implements IPokedex {
    private final ArrayList<String[]> pokedata = new ArrayList<String[]>();
    private final ArrayList<String[]> capacitedata = new ArrayList<String[]>();

    public Pokedex() {
        String pokeFile = new File("external/listePokemon1G.csv").getAbsolutePath();
        try {
            Scanner sc = new Scanner(new File(pokeFile));
            sc.useDelimiter(",");
            while (sc.hasNext()) {
                pokedata.add(sc.nextLine().split(";"));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        
        String capaciteFile = new File("external/listeCapacites.csv").getAbsolutePath();
        try {
            Scanner sc = new Scanner(new File(capaciteFile));
            sc.useDelimiter(",");
            while (sc.hasNext()) {
                capacitedata.add(sc.nextLine().split(";"));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String[] s : pokedata) {
            sb.append(s[1]);
            sb.append("\n");
        }
        return sb.toString();
    }

    public void searchPokemon(String name) {
        System.out.println("Searching for " + name + "...");
        for (String[] s : pokedata) {
            try {
                if (s[1].equalsIgnoreCase(name)) {
                    System.out.println("Found !");
                    for (int i = 0; i < s.length; i++) {
                        System.out.println(pokedata.get(0)[i] + " : " + s[i]);
                    }
                    return;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        System.out.println("Pokemon not found");
    }

    public void searchPokemon(int id) {
        System.out.println("Searching for " + id + "...");
        for (String[] s : pokedata) {
            try {

                if (Integer.parseInt(s[0]) == (id)) {
                    System.out.println("Found !");
                    for (int i = 0; i < s.length; i++) {
                        System.out.println(pokedata.get(0)[i] + " : " + s[i]);
                    }
                    return;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        System.out.println("Pokemon not found");
    }


    private String getPokemonById(int id) {
        for (String[] s : pokedata) {
            if (Integer.parseInt(s[0]) == (id)) {
                return s[1];
            }
        }
        return null;
    }

    private String[] getEvolution(int id) {
        id++;
        for (String[] s : pokedata) {
            try {


                if (Integer.parseInt(s[0]) == (id)) {
                    if (s[10].equals("1")) {
                        String[] evolutions = new String[2];
                        evolutions[0] = "";
                        evolutions[1] = "";
                        return evolutions;
                    } else {
                        String[] evolutions = new String[2];
                        evolutions[0] = s[1];
                        evolutions[1] = s[10];
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
    }


    @Override
    public IPokemon[] engendreRanch() {
    	ArrayList<String> pokeLvl1=new ArrayList<String>();
    	Pokemon[] ranch=new Pokemon[6];
    	Species esp;
    	for (String[] s : pokedata) {
    		if(s[15].equals("1"))
    			pokeLvl1.add(s[1]);
    	}
    	for(int i=0;i<6;i++) {
    		esp=(Species)getInfo(pokeLvl1.get((int) (Math.random()*(pokeLvl1.size()))));
    		//Capacites au hasard parmi les capacites dispo de l'espece
    		Capacite[] capacitePoke=new Capacite[4];
    		for(int j=0;j<4;j++)
    			capacitePoke[i]=esp.capacities[(int)(Math.random()*(esp.capacities.length))];
    		//ID aleatoire
    		UUID id=UUID.randomUUID();
    		String idStr=""+id;
    		//création du ranch
    		ranch[i]=new Pokemon(esp.nameOfSpecies, esp.nameOfSpecies, esp.types, esp.baseStats,esp.startLevel, esp.evolution, esp.capacities, esp.getBaseExp(),esp.baseStats /* pas bonnes stats*/,capacitePoke, Integer.parseInt(idStr),esp.gainsStat);
    	}
    	return ranch;
    }


    @Override
    public IEspece getInfo(String nomEspece) {
        Stats baseStats;
        Stats gainStats;
        Types[] types=new Types[2];
        ArrayList<Capacite> capacitesArray=new ArrayList<Capacite>();
        
        for (String[] s : pokedata) {
            try {
                if (s[1].equalsIgnoreCase(nomEspece)) {
                	//Stats de base
                    baseStats = new Stats(Integer.parseInt(s[2]),Integer.parseInt(s[3]),Integer.parseInt(s[4]),Integer.parseInt(s[5]),Integer.parseInt(s[6]));
                    //Niveau de base
                    int baseLvl = Integer.parseInt(s[15]);
                    //évolutions
                    TreeMap<Integer, String> evolution = new TreeMap<Integer, String>();
                    int i = Integer.parseInt(s[0]);
                    while (Integer.parseInt(pokedata.get(i)[16]) != 0) {
                        evolution.put(Integer.parseInt(pokedata.get(i)[16]), pokedata.get(i)[17]);
                        i++;
                    }
                    //types
                    types[0]=new Types(s[13]);
                    if (s[14] != " ")
                    	types[1]=new Types(s[14]);
                    //gainStats
                    gainStats = new Stats(Integer.parseInt(s[8]),Integer.parseInt(s[9]),Integer.parseInt(s[10]),Integer.parseInt(s[11]),Integer.parseInt(s[12]));
                    //Capacite
                    for(String[] s1 : capacitedata) {
                    	if(s1[6].equals(types[0].getNom()) || s1[6].equals(types[1].getNom())) {
                    		if(s1[6].equals("Physique"))
                    			capacitesArray.add(new Capacite(s1[0],new Types(s1[6]),new Categorie(s1[5], false),Integer.parseInt(s1[1]),Double.parseDouble(s1[2]),Integer.parseInt(s1[3])));
                    		else 
                    			capacitesArray.add(new Capacite(s1[0],new Types(s1[6]),new Categorie(s1[5], true),Integer.parseInt(s1[1]),Double.parseDouble(s1[2]),Integer.parseInt(s1[3])));
                    	}
                    }
                    //Species accepte uniquement tableau de Capacite, pas ArrayList
                    Capacite[] capacites=new Capacite[capacitesArray.size()];
                    for(int i1=0;i1<capacitesArray.size();i1++)
                    	capacites[i1]=capacitesArray.get(i1);
                    //Création espèce
                    Species esp = new Species(nomEspece, types, baseStats, baseLvl, evolution, capacites,0,gainStats);
                    return esp;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }
    //cases CSV: 2-12 (stats),13-14 (types),15 (baselvl),


    @Override
    public Double getEfficacite(IType attaque, IType defense) {
        HashMap<String, String[]> csv = initializeFromCSV();
        return Double.valueOf(csv.get(attaque.getNom())[EnumCapacite.valueOf(defense.getNom()).getId()]);
    }

    public HashMap<String, String[]> initializeFromCSV() {
        HashMap<String, String[]> lines = new HashMap<>();
        try {

            String file = String.valueOf(new File("external/efficacites.csv"));
            Scanner sc = new Scanner(new File(file));
            sc.useDelimiter(";");
            sc.nextLine();
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split(";");
                lines.put(line[0], line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return lines;
    }

    public enum EnumCapacite {

        Combat(1), Dragon(2), Eau(3), Electrique(4), Feu(5), Glace(6), Insecte(7), Normal(8), Plante(9), Poison(10), Psy(11), Roche(12), Sol(13), Spectre(14), Vol(15);

        private final int id;

        EnumCapacite(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }


    @Override
    public ICapacite getCapacite(String nom) {
        //TODO Necessite de lire le fichier CSV

        return null;
    }


    @Override
    public ICapacite getCapacite(int n) {
        // TODO Necessite de lire le fichier CSV
        return null;
    }
}