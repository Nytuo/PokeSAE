package pokemon;

import java.util.ArrayList;
import java.util.TreeMap;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;
import pokedex.Pokedex;

public class Species implements IEspece{
    public String nameOfSpecies;
    public ArrayList<String> types;
    public TreeMap<String,Integer> baseStats;
    public int startLevel;
    public TreeMap<Integer,String> evolution;
    public ArrayList<String> capacities;
    private int baseXp;
    private int gainsXp;

    public Species(String name_of_species, ArrayList<String> types, TreeMap<String,Integer> baseStats, int start_level, TreeMap<Integer,String> evolution, ArrayList<String> capacities) {
        this.nameOfSpecies = name_of_species;
        this.types = types;
        this.baseStats = baseStats;
        this.startLevel = start_level;
        this.evolution = evolution;
        this.capacities = capacities;
    }
        
        public IStat getBaseStat() { //stats de base
        	return ;
        };				
    	public String getNom() {
    		return nameOfSpecies;
    	};
    	public int getNiveauDepart() { 
    		return startLevel;
    	};
    	public int getBaseExp() {
			return baseXp; 
    	};
    	public IStat getGainsStat() { //stats correspondant aux EV
			return ; 
    	};	
    	
    	public ICapacite[] getCapSet() { //ensemble des capacités disponibles pour cette espèce
			return ; 
    	};			
    	
    	public IEspece getEvolution(int niveau) { //renvoie null si aucune evolution possible
			if(this.evolution.get(niveau).isBlank())
				return null;
			else
				return new Pokedex().getInfo(this.evolution.get(niveau));
    	};  
    	
    	public IType[] getTypes() { //une espece de pokemon peut avoir un ou deux types
			return ;
    	};		
    	
}