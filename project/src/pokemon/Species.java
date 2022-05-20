package pokemon;

import java.util.ArrayList;
import java.util.TreeMap;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;

public class Species implements IEspece{
    public String name_of_species;
    public ArrayList<String> types;
    public TreeMap<String,Integer> baseStats;
    public int start_level = 1;
    public ArrayList<String> evolution;
    public ArrayList<String> capacities;
    private int base_xp;
    private int gains_xp;

    public Species(String name_of_species, ArrayList<String> types, TreeMap<String,Integer> baseStats, int start_level, ArrayList<String> evolution, ArrayList<String> capacities) {
        this.name_of_species = name_of_species;
        this.types = types;
        this.baseStats = baseStats;
        this.start_level = start_level;
        this.evolution = evolution;
        this.capacities = capacities;
    }
        
        public IStat getBaseStat() { //stats de base
        	return baseStats;
        };				
    	public String getNom() {
    		return name_of_species;
    	};
    	public int getNiveauDepart() { 
    		return start_level;
    	};
    	public int getBaseExp() {
			return base_xp; 
    	};
    	public IStat getGainsStat() { //stats correspondant aux EV
			return null; 
    	};	
    	
    	public ICapacite[] getCapSet() { //ensemble des capacités disponibles pour cette espèce
			return null; 
    	};			
    	
    	public IEspece getEvolution(int niveau) { //renvoie null si aucune evolution possible
			return null; 
    	};  
    	
    	public IType[] getTypes() { //une espece de pokemon peut avoir un ou deux types
			return null;
    	};			
    	
}