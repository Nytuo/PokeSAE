package pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;
import pokedex.Pokedex;

public class Pokemon extends Species implements IPokemon, IStat {
    private int ID;
    private static String name;
    private int level;
    private double xp;
    private TreeMap<String,Integer> stats;
    /*private HashMap<String, HashMap<String, String>> known_capacities;*/
    /*private static int pvMax;*/
    private TreeMap<String,Integer> EV;
    private static ArrayList<Integer> DV;


    public Pokemon(String name_of_species, ArrayList<String> types, TreeMap<String,Integer> baseStats, int baseLevel, TreeMap<Integer,String> evolution, ArrayList<String> capacities, int level, double xp, HashMap<String,Integer> stats, /*HashMap<String,HashMap<String, String>> capacities1,*/ String name, int ID) {
        super(name_of_species, types, baseStats, baseLevel, evolution, capacities);
        this.level = baseLevel;
        this.xp = xp;
        this.stats=baseStats; //pv,force,defence,special,vitesse
        /*this.known_capacities = capacities1;*/
        Pokemon.name = name;
        this.ID = ID;
        /*Pokemon.pvMax = stats.get("PV");*/
        this.EV.putAll(baseStats);
        this.EV.replaceAll((k,v) -> 0);
    }
    
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
		new Pokemon(this.evolution.get(this.level),
	}
	
	public ICapacite[] getCapacitesApprises() {
		return null;
	}
	
	public void apprendCapacites(ICapacite[] caps) {
		
	}
	
	public void remplaceCapacite(int i, ICapacite cap) throws Exception {
		
	}
	
	public void gagneExperienceDe(IPokemon pok) {
		this.xp+=(1.5 * pok.getNiveau() * ((IEspece) pok).getBaseExp())/7;
	}
	
	public void subitAttaqueDe(IPokemon pok, IAttaque atk) {
		this.stats.get(atk) .calculeDommage(pok, pok)
	}
	
	public boolean estEvanoui() {
		return this.stats.get("PV") == 0;
		
	}
	
	public boolean aChangeNiveau() {
		return this.xp > (0.8*Math.pow(this.level,3)); //Formule: courbe d'experience
	}
	
	public boolean peutMuter() {
		return this.evolution.containsKey(this.level) && (this.evolution.get(this.level) != this.nameOfSpecies);
	}
	
	public void soigne() {
		this.stats.replace("PV", )
	}

	public int getPV() {
		return stats.get("PV-base");
	}

	public int getForce() {
		return stats.get("Force-base");
	}

	public int getDefense() {
		return stats.get("Défense-base");
	}

	public int getSpecial() {
		return stats.get("Spécial-base");
	}

	public int getVitesse() {
		return stats.get("Vitesse-base");
	}

	
	
	public void setPV(int i) {
		stats.replace("PV-base", i);
	}

	public void setForce(int i) {
		stats.replace("Force-base", i);
	}

	public void setDefense(int i) {
		stats.replace("Défense-base", i);
	}

	public void setVitesse(int i) {
		stats.replace("Vitesse-base", i);
	}

	public void setSpecial(int i) {
		stats.replace("Spécial-base", i);
	}
	

}
