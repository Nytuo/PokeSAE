package game;

import dresseur.AIsimple;
import dresseur.Dresseur;
import interfaces.IAttaque;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;
import pokemon.Capacite;

/** La classe Tour gère les tours du combat. Elle implémente l'interface ITour. */
public class Tour implements ITour {

  /** Pokémon du joueur 1 */
  IPokemon pok1;

  /** Attaque du pokémon du joueur 1 */
  IAttaque atk1;

  /** Pokémon du joueur 2 */
  IPokemon pok2;

  /** Attaque du pokémon du joueur 2 */
  IAttaque atk2;

  /** Dresseur 1 (Joueur 1) */
  IDresseur dresseur1;

  /** Dresseur 2 (Joueur 2 (principalement l'IA)) */
  IDresseur dresseur2;

  int pokes1EnVie;
  int pokes2EnVie;

  /** Constructeur de la classe Tour
   * @param pok1 Pokémon du joueur 1
   * @param atk1 Attaque du pokémon du joueur 1
   * @param pok2 Pokémon du joueur 2
   * @param atk2 Attaque du pokémon du joueur 2
   * @param d1 Dresseur 1 (Joueur 1)
   * @param d2 Dresseur 2 (Joueur 2 (principalement l'IA))
   */
  public Tour(
      IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2, IDresseur d1, IDresseur d2) {

    this.pok1 = pok1;
    this.atk1 = atk1;
    this.pok2 = pok2;
    this.atk2 = atk2;
    this.dresseur1 = d1;
    this.dresseur2 = d2;
  }

  /**
   * Méthode permettant de commencer le tour. Le premier pokémon à attaqué est celui dont la vitesse
   * est suéprieur à celle de l'autre
   */
  @Override
  public void commence() {

    if (pok1.getStat().getVitesse() < pok2.getStat().getVitesse()) {
      System.out.println("Turn of " + dresseur1.getNom() + " with " + pok1.getNom());
      attaque(dresseur2, pok2, atk2, pok1, atk1);
      if (pok1 == Combat.pok1) {
        System.out.println("Turn of " + dresseur2.getNom() + " with " + pok2.getNom());
        attaque(dresseur1, pok1, atk1, pok2, atk2);
      }
    } else {

      System.out.println("Turn of " + dresseur2.getNom() + " with " + pok2.getNom());
      attaque(dresseur1, pok1, atk1, pok2, atk2);
      if (pok2 == Combat.pok2) {
        System.out.println("Turn of " + dresseur1.getNom() + " with " + pok1.getNom());
        attaque(dresseur2, pok2, atk2, pok1, atk1);
      }
    }
  }

  /**
   * Méthode qui permet d'attaquer un pokémon ennemi. On gère ici la défaite d'un Pokémon sur ce
   * tour et l'attaque utilisée contre le pokémon ennemi
   *
   * @param dresseur1 Le dresseur qui attaque
   * @param pok1 Le pokémon attaquant
   * @param atk1 L'attaque utilisée
   * @param pok2 Le pokémon attaqué
   * @param atk2 L'attaque utilisée
   */
  public void attaque(
      IDresseur dresseur1, IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {

    atk1.utilise();

    if (atk1.getClass() == Echange.class) {
      pok1.subitAttaqueDe(pok2, atk2);
      System.out.println(
          pok1.getNom()
              + " get hurt by "
              + ((Capacite) atk2).getNom()
              + " from "
              + pok2.getNom()
              + " and remains only "
              + pok1.getPourcentagePV()
              + " % of his HP");
    } else if (atk2.getClass() == Echange.class) {

    } else {
      System.out.println(atk2.calculeDommage(pok2, pok1) + " damages points");
      pok1.subitAttaqueDe(pok2, atk2);
      System.out.println(
          pok1.getNom()
              + " get hurt by "
              + ((Capacite) atk2).getNom()
              + " from "
              + pok2.getNom()
              + " and remains only "
              + pok1.getPourcentagePV()
              + " % of his HP");
    }

    if (this.pok1.estEvanoui()) {

      System.out.println(this.pok2.getNom() + " slain " + this.pok1.getNom() + "\n");

      this.pok2.gagneExperienceDe(this.pok1);
      System.out.println(this.pok2.getNom() + " gained " + this.pok2.getExperience() + " xp\n");

      ((Dresseur) this.dresseur1).pokeEnVie--;
      if (((Dresseur) this.dresseur1).pokeEnVie > 0) {

        Combat.pok1 =
            new Echange((Dresseur) this.dresseur1, this.pok1, this.pok2).echangeCombattant();

        System.out.println(this.dresseur1.getNom() + " sent " + Combat.pok1.getNom());
      }
    }

    if (this.pok2.estEvanoui()) {

      System.out.println(this.pok1.getNom() + " slain " + this.pok2.getNom());

      this.pok1.gagneExperienceDe(this.pok2);
      System.out.println(this.pok1.getNom() + " gained " + this.pok1.getExperience() + " xp\n");

      ((Dresseur) this.dresseur2).pokeEnVie--;
      if (((Dresseur) this.dresseur2).pokeEnVie > 0) {
        Combat.pok2 =
            new Echange((AIsimple) this.dresseur2, this.pok2, this.pok1).echangeCombattant();
        System.out.println(this.dresseur2.getNom() + " sent " + Combat.pok2.getNom());
      }
    }
  }

  /**
   * Méthode qui permet de savoir si le dresseur passe son tour
   *
   * @param dresseur1 le dresseur qui passe son tour
   */
  void passTour(Dresseur dresseur1) {
    System.out.println("Pass his turn");
  }
}
