package game;

import interfaces.IAttaque;
import interfaces.ICombat;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;

public class Combat implements ICombat {

  IDresseur dresseur1;
  IDresseur dresseur2;
  ITour tour;

  public Combat(IDresseur dresseur1, IDresseur dresseur2) {
    this.dresseur1 = dresseur1;
    this.dresseur2 = dresseur2;
  }

  @Override
  public void commence() {

    IPokemon pok1 = dresseur1.choisitCombattant();
    IPokemon pok2 = dresseur2.choisitCombattant();

    IAttaque atk1 = dresseur1.choisitAttaque(pok1, pok2);
    IAttaque atk2 = dresseur2.choisitAttaque(pok2, pok1);

    tour = nouveauTour(pok1, atk1, pok2, atk2);
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

    tour.commence();

    if (pok1.getStat().getVitesse() < pok2.getStat().getVitesse()) {

      attaque(this.dresseur1, pok1, atk1, pok2, atk2);
      attaque(this.dresseur2, pok2, atk2, pok1, atk1);
    } else {

      attaque(this.dresseur2, pok2, atk2, pok1, atk1);
      attaque(this.dresseur1, pok1, atk1, pok2, atk2);
    }

    return this.nouveauTour(pok1, atk1, pok2, atk2);
  }

  public void attaque(
      IDresseur dresseur1, IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {

    atk1.utilise();
    if (atk1.getClass() == Echange.class) {
      pok1 = ((Echange) atk1).echangeCombattant();
    } else {
      pok1.subitAttaqueDe(pok2, atk2);

      if (pok1.estEvanoui()) {
        this.dresseur1.choisitCombattantContre(pok2);
      }
    }
  }

  @Override
  public void termine() {
    // TODO Auto-generated method stub

  }
}
