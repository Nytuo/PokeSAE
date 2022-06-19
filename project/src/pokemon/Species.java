package pokemon;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;
import java.util.TreeMap;
import pokedex.Pokedex;

/** La classe Species représente l'espèce d'un Pokémon Elle implémente l'interface IEspece */
public class Species implements IEspece {
  /** Nom de l'espèce */
  public String nameOfSpecies;
  /** Types de l'espèce */
  public Types[] types;
  /** Statistiques de base de l'espèce */
  public Stats baseStats;
  /** Gains en EV que vont obtenir les Pokémons vainqueurs contre cette espèce. */
  public Stats gainsStat;
  /** Niveau de départ qu'a cette espèce. */
  public int startLevel;
  /** TreeMap des évolutions de cette espèce, où la clé correspond au niveau de l'évolution. */
  public TreeMap<Integer, String> evolution;
  /** Tableau des capacités qui peuvent être enseignées à cette espèce. */
  public Capacite[] capacities;
  /** XP de base de cette espèce. */
  private final int baseXp;
  /** L'ID de l'espèce */
  public int ID;

  /**
   * Constructeur de la classe Species qui insert dans les variables les valeurs par défaut.
   *
   * @param name_of_species Nom de l'espèce
   * @param types Types de l'espèce
   * @param baseStats Statistiques de base de l'espèce
   * @param start_level Niveau de départ de l'espèce
   * @param evolution Evolutions de l'espèce
   * @param capacities Capacités de l'espèce
   * @param baseXp XP de base de l'espèce
   * @param id ID de l'espèce
   * @param gainsStat Gains en EV que vont obtenir les Pokémons vainqueurs contre cette espèce.
   */
  public Species(
      String name_of_species,
      Types[] types,
      Stats baseStats,
      int start_level,
      TreeMap<Integer, String> evolution,
      Capacite[] capacities,
      int baseXp,
      int id,
      Stats gainsStat) {
    this.nameOfSpecies = name_of_species;
    this.types = types;
    this.baseStats = baseStats;
    this.startLevel = start_level;
    this.evolution = evolution;
    this.capacities = capacities;
    this.baseXp = baseXp;
    this.ID = id;
    this.gainsStat = gainsStat;
  }

  /**
   * Donne les stats de base de l'espèce.
   *
   * @return un objet Stat correspondant aux stats de base de l'espèce.
   */
  public IStat getBaseStat() { // stats de base

    return this.baseStats;
  }

  /**
   * Donne le nom de l'espèce.
   *
   * @return le nom de l'espèce.
   */
  public String getNom() {
    return nameOfSpecies;
  }

  /**
   * Donne le niveau de départ de l'espèce.
   *
   * @return le niveau de départ de l'espèce.
   */
  public int getNiveauDepart() {
    return startLevel;
  }

  /**
   * Donne l'expérience de départ de l'espèce.
   *
   * @return l'expérience de départ de l'espèce.
   */
  public int getBaseExp() {
    return baseXp;
  }

  /**
   * Donne les gains d'EV qu'obtiennent les vainqueurs contre cette espèce.
   *
   * @return un objet Stat correspondant aux gainStat.
   */
  public IStat getGainsStat() { // stats correspondant aux EV
    return this.gainsStat;
  }

  /**
   * Donne toutes les capacités disponibles pour cette espèce.
   *
   * @return un tableau d'objets Capacite.
   */
  public ICapacite[] getCapSet() {
    return this.capacities;
  }

  /**
   * Donne l'espèce correspondant à l'évolution de l'espèce actuelle si elle en possède une au
   * niveau en paramètre.
   *
   * @return un objet Species.
   */
  public IEspece getEvolution(int niveau) { // renvoie null si aucune evolution possible
    try {
			return new Pokedex().getInfo(this.evolution.get(this.evolution.ceilingKey(niveau)));
		} catch (NullPointerException e) {
			try {
				if (this.evolution.containsKey(this.evolution.floorKey(niveau)))
					return new Pokedex().getInfo(this.evolution.get(this.evolution.floorKey(niveau)));
			} catch (NullPointerException e2) {
			}
		}
		return null;
	}

  /**
   * Donne un tableau d'objets Types correspondant aux types de l'espèce.
   *
   * @return un tableau d'objets Types.
   */
  public IType[] getTypes() { // une espece de pokemon peut avoir un ou deux types
    return this.types;
  }
}
