package game;

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

      attaque(dresseur1, pok1, atk1, pok2, atk2);
      attaque(dresseur2, pok2, atk2, pok1, atk1);
    } else {

      attaque(dresseur2, pok2, atk2, pok1, atk1);
      attaque(dresseur1, pok1, atk1, pok2, atk2);
    }
  }

  public void attaque(
      IDresseur dresseur1, IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {

    atk1.utilise();

    if (atk1.getClass() == Echange.class) {
      pok1.subitAttaqueDe(pok2, atk2);
    } else if (atk2.getClass() == Echange.class) {

    } else {
      System.out.println(atk2.calculeDommage(pok2, pok1) + " points de dommage");
      pok1.subitAttaqueDe(pok2, atk2);
      System.out.println(
          pok1.getNom()
              + " a subit "
              + ((Capacite) atk2).getNom()
              + " de "
              + pok2.getNom()
              + " PV restant : "
              + pok1.getPourcentagePV()
              + " %");
    }

    if (pok1.estEvanoui()) {

      System.out.println(pok2.getNom() + " a vaincu " + pok1.getNom() + "\n");

      pok2.gagneExperienceDe(pok1);
      System.out.println(pok2.getNom() + " a accumulé " + pok2.getExperience() + " xp\n");

      ((Dresseur) dresseur1).pokeEnVie--;
      if (((Dresseur) dresseur1).pokeEnVie > 0) {
        atk1 = new Echange((Dresseur) dresseur1, pok1, pok2);
        pok1 = ((Echange) atk1).echangeCombattant();
        System.out.println(dresseur1.getNom() + " sent " + pok1.getNom());
      }
    }

    if (pok2.estEvanoui()) {

      System.out.println(pok1.getNom() + " a vaincu " + pok2.getNom() + "\n");

      pok1.gagneExperienceDe(pok2);
      System.out.println(pok1.getNom() + " a accumulé " + pok1.getExperience() + " xp\n");

      ((Dresseur) dresseur2).pokeEnVie--;
      if (((Dresseur) dresseur2).pokeEnVie > 0) {
        atk2 = new Echange((Dresseur) dresseur1, pok2, pok1);
        pok2 = ((Echange) atk2).echangeCombattant();
        System.out.println(dresseur2.getNom() + " sent " + pok2.getNom());
      }
    }
  }
}
