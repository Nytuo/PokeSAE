package pokemon;

import java.util.TreeMap;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;
import pokedex.Pokedex;

public class Species implements IEspece{
    public String nameOfSpecies;
    public Types[] types;
    public Stats baseStats;
    public Stats gainsStat;
    public int startLevel;
    public TreeMap<Integer,String> evolution;
    public Capacite[] capacities;
    private final int baseXp;

    public Species(String name_of_species, Types[] types, Stats baseStats, int start_level, TreeMap<Integer,String> evolution, Capacite[] capacities, int xp, Stats gainsStat) {
        this.nameOfSpecies = name_of_species;
        this.types = types;
        this.baseStats = baseStats;
        this.startLevel = start_level;
        this.evolution = evolution;
        this.capacities = capacities;
        this.baseXp = xp;
        this.gainsStat=gainsStat;
    }

    public IStat getBaseStat() { //stats de base

        return this.baseStats;
    }

    public String getNom() {
        return nameOfSpecies;
    }

    public int getNiveauDepart() {
        return startLevel;
    }

    public int getBaseExp() {
        return baseXp;
    }

    public IStat getGainsStat() { //stats correspondant aux EV
        return this.gainsStat;
    }

    public ICapacite[] getCapSet() { //ensemble des capacités disponibles pour cette espèce
        return this.capacities;
    }

    public IEspece getEvolution(int niveau) { //renvoie null si aucune evolution possible
        if(this.evolution.containsKey(niveau))
        	return new Pokedex().getInfo(this.evolution.get(niveau));
        else
            return null;
    }

    public IType[] getTypes() { //une espece de pokemon peut avoir un ou deux types
        return this.types;
    }

}