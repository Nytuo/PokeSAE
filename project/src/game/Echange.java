package game;

import dresseur.Dresseur;
import interfaces.IEchange;
import interfaces.IPokemon;

/** La classe Echange gère les échanges. Elle implémente l'interface IEchange. */
public class Echange implements IEchange {

  /** Le dresseur faisant l'échange. */
  Dresseur dresseur;

  /** L'ancien pokémon du dresseur */
  IPokemon oldPok;

  /** Le nouveau pokémon du dresseur */
  IPokemon newPok;

  /** Le pokemon adverse, défenseur */
  IPokemon defenseur;

  /**
   * le constructeur de la classe Echange.
   *
   * @param dresseur Dresseur faisant l'échange
   * @param oldPok Ancien pokémon du dresseur
   * @param defenseur Pokemon adverse, défenseur
   */
  public Echange(Dresseur dresseur, IPokemon oldPok, IPokemon defenseur) {

    this.dresseur = dresseur;
    this.oldPok = oldPok;
    this.defenseur = defenseur;
  }

  /**
   * Calcule des dommages infligés
   *
   * @param lanceur Pokemon attaquant
   * @param receveur Pokemon défenseur
   * @return Dommages infligés (0 car l'echange ne fait pas de dégats)
   */
  @Override
  public int calculeDommage(IPokemon lanceur, IPokemon receveur) {

    return 0;
  }

  /** Utilise un échange disponible sur le dresseur */
  @Override
  public void utilise() {

    this.dresseur.echangeRestant--;
  }

  /**
   * Set le nouveau pokémon du dresseur
   *
   * @param pok Pokemon à échanger
   */
  @Override
  public void setPokemon(IPokemon pok) {

    this.newPok = pok;
  }

  /**
   * Echange du pokémon
   *
   * @return Le nouveau pokémon du dresseur
   */
  @Override
  public IPokemon echangeCombattant() {

    this.setPokemon(this.dresseur.choisitCombattantContre(defenseur));

    return this.newPok;
  }
}
