package game;

import dresseur.AIsimple;
import dresseur.Dresseur;
import interfaces.IAttaque;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;
import pokemon.Capacite;

public class Tour implements ITour {

  IPokemon pok1;
  IAttaque atk1;
  IPokemon pok2;
  IAttaque atk2;
  IDresseur dresseur1;
  IDresseur dresseur2;
  int pokes1EnVie;
  int pokes2EnVie;

  public Tour(
      IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2, IDresseur d1, IDresseur d2) {

    this.pok1 = pok1;
    this.atk1 = atk1;
    this.pok2 = pok2;
    this.atk2 = atk2;
    this.dresseur1 = d1;
    this.dresseur2 = d2;
  }

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
      if ( pok2==Combat.pok2) {
        System.out.println("Turn of " + dresseur1.getNom() + " with " + pok1.getNom());
        attaque(dresseur2, pok2, atk2, pok1, atk1);
      }
    }
  }

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

  void passTour(Dresseur dresseur1) {
    System.out.println("Pass his turn");
  }
}
