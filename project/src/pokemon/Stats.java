package pokemon;

/**
 * @author Beux
 */

/**
 * La classe Stats représente les statistiques d'un Pokémon/d'une espèce. Elle implémente
 * l'interface IStat
 */
public class Stats implements interfaces.IStat {
  /** Stat PV. */
  private int PV;
  /** Stat Force. */
  private int force;
  /** Stat Défense. */
  private int defense;
  /** Stat Spécial. */
  private int special;
  /** Stat Vitesse. */
  private int vitesse;

  /**
   * Le constructeur de la classe Stats qui donne une valeur aux attributs
   *
   * @param PV
   * @param force
   * @param defense
   * @param special
   * @param vitesse
   */
  public Stats(int PV, int force, int defense, int special, int vitesse) {
    this.PV = PV;
    this.force = force;
    this.defense = defense;
    this.special = special;
    this.vitesse = vitesse;
  }

  /**
   * Donne la valeur de l'attribut "PV".
   *
   * @return l'attribut "PV".
   */
  @Override
  public int getPV() {
    return PV;
  }

  /**
   * Donne la valeur de l'attribut "force".
   *
   * @return l'attribut "force".
   */
  @Override
  public int getForce() {
    return force;
  }

  /**
   * Donne la valeur de l'attribut "defense".
   *
   * @return l'attribut "defense".
   */
  @Override
  public int getDefense() {
    return defense;
  }

  /**
   * Donne la valeur de l'attribut "special".
   *
   * @return l'attribut "special".
   */
  @Override
  public int getSpecial() {
    return special;
  }

  /**
   * Donne la valeur de l'attribut "vitesse".
   *
   * @return l'attribut "vitesse".
   */
  @Override
  public int getVitesse() {
    return vitesse;
  }

  /** Attribue une valeur à l'attribut "PV". */
  @Override
  public void setPV(int i) {
    this.PV = i;
  }

  /** Attribue une valeur à l'attribut "force". */
  @Override
  public void setForce(int i) {
    this.force = i;
  }

  /** Attribue une valeur à l'attribut "defense". */
  @Override
  public void setDefense(int i) {
    this.defense = i;
  }

  /** Attribue une valeur à l'attribut "vitesse". */
  @Override
  public void setVitesse(int i) {
    this.vitesse = i;
  }

  /** Attribue une valeur à l'attribut "special". */
  @Override
  public void setSpecial(int i) {
    this.special = i;
  }
}
