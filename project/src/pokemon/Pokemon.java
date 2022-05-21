package pokemon;

import java.util.ArrayList;
import java.util.TreeMap;


import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;

public class Pokemon extends Species implements IPokemon, interfaces.IEchange {
    private int ID;
    private static String name;
    private int level;
    private double xp;
    private Stats stats;
    private Capacite[] known_capacities;
    private int PVActuels;
    private TreeMap<String,Integer> EV;
    private static ArrayList<Integer> DV;


    public Pokemon(String name_of_species, String name, Types[] types, Stats baseStats, int baseLevel, TreeMap<Integer,String> evolution, Capacite[] capacities, int level, double xp, Stats stats, Capacite[] capacitiesPoke, int ID) {
        super(name_of_species, types, baseStats, baseLevel, evolution, capacities);
        this.level = baseLevel;
        this.xp = xp;
        this.known_capacities = capacitiesPoke;
        Pokemon.name = name;
        this.ID = ID;
        this.PVActuels = stats.getPV();
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
        return stats.getPV();
    }

    public IEspece getEspece() {
        return new Species(this.nameOfSpecies, this.types, this.baseStats, this.startLevel, this.evolution, this.capacities);
    }
    public void vaMuterEn(IEspece esp) {
        new Pokemon(esp.getNom(), this.getNom(), this.types,(Stats) esp.getBaseStat(), this.level,this.evolution, this.capacities, this.level, this.xp, (Stats) esp.getBaseStat(), (Capacite[]) esp.getCapSet(), this.ID);
    }

    public ICapacite[] getCapacitesApprises() {

        return new ICapacite[0];
    }

    public void apprendCapacites(ICapacite[] caps) {

    }

    public void remplaceCapacite(int i, ICapacite cap) throws Exception {
        try{

        this.known_capacities[i] = (Capacite) cap;
        }catch (Exception e){
            throw new Exception("Erreur de remplacement de capacité");
        }
    }

    public void gagneExperienceDe(IPokemon pok) {
        this.xp+=(1.5 * pok.getNiveau() * ((IEspece) pok).getBaseExp())/7;
    }

    public void subitAttaqueDe(IPokemon pok, IAttaque atk) {
        this.PVActuels-=atk.calculeDommage(pok, this);
    }

    public boolean estEvanoui() {
        return this.PVActuels <= 0;

    }

    public boolean aChangeNiveau() {
        return this.xp > (0.8*Math.pow(this.level,3)); //Formule: courbe d'experience
    }

    public boolean peutMuter() {
        return this.evolution.containsKey(this.level) && (this.evolution.get(this.level) != this.nameOfSpecies);
    }

    public void soigne() {
        this.PVActuels=this.stats.getPV();
    }

    @Override
    public void setPokemon(IPokemon pok) {

    }

    @Override
    public IPokemon echangeCombattant() {
        return null;
    }

    @Override
    public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
        return 0;
    }

    @Override
    public void utilise() {

    }
}
