package pokemon;

import java.util.Objects;
import java.util.TreeMap;


import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;

public class Pokemon extends Species implements IPokemon {
    private final int ID;
    private String name;
    private int level;
    private double xp;
    private Stats stats;
    private Capacite[] known_capacities;
    private TreeMap<String, Integer> EV;
    private static TreeMap<String, Integer> DV;

    private int PVActuel;

    public Pokemon(String name_of_species, String name, Types[] types, Stats baseStats, int baseLevel, TreeMap<Integer, String> evolution, Capacite[] capacities, double xp, Stats stats, Capacite[] capacitiesPoke, int ID, Stats gainsStats) {
        super(name_of_species, types, baseStats, baseLevel, evolution, capacities, (int) xp, gainsStats);
        this.level = baseLevel;
        this.xp = xp;
        this.known_capacities = capacitiesPoke;
        this.stats = stats;
        this.name = name;
        this.ID = ID;
        this.PVActuel = this.stats.getPV();
        this.EV = new TreeMap<>();
        EV.put("PV", 0);
        EV.put("Force", 0);
        EV.put("Defense", 0);
        EV.put("Special", 0);
        EV.put("Vitesse", 0);
        DV = new TreeMap<>();
        DV.put("Force", (int) (Math.random() * (15)));
        DV.put("Defense", (int) (Math.random() * (15)));
        DV.put("Special", (int) (Math.random() * (15)));
        DV.put("Vitesse", (int) (Math.random() * (15)));
        DV.put("PV", toBit(DV.get("Force"), DV.get("Defense"), DV.get("Vitesse"), DV.get("Special")));
    }

    public static int toBit(int n1, int n2, int n3, int n4) {
        String s = String.valueOf(getTheLastDigit(Integer.parseInt(Integer.toBinaryString(n1)))) + String.valueOf(getTheLastDigit(Integer.parseInt(Integer.toBinaryString(n2))) + String.valueOf(getTheLastDigit(Integer.parseInt(Integer.toBinaryString(n3)))) + String.valueOf(getTheLastDigit(Integer.parseInt(Integer.toBinaryString(n4)))));
        return Integer.parseInt(s, 2);
    }
    public void setNom(String name) {
        this.name = name;
    }


    public static int getTheLastDigit(int n) {
        return n % 10;
    }

    public IStat getStat() {
        return this.stats;
    }

    public TreeMap<String, Integer> getDV() {
        return DV;
    }

    public TreeMap<String, Integer> getEV() {
        return EV;
    }

    public void setEV(String Key, int Value) {
        this.EV.replace(Key, Value);
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
        return ((PVActuel / stats.getPV()) * 100);
    }

    public IEspece getEspece() {
        return new Species(this.nameOfSpecies, this.types, this.baseStats, this.startLevel, this.evolution, this.capacities, (int) this.xp, (Stats) this.getGainsStat());
    }

    public void vaMuterEn(IEspece esp) {
        this.nameOfSpecies = esp.getNom();
        this.types = (Types[]) esp.getTypes();
        this.baseStats = (Stats) esp.getBaseStat();
        this.startLevel = this.level;
        this.capacities = (Capacite[]) esp.getCapSet();
        this.gainsStat = (Stats) esp.getGainsStat();
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
        this.PVActuel -= atk.calculeDommage(pok, this);
    }

    public boolean estEvanoui() {
        boolean evanoui = false;
        int ppRestant=0;
        for (Capacite c: this.known_capacities) {
            ppRestant = c.getPP();
        }
        if (ppRestant == 0){
            evanoui = true;
        }
        if (this.PVActuel <= 0) {
            evanoui = true;
        }
        return evanoui;
    }

    public boolean aChangeNiveau() {
        return this.xp > (0.8 * Math.pow(this.level, 3)); //Formule: courbe d'experience
    }

    public boolean peutMuter() {
        return this.evolution.containsKey(this.level) && (!Objects.equals(this.evolution.get(this.level), this.nameOfSpecies));
    }

    public void soigne() {
        this.PVActuel = this.stats.getPV();
    }

}
