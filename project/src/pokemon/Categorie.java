package pokemon;

/**
 * La classe Categorie représente la catégorie d'une Capacite. Elle implémente l'interface
 * ICategorie.
 */
public class Categorie implements interfaces.ICategorie {
  /** Nom de la catégorie. */
  private final String nom;
  /** true: Capacite spéciale; false: Capacite physique. */
  private final boolean special;

  /**
   * Le constructeur de la classe Categorie.
   *
   * @param nom String (Le nom de la catégorie).
   * @param special boolean (true: Capacite spéciale; false: Capacite physique).
   */
  public Categorie(String nom, boolean special) {
    this.nom = nom;
    this.special = special;
  }

  /**
   * Donne l'état de la catégorie.
   *
   * @return l'état de la catégorie.
   */
  @Override
  public boolean isSpecial() {
    return special;
  }

  /**
   * Donne le nom de la catégorie.
   *
   * @return le nom de la catégorie.
   */
  @Override
  public String getNom() {
    return nom;
  }
}
