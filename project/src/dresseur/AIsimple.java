package dresseur;

import interfaces.IAttaque;
import interfaces.IPokemon;
import interfaces.IStrategy;
import java.util.Random;
import pokedex.Pokedex;
import pokemon.Capacite;
import pokemon.Pokemon;

/**
 * Une stratégie est utilisée par les dresseurs non humains (IA) pour prendre les décisions Un
 * DresseurIA possède une référence sur une IStrategy à qui il délègue la prise de décision Un
 * dresseur humain n'utilise pas IStrategy
 *
 * <p>Chaque méthode de IStrategy correspond à la méthode homonyme de IDresseur
 */
public class AIsimple extends Dresseur implements IStrategy {
  public AIsimple(String name, Pokemon[] pokemons) {
    super(name, pokemons);
  }

  @Override
  public IPokemon choisitCombattant() {
    // TODO Auto-generated method stub
    return getPokemon(0);
  }

  @Override
  public IPokemon choisitCombattantContre(IPokemon pok) {
    // TODO Auto-generated method stub
    Random r = new Random();

    int numPok = 6;
    while (numPok == 6) {

      numPok = r.nextInt((5));

      if (getPokemon(numPok).estEvanoui()) {
        numPok = 6;
      }
    }

    return getPokemon(numPok);
  }

  @Override
  public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
    Capacite[] capList =
        (Capacite[])
            attaquant.getCapacitesApprises(); // Récupère toutes les capacités de l'attaquant
    Random r = new Random();
    int numAttaque = -1;
    int nbCap = 3 - Boolean.compare(false, echangeRestant > 0);

    while (numAttaque < 0 || numAttaque > nbCap) {
      numAttaque = r.nextInt((nbCap));
      ;
      if (capList[numAttaque].getPP() <= 0) {
        numAttaque = -1;
        if (capList[0].getPP() <= 0
            && capList[1].getPP() <= 0
            && capList[2].getPP() <= 0
            && capList[3].getPP() <= 0) {
          return (IAttaque) new Pokedex().getCapacite("Lutte");
        }
      }
      System.out.println();
    }

    return capList[numAttaque];
  }
}
