package game;

import dresseur.Dresseur;
import interfaces.IEchange;
import interfaces.IPokemon;

public class Echange implements IEchange {

  Dresseur dresseur;

  IPokemon oldPok;
  IPokemon newPok;
  IPokemon defenseur;

  public Echange(Dresseur dresseur, IPokemon oldPok, IPokemon defenseur) {

    this.dresseur = dresseur;
    this.oldPok = oldPok;
    this.defenseur = defenseur;
  }

  @Override
  public int calculeDommage(IPokemon lanceur, IPokemon receveur) {

    return 0;
  }

  @Override
  public void utilise() {

    this.dresseur.echangeRestant--;
  }

  @Override
  public void setPokemon(IPokemon pok) {

    this.newPok = pok;
  }

  @Override
  public IPokemon echangeCombattant() {

    this.setPokemon(this.dresseur.choisitCombattantContre(defenseur));

    return this.newPok;
  }
}
