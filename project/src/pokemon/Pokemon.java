package pokemon;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;


import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;

public class Pokemon extends Species implements IPokemon {
    private final int ID;
    private static String name;
    private final int level;
    private double xp;
    private final Stats stats;
    private Capacite[] known_capacities;
    private TreeMap<String, Integer> EV;
    private static ArrayList<Integer> DV;


    public Pokemon(String name_of_species, String name, Types[] types, Stats baseStats, int baseLevel, TreeMap<Integer, String> evolution, Capacite[] capacities, int level, double xp, Stats stats, Capacite[] capacitiesPoke, int ID) {
        super(name_of_species, types, baseStats, baseLevel, evolution, capacities, (int) xp);
        this.level = baseLevel;
        this.xp = xp;
        this.known_capacities = capacitiesPoke;
        this.stats = stats;
        Pokemon.name = name;
        this.ID = ID;
    }

    public IStat getStat() {
        return this.stats;
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
        return ((stats.getPV() / baseStats.getPV()) * 100);
    }

    public IEspece getEspece() {
        return new Species(this.nameOfSpecies, this.types, this.baseStats, this.startLevel, this.evolution, this.capacities, (int) this.xp);
    }

    public void vaMuterEn(IEspece esp) {
        new Pokemon(esp.getNom(), this.getNom(), this.types, (Stats) esp.getBaseStat(), this.level, this.evolution, this.capacities, this.level, this.xp, (Stats) esp.getBaseStat(), (Capacite[]) esp.getCapSet(), this.ID);
    }

    public ICapacite[] getCapacitesApprises() {
        return this.known_capacities;
    }

    public void apprendCapacites(ICapacite[] caps) {
        this.known_capacities = (Capacite[]) caps;
    }

    public void remplaceCapacite(int i, ICapacite cap) throws Exception {
        try {
            this.known_capacities[i] = (Capacite) cap;
        } catch (Exception e) {
            throw new Exception("Erreur de remplacement de capacité");
        }
    }

    public void gagneExperienceDe(IPokemon pok) {
        this.xp += (1.5 * pok.getNiveau() * ((IEspece) pok).getBaseExp()) / 7;
    }

    public void subitAttaqueDe(IPokemon pok, IAttaque atk) {
        this.stats.setPV(this.stats.getPV() - atk.calculeDommage(pok, this));
    }

    public boolean estEvanoui() {
        return this.stats.getPV() <= 0;

    }

    public boolean aChangeNiveau() {
        return this.xp > (0.8 * Math.pow(this.level, 3)); //Formule: courbe d'experience
    }

    public boolean peutMuter() {
        return this.evolution.containsKey(this.level) && (!Objects.equals(this.evolution.get(this.level), this.nameOfSpecies));
    }

    public void soigne() {
        this.stats.setPV(this.baseStats.getPV());
    }

}
