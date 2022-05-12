package pokemon;

import java.util.ArrayList;
import java.util.HashMap;

public class Pokemon extends Species {
    public int ID;
    public static String name;
    public double level;
    public int xp;
    public HashMap<String,Integer> stats;
    public HashMap<String, HashMap<String, String>> known_capacities;
    private ArrayList<Integer> EV;
    private static ArrayList<Integer> DV;


    public Pokemon(String name_of_species, ArrayList<String> types, ArrayList<Integer> base_stats, int start_level, ArrayList<String> evolution, ArrayList<String>  capacities, double level, int xp, HashMap<String,Integer> stats,  HashMap<String, HashMap<String, String>> capacities1, String name, int ID) {
        super(name_of_species, types, base_stats, start_level, evolution, capacities);
        this.level = level;
        this.xp = xp;
        this.stats = stats;
        this.known_capacities = capacities1;
        Pokemon.name = name;
        this.ID = ID;
    }
    private void evolution() {

    }

    private void levelUp() {

    }
    private void soigner() {

    }
    private void rappeler() {

    }
    private void attaquer() {

    }
    public boolean isKO() {

        return false;
    }


    public int getID() {
        return ID;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Pokemon.name = name;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }

    public void setStats(HashMap<String, Integer> stats) {
        this.stats = stats;
    }

    public HashMap<String, HashMap<String, String>> getKnown_capacities() {
        return known_capacities;
    }

    public void setKnown_capacities(HashMap<String, HashMap<String, String>> known_capacities) {
        this.known_capacities = known_capacities;
    }

    public ArrayList<Integer> getEV() {
        return EV;
    }

    public void setEV(ArrayList<Integer> EV) {
        this.EV = EV;
    }

    public static ArrayList<Integer> getDV() {
        return DV;
    }

    public static void setDV(ArrayList<Integer> DV) {
        Pokemon.DV = DV;
    }
}
