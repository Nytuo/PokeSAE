package pokemon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;
import pokedex.Pokedex;

public class Pokemon extends Species implements IPokemon, IStat {
    private int ID;
    private static String name;
    /*private int level;*/
    private double xp;
    private HashMap<String,Integer> stats;
    private HashMap<String, HashMap<String, String>> known_capacities;
    private static int pvMax;
    private ArrayList<Integer> EV;
    private static ArrayList<Integer> DV;


    public Pokemon(String name_of_species, ArrayList<String> types, ArrayList<Integer> baseStats, int start_level, ArrayList<String> evolution, ArrayList<String> capacities, int level, double xp, HashMap<String,Integer> stats,  HashMap<String, HashMap<String, String>> capacities1, String name, int ID) {
        super(name_of_species, types, baseStats, start_level, evolution, capacities);
        /*this.level = level;*/
        this.xp = xp;
        this.stats=stats; //pv,force,defence,special,vitesse
        this.known_capacities = capacities1;
        Pokemon.name = name;
        this.ID = ID;
        Pokemon.pvMax = stats.get("PV");
    }
    
    
  
    
    /*private void evolution() {

    }
    

    private void levelUp() {

    }
    private void soigne() {

    }
    private void rappeler() {

    }
    private void attaquer() {

    }
    public boolean isKO() {

        return null;
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
    */
    
    public IStat getStat() {
		return null;
    }
    
    public double getExperience() {
		return xp;
    	
    }
    
    public int getNiveau() {
		return level;
    	
    }
	public int getId() {
		return ID;
		
	}
	public String getNom() {
		return name;
		
	}
	public double getPourcentagePV() {
		return stats.get("PV");
	}
	
	public IEspece getEspece() {
		return null;
		
	}
	
	public void vaMuterEn(IEspece esp) {
		
	}
	
	public ICapacite[] getCapacitesApprises() {
		return null;
	}
	
	public void apprendCapacites(ICapacite[] caps) {
		
	}
	
	public void remplaceCapacite(int i, ICapacite cap) throws Exception {
		
	}
	
	public void gagneExperienceDe(IPokemon pok) {
		this.stats
	}
	
	public void subitAttaqueDe(IPokemon pok, IAttaque atk) {
		
	}
	
	public boolean estEvanoui() {
		return this.stats.get("PV") == 0;
		
	}
	
	public boolean aChangeNiveau() {
		return false;
		
	}
	
	public boolean peutMuter() {
		return false;
	}
	
	public void soigne() {
		
	}
}
