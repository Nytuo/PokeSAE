package pokemon;

public class Species {
    public String name_of_species;
    public String [] types;
    public int [] base_stats;
    public int start_level = 1;
    public String [] evolution;
    public String [] capacities;
    private int base_xp;
    private int gains_xp;

    public Species(String name_of_species, String[] types, int[] base_stats, int start_level, String[] evolution, String[] capacities) {
        this.name_of_species = name_of_species;
        this.types = types;
        this.base_stats = base_stats;
        this.start_level = start_level;
        this.evolution = evolution;
        this.capacities = capacities;
    }
}
