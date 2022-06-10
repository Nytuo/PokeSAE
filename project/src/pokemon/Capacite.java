/**
 * @author BEUX Arnaud
 */
package pokemon;

import game.Combat;
import interfaces.ICategorie;
import interfaces.IType;
import java.util.*;
import pokedex.Pokedex;

/**
 * La classe Capacite représente une capacité d'un pokemon. Elle implémente les interfaces :
 * ICapacite, IEspece et IType.
 */
public class Capacite implements interfaces.ICapacite {

  /** Liste des capacités qui font un One Hit Kill. */
  List<String> OHKO =
      Arrays.asList("Abîme", "Empal'Korne", "Guillotine"); // Liste des capacités qui infligent des
  // dommages OHKO

  /** Le nom de la capacité. */
  String nom;

  /** Le type de la capacité (Eau, dragon, etc...). */
  interfaces.IType type;

  /** Savoir si la capacité est physique ou spéciale. */
  interfaces.ICategorie categorie;

  /** La puissance de la capacité. */
  int puissance;

  /** La précision de la capacité. */
  double precision;

  /** variable permettant de toogle l'utilisation de la capacité "patience" */
  boolean switchPatience = false;

  /** Le nombre de PP de la capacité. */
  int PP;

  /** Le nombre de PP maximum de la capacité. */
  int PPmax;

  /**
   * Constructeur de la classe Capacite qui insert dans les variables les valeurs par défaut.
   *
   * @param nom Le nom de la capacité.
   * @param type Le type de la capacité (Eau, dragon, etc...).
   * @param categorie Savoir si la capacité est physique ou spéciale.
   * @param puissance La puissance de la capacité.
   * @param precision La précision de la capacité.
   * @param PP Le nombre de PP de la capacité.
   */
  public Capacite(
      String nom,
      interfaces.IType type,
      interfaces.ICategorie categorie,
      int puissance,
      double precision,
      int PP) {
    this.nom = nom;
    this.type = type;
    this.categorie = categorie;
    this.puissance = puissance;
    this.precision = precision;
    this.PP = PP;
    this.PPmax = PP;
  }

  /**
   * Donne le nom de la capacité.
   *
   * @return Le nom de la capacité.
   */
  @Override
  public String getNom() {
    return nom;
  }

  /**
   * Donne la précision de la capacité.
   *
   * @return La précision de la capacité.
   */
  @Override
  public double getPrecision() {
    return precision;
  }

  /**
   * Donne la puissance de la capacité.
   *
   * @return La puissance de la capacité.
   */
  @Override
  public int getPuissance() {
    return puissance;
  }

  /**
   * Donne le nombre de PP restant de la capacité.
   *
   * @return Le nombre de PP restant de la capacité.
   */
  @Override
  public int getPP() {
    return PP;
  }

  /** Remet les PP à leur valeur initial (PPmax). */
  @Override
  public void resetPP() {
    this.PP = this.PPmax;
  }

  /**
   * Donne la catégorie de la capacité.
   *
   * @return la categorie de la capacité.
   */
  @Override
  public ICategorie getCategorie() {
    return categorie;
  }

  /**
   * Donne le type de la capacité.
   *
   * @return Le type de la capacité.
   */
  @Override
  public IType getType() {
    return type;
  }

  /**
   * Cette méthode calcule le dommage de la capacité entre deux pokémons.
   *
   * @param lanceur Le pokemon qui lance la capacité (attaquant).
   * @param receveur Le pokemon qui reçoit la capacité (adversaire).
   * @return Le nombre de dommage infligé.
   */
  @Override
  public int calculeDommage(interfaces.IPokemon lanceur, interfaces.IPokemon receveur) {

    double R = new Random().nextDouble() * (1 - this.getPrecision()) + this.getPrecision();
    if (R > this.getPrecision()) {
      return 0;
    }

    // Gestion des capacités qui infligent des dommages OHKO (On Hit Kill)
    if (OHKO.contains(this.categorie.getNom())) {
      return receveur.getStat().getPV();
    }

    // Gestion des capacités particulières
    switch (this.categorie.getNom()) {
      case "Sonic Boom":
        return 20;

      case "Ombre Nocturne":

      case "Frappe Atlas":
        return lanceur.getStat().getPV();
      case "Croc Fatal":
        if (receveur.getStat().getPV() == 1) {
          return 1;
        } else {
          return receveur.getStat().getPV() / 2;
        }
      case "Vague Psy":
        if (!receveur.getEspece().getTypes()[0].getNom().equals("Ténèbres")
            || !receveur.getEspece().getTypes()[1].getNom().equals("Ténèbres")) {
          return (int) ((lanceur.getNiveau()) * ((Math.random() * 10) + 5) / 10);
        }
        break;

      case "Draco-Rage":
        return 40;

      case "Riposte":
        return 2 * ((Pokemon) receveur).getAppliedDamages().get(Combat.nbTour - 1);

      case "Patience":
        if (switchPatience) {
          switchPatience = false;
          return 2 * ((Pokemon) receveur).getAppliedDamages().get(Combat.nbTour - 1)
              + ((Pokemon) receveur).getAppliedDamages().get(Combat.nbTour - 2);
        } else {
          switchPatience = true;
          return 0;
        }
    }

    // Gestion des capacités normales
    Pokedex pokedex =
        new Pokedex(); // Création d'une instance de la classe Pokedex pour acceder aux efficacités
    // des pokémons

    // Gestion du même type
    double STAB = 1;
    if (this.type == lanceur.getEspece().getTypes()[0]
        || this.type == lanceur.getEspece().getTypes()[1]) { // Si le
      // pokemon
      // lanceur
      // possède
      // le
      // même
      // type
      // que
      // la
      // capacité
      STAB = 1.5;
    }
    double EFFICACITE;
    // Calcul de l'efficacité
    double efficacite1 =
        pokedex.getEfficacite(
            lanceur.getEspece().getTypes()[0], receveur.getEspece().getTypes()[0]);
    try {
      double efficacite2 =
          pokedex.getEfficacite(
              lanceur.getEspece().getTypes()[1], receveur.getEspece().getTypes()[1]);
      EFFICACITE = Math.min(efficacite1, efficacite2);
    } catch (Exception e) {
      EFFICACITE = efficacite1;
    }
    double RANDOM = new Random().nextDouble() * (1 - 0.85) + 0.85; // Nombre random entre 0.85 et 1
    double CM = STAB * EFFICACITE * RANDOM; // Calcul du Multiplicateur

    // Calcul de la force en fonction du type de la capacité
    int att;
    int def;
    if (categorie.isSpecial()) {
      att = lanceur.getStat().getSpecial();
      def = receveur.getStat().getSpecial();
    } else {
      att = lanceur.getStat().getForce();
      def = receveur.getStat().getDefense();
    }
    return (int)
        (((((lanceur.getNiveau() * 0.4 + 2) * att * this.puissance) / (def * 50)) + 2)
            * CM); // Calcul du
    // nombre de
    // dommage
  }

  /** Diminue le nombre de PP de la capacité quand cette dernière est utilisée. */
  @Override
  public void utilise() {
    this.PP--;
  }
}
