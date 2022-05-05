package pokemon;

import java.util.ArrayList;

public class Species {
    public String name_of_species;
    public ArrayList<String> types;
    public ArrayList<Integer> base_stats;
    public int start_level = 1;
    public ArrayList<String> evolution;
    public ArrayList<String> capacities;
    private int base_xp;
    private int gains_xp;

    public Species(String name_of_species, ArrayList<String> types, ArrayList<Integer> base_stats, int start_level, ArrayList<String> evolution, ArrayList<String> capacities) {
        this.name_of_species = name_of_species;
        this.types = types;
        this.base_stats = base_stats;
        this.start_level = start_level;
        this.evolution = evolution;
        this.capacities = capacities;
    }
}
