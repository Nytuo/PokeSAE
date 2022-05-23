package pokemon;

import java.util.Objects;
import java.util.TreeMap;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;

/**
 * @author testa/beux
 **/

/**
 * La classe Pokémon représente un Pokémon
 * Elle implémente IPokemon
 */
public class Pokemon extends Species implements IPokemon {
    /**
     * ID du Pokémon
     */
    private final int ID;
    /**
     * Nom du Pokémon
     */
    private String name;
    /**
     * Niveau du Pokémon
     */
    private int level;
    /**
     * Expérience du Pokémon
     */
    private double xp;
    /**
     * Statistiques du Pokémon
     */
    private Stats stats;
    /**
     * Capacités apprises par le Pokémon
     */
    private Capacite[] known_capacities;
    /**
     * Valeurs des EV du Pokémon
     */
    private TreeMap<String, Integer> EV;
    /**
     * Valeurs des DV du Pokémon
     */
    private final TreeMap<String, Integer> DV;

    /**
     * PV actuels du Pokémon
     */
    private int PVActuel;


    /**
     * Constructeur de la classe Pokémon qui va construire le Pokémon.
     *
     * @param name_of_species Le nom de l'espèce.
     * @param name            Le nom du Pokémon.
     * @param types           Les types du Pokémon.
     * @param baseStats       Les statistiques de base de l'espèce du Pokémon.
     * @param baseLevel       Le niveau de base du Pokémon.
     * @param evolution       Les évolutions de l'espèce du Pokémon.
     * @param capacities      Les capacites que peut apprendre l'espèce du Pokémon.
     * @param xp              L'expérience de départ du Pokémon.
     * @param capacitiesPoke  Les capacités apprises par le Pokémon.
     * @param ID              L'ID unique du Pokémon.
     * @param gainsStats      Les valeurs des EV que peuvent obtenir les vainqueurs contre l'espèce du Pokémon.
     */
    public Pokemon(String name_of_species, String name, Types[] types, Stats baseStats, int baseLevel, TreeMap<Integer, String> evolution, Capacite[] capacities, double xp, Capacite[] capacitiesPoke, int ID, Stats gainsStats) {
        super(name_of_species, types, baseStats, baseLevel, evolution, capacities, (int) xp, gainsStats);
        this.level = baseLevel;
        this.xp = xp;
        this.known_capacities = capacitiesPoke;
        this.name = name;
        this.ID = ID;
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
        int PV = ((((2 * (this.baseStats.getPV() + this.getDV().get("PV")) + this.getEV().get("PV") / 4) * this.getNiveau()) / 100) + this.getNiveau() + 10);
        int force = ((((2 * (this.baseStats.getForce() + this.getDV().get("Force")) + this.getEV().get("Force") / 4) * this.getNiveau()) / 100) + 5);
        int defense = ((((2 * (this.baseStats.getDefense() + this.getDV().get("Defense")) + this.getEV().get("Defense") / 4) * this.getNiveau()) / 100) + 5);
        int special = ((((2 * (this.baseStats.getSpecial() + this.getDV().get("Special")) + this.getEV().get("Special") / 4) * this.getNiveau()) / 100) + 5);
        int vitesse = ((((2 * (this.baseStats.getVitesse() + this.getDV().get("Vitesse")) + this.getEV().get("Vitesse") / 4) * this.getNiveau()) / 100) + 5);
        this.PVActuel = PV;
        this.stats = new Stats(PV, force, defense, special, vitesse);
    }

    /**
     * Prend le dernier bit de chaque paramètre et donne la valeur décimale des bit assemblés dans l'ordre des paramètres
     *
     * @param n1 Correspond au DV de la stat Force.
     * @param n2 Correspond au DV de la stat Défense.
     * @param n3 Correspond au DV de la stat Spécial.
     * @param n4 Correspond au DV de la stat Vitesse.
     * @return la valeur décimale des bit assemblés dans l'ordre des paramètres.
     */
    public static int toBit(int n1, int n2, int n3, int n4) {
        String s = String.valueOf(getTheLastDigit(Integer.parseInt(Integer.toBinaryString(n1)))) + String.valueOf(getTheLastDigit(Integer.parseInt(Integer.toBinaryString(n2))) + String.valueOf(getTheLastDigit(Integer.parseInt(Integer.toBinaryString(n3)))) + String.valueOf(getTheLastDigit(Integer.parseInt(Integer.toBinaryString(n4)))));
        return Integer.parseInt(s, 2);
    }


    /**
     * Donne l'unité d'un nombre.
     *
     * @param n Le nombre dont on veut l'unité
     * @return L'unité d'un nombre.
     */
    public static int getTheLastDigit(int n) {
        return n % 10;
    }

    /**
     * Change le nom du Pokémon.
     *
     * @param name.
     */
    public void setNom(String name) {
        this.name = name;
    }

    /**
     * Donne les stats du Pokémon.
     *
     * @return un objet Stat.
     */
    public IStat getStat() {
        return this.stats;
    }

    /**
     * Donne la TreeMap des DV du Pokémon.
     *
     * @return une TreeMap des DV.
     */
    public TreeMap<String, Integer> getDV() {
        return DV;
    }

    /**
     * Donne la TreeMap des EV du Pokémon.
     *
     * @return une TreeMap des EV.
     */
    public TreeMap<String, Integer> getEV() {
        return EV;
    }

    /**
     * Attribue une valeur à un EV en fonction de sa clé.
     *
     * @param Key   Clé de la TreeMap.
     * @param Value Valeur que l'on attribue à l'EV.
     */
    public void setEV(String Key, int Value) {
        this.EV.replace(Key, Value);
    }

    /**
     * Donne l'expérience du Pokémon
     *
     * @return l'expérience du Pokémon
     */
    public double getExperience() {
        return xp;

    }

    /**
     * Donne le niveau du Pokémon.
     *
     * @return le niveau du Pokémon.
     */
    public int getNiveau() {
        return level;

    }

    /**
     * Donne l'ID du Pokémon.
     *
     * @return l'ID du Pokémon.
     */
    public int getId() {
        return ID;

    }

    /**
     * Donne le nom du Pokémon.
     *
     * @return le nom du Pokémon.
     */
    public String getNom() {
        return name;


    }

    /**
     * Donne le pourcentage actuel de PV du Pokémon.
     *
     * @return le pourcentage actuel de PV du Pokémon.
     */
    public double getPourcentagePV() {
        return ((PVActuel / stats.getPV()) * 100);
    }

    /**
     * Donne l'espèce du Pokémon
     *
     * @return un objet Species.
     */
    public IEspece getEspece() {
        return new Species(this.nameOfSpecies, this.types, this.baseStats, this.startLevel, this.evolution, this.capacities, (int) this.xp, (Stats) this.getGainsStat());
    }

    /**
     * Cette méthode change l'espèce du Pokémon en l'espèce en laquelle il évolue.
     *
     * @param esp
     */
    public void vaMuterEn(IEspece esp) {
        this.nameOfSpecies = esp.getNom();
        this.types = (Types[]) esp.getTypes();
        this.baseStats = (Stats) esp.getBaseStat();
        this.startLevel = this.level;
        this.capacities = (Capacite[]) esp.getCapSet();
        this.gainsStat = (Stats) esp.getGainsStat();
    }

    /**
     * Donne les capacités apprises par le Pokémon.
     *
     * @return un tableau d'objets Capacite.
     */
    public ICapacite[] getCapacitesApprises() {
        return this.known_capacities;
    }

    /**
     * Cette méthode remplace les capacités apprises par un tableau de Capacite.
     *
     * @param caps Le tableau de Capacite par lequel sera remplacé les capacités actuelles du Pokémon
     */
    public void apprendCapacites(ICapacite[] caps) {
        this.known_capacities = (Capacite[]) caps;
    }

    /**
     * Cette méthode remplace une capacité du Pokémon par une autre
     *
     * @param i   L'index de la capacité que l'on souhaite remplacer
     * @param cap L'objet Capacite par lequel on souhaite remplacer la capacité actuelle
     */
    public void remplaceCapacite(int i, ICapacite cap) throws Exception {
        try {
            this.known_capacities[i] = (Capacite) cap;
        } catch (Exception e) {
            throw new Exception("Erreur de remplacement de capacité");
        }
    }

    /**
     * Cette méthode calcule l'expérience qu'obtient le Pokémon en battant un autre Pokémon.
     *
     * @param pok Le Pokémon vaincu
     */
    public void gagneExperienceDe(IPokemon pok) {
        this.xp += (1.5 * pok.getNiveau() * ((IEspece) pok).getBaseExp()) / 7;
    }

    /**
     * Enlève la quantité de dégâts infligée par l'attaque adversaire.
     *
     * @param pok Le pokémon adverse
     * @param atk L'attaque adverse
     */
    public void subitAttaqueDe(IPokemon pok, IAttaque atk) {
        this.PVActuel -= atk.calculeDommage(pok, this);
    }

    /**
     * Donne l'état du Pokémon
     *
     * @return l'état du Pokémon
     */
    public boolean estEvanoui() {
        boolean evanoui = false;
        int ppRestant = 0;
        for (Capacite c : this.known_capacities) {
            ppRestant = c.getPP();
        }
        if (ppRestant == 0) {
            evanoui = true;
        }
        if (this.PVActuel <= 0) {
            evanoui = true;
        }
        return evanoui;
    }

    /**
     * Donne l'état de changement de niveau, si un changement de niveau est réalisé
     *
     * @return l'état de changement de niveau
     */
    public boolean aChangeNiveau() {
        return this.xp > (0.8 * Math.pow(this.level, 3)); //Formule: courbe d'experience
    }

    /**
     * Cette méthode sert à incrémenter le niveau du Pokémon et augmenter ses statistiques suite à un changement de level.
     */
    public void levelUp() {
        this.level++;
        this.stats.setPV((((2 * (this.stats.getPV() + this.getDV().get("PV")) + this.getEV().get("PV") / 4) * this.getNiveau()) / 100) + this.getNiveau() + 10);
    }

    /**
     * Donne l'état d'évolution du Pokémon/s'il peut évoluer.
     *
     * @return l'état d'évolution du Pokémon.
     */
    public boolean peutMuter() {
        return this.evolution.containsKey(this.level) && (!Objects.equals(this.evolution.get(this.level), this.nameOfSpecies));
    }

    /**
     * Cette méthode remet au maximum les PV du Pokémon.
     */
    public void soigne() {
        this.PVActuel = this.stats.getPV();
    }

}
