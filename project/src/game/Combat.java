package game;

import dresseur.Dresseur;
import interfaces.IAttaque;
import interfaces.ICombat;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;
import pokemon.Capacite;
import pokemon.Pokemon;

public class Combat implements ICombat {

  IDresseur dresseur1;
  IDresseur dresseur2;
  ITour tour;

  static IPokemon pok1;
  static IPokemon pok2;

  static IAttaque atk1;
  static IAttaque atk2;

  String gagnant;

  public Combat(IDresseur dresseur1, IDresseur dresseur2) {
    this.dresseur1 = dresseur1;
    this.dresseur2 = dresseur2;
  }

  @Override
  public void commence() {

    System.out.println("\n\n" + dresseur1.getNom() + " VS " + dresseur2.getNom() + "\n");

    Combat.pok1 = dresseur1.choisitCombattant();
    Combat.pok2 = dresseur2.choisitCombattant();

    System.out.println(Combat.pok1.getNom() + " VS " + Combat.pok2.getNom() + "\n");

    while ((((Dresseur) dresseur1).pokeEnVie > 0) && (((Dresseur) dresseur2).pokeEnVie > 0)) {

      Combat.atk1 = dresseur1.choisitAttaque(Combat.pok1, Combat.pok2);
      Combat.atk2 = dresseur2.choisitAttaque(Combat.pok2, Combat.pok1);
      if (Combat.atk1.getClass() == Echange.class) {
        System.out.println(dresseur1.getNom() + " used swap");
        Combat.pok1 = ((Echange) Combat.atk1).echangeCombattant();
        System.out.println(dresseur1.getNom() + " sent " + Combat.pok1.getNom());
      }
      if (Combat.atk2.getClass() == Echange.class) {
        System.out.println(dresseur2.getNom() + " used swap");
        Combat.pok2 = ((Echange) Combat.atk2).echangeCombattant();
        System.out.println(dresseur2.getNom() + " sent " + Combat.pok1.getNom());
      }

      nouveauTour(Combat.pok1, Combat.atk1, Combat.pok2, Combat.atk2).commence();
    }

    setGagnant();

    this.termine();
  }

  private void setGagnant() {

    if (((Dresseur) dresseur2).pokeEnVie == 0) {
      gagnant = dresseur1.getNom();
    } else {
      gagnant = dresseur2.getNom();
    }
  }

  @Override
  public IDresseur getDresseur1() {

    return dresseur1;
  }

  @Override
  public IDresseur getDresseur2() {

    return dresseur2;
  }

  @Override
  public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {

    return new Tour(pok1, atk1, pok2, atk2, dresseur1, dresseur2);
  }

  @Override
  public void termine() {

    Dresseur dresseur1 = (Dresseur) getDresseur1();

    System.out.println(gagnant + " won the fight");

    resetAllandEvolve(dresseur1);

    MainGame.saveGame(dresseur1.pokemons, dresseur1.name, MainGame.nbSave);
  }

  public void resetAllandEvolve(Dresseur dresseur) {

    dresseur.echangeRestant = 5;
    dresseur.soigneRanch();

    for (Pokemon pok : dresseur.pokemons) {

      for (Capacite cap : pok.capacities) {
        cap.resetPP();
      }
      if (pok.peutMuter()) {
        System.out.println("Quoi ?!\n" + pok.getNom() + " évolue ?!");
        pok.vaMuterEn(pok.getEvolution(pok.getNiveau()));
        System.out.println("Iel est devenu un " + pok.getNom());
      }
    }
  }
}
