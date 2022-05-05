package pokedex;

import pokemon.Pokemon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Pokedex {
    private ArrayList<String[]> pokedata = new ArrayList<String[]>();

    public Pokedex() {
        String file = new File("listePokemeon1G.csv").getAbsolutePath();
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
                if (s[1].equals(name)) {
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

    public Pokemon[] generatePokemon(int nb) {
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
    }

    private Pokemon createPokemon(int id) {
        for (String[] s : pokedata) {
            try {
                if (Integer.parseInt(s[0]) == (id)) {
                    String[] types = new String[2];
                    types[0] = s[8];
                    types[1] = s[9];
                    int[] stats = new int[5];
                    stats[0] = Integer.parseInt(s[2]);
                    stats[1] = Integer.parseInt(s[3]);
                    stats[2] = Integer.parseInt(s[4]);
                    stats[3] = Integer.parseInt(s[5]);
                    stats[4] = Integer.parseInt(s[6]);
                    String[] evolutions = new String[2];
                    evolutions[0] = Objects.requireNonNull(getEvolution(id))[0];
                    evolutions[1] = Objects.requireNonNull(getEvolution(id))[1];
                    return new Pokemon(s[1], types, stats, Integer.parseInt(s[10]), evolutions, null, 1, 0, null, null, "name");

                }
            } catch (NumberFormatException ignored) {

            }
        }
        return null;
    }

    private boolean isLevelOne(int id) {
        for (String[] s : pokedata) {
            try {
                if (Integer.parseInt(s[0]) == (id)) {
                    if (s[10].equals("1")) {
                        return true;
                    }
                }
            } catch (NumberFormatException ignored) {

            }
        }
        return false;
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
}
