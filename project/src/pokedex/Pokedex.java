package pokedex;

import pokemon.Pokemon;
import pokemon.Species;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokedex;
import interfaces.IPokemon;
import interfaces.IType;

public class Pokedex implements IPokedex{
    private ArrayList<String[]> pokedata = new ArrayList<String[]>();

    public Pokedex() {
        String file = new File("external/listePokemon1G.csv").getAbsolutePath();
        try {
            Scanner sc = new Scanner(new File(file));
            sc.useDelimiter(",");
            while (sc.hasNext()) {
                pokedata.add(sc.nextLine().split(";"));
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
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public IEspece getInfo(String nomEspece) {
		TreeMap<String,Integer> baseStats=new TreeMap<String,Integer>();
		ArrayList<String> types=new ArrayList<String>();
		for (String[] s : pokedata) {
            try {
                if (s[1].equalsIgnoreCase(nomEspece)) {
                    for (int i = 2; i < 12; i++) {
                    	baseStats.put(pokedata.get(0)[i], Integer.parseInt(s[i]));
                    }
                    int baseLvl=Integer.parseInt(s[15]);
                    TreeMap<Integer,String> evolution=new TreeMap<Integer,String>();
                    int i=Integer.parseInt(s[0]);
                    ///On crée la TreeMap des évolutions de l'espèce
                    while(Integer.parseInt(pokedata.get(i)[16])!=0) {  
                    	evolution.put(Integer.parseInt(pokedata.get(i)[16]),pokedata.get(i)[17]); 
                    	i++;
                    }
                    types.add(s[13]);
                    if(s[14] != " ")
                    	types.add(s[14]);
                    // TODO Ajouter les capacites de l'espece
                    Species esp=new Species(nomEspece, types, baseStats, baseLvl, evolution, null);
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
	
	public HashMap<String,String[]> initializeFromCSV() {
        HashMap<String,String[]> lines = new HashMap<>();
        try {

            String file = String.valueOf(new File("external/efficacites.csv"));
            Scanner sc = new Scanner(new File(file));
            sc.useDelimiter(";");
            sc.nextLine();
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split(";");
                lines.put(line[0],line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return lines;
    }
	
	public enum EnumCapacite {

        Combat(1),Dragon(2),Eau(3),Electrique(4),Feu(5),Glace(6),Insecte(7),Normal(8),Plante(9),Poison(10),Psy(11),Roche(12),Sol(13),Spectre(14),Vol(15);

        private int id;

        EnumCapacite(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
	
	

	@Override
	public ICapacite getCapacite(String nom) {
		//TODO Necessite Capacite
		Capacite capacite=new Capacite();
		return capacite;
	}



	@Override
	public ICapacite getCapacite(int n) {
		// TODO Necessite Capacite
		Capacite capacite=new Capacite();
		return capacite;
	}
}