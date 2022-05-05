package pokemon;

import java.util.UUID;

public class Pokemon extends Species {
    public static int ID;
    public static String name;
    public double level;
    public int xp;
    public int[] stats;
    public String[] known_capacities;
    private int[] EV;
    private static int[] DV;

    public Pokemon(String name_of_species, String[] types, int[] base_stats, int start_level, String[] evolution, String[] capacities, double level, int xp, int[] stats, String[] capacities1, String name) {
        super(name_of_species, types, base_stats, start_level, evolution, capacities);
        this.level = level;
        this.xp = xp;
        this.stats = stats;
        this.known_capacities = capacities1;
        Pokemon.name = name;
        UUID uuid = UUID.randomUUID();
        ID = uuid.hashCode();
    }
}
