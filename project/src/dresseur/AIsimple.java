package dresseur;

import interfaces.IAttaque;
import interfaces.IPokemon;
import interfaces.IStrategy;
import java.util.ArrayList;
import java.util.Random;
import pokemon.Capacite;
import pokemon.Pokemon;

/**
 * Une strat�gie est utilis�e par les dresseurs non humains (IA) pour prendre les d�cisions Un
 * DresseurIA poss�de une r�f�rence sur une IStrategy � qui il d�l�gue la prise de d�cision Un
 * dresseur humain n'utilise pas IStrategy
 *
 * <p>Chaque m�thode de IStrategy correspond � la m�thode homonyme de IDresseur
 */
public class AIsimple extends Dresseur implements IStrategy {
  public AIsimple(String name, int level, ArrayList<Pokemon> pokemons) {
    super(name, level, pokemons);
  }

  @Override
  public IPokemon choisitCombattant() {
    // TODO Auto-generated method stub
    return pokemons.get(0);
  }

  @Override
  public IPokemon choisitCombattantContre(IPokemon pok) {
    // TODO Auto-generated method stub
    Random r = new Random();
    int numPok = r.nextInt((5));

    return pokemons.get(numPok);
  }

  @Override
  public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
    Capacite[] capList =
        (Capacite[]) attaquant.getCapacitesApprises(); // R�cup�re toutes les capacit�s de
    // l'attaquant
    Random r = new Random();
    int numAttaque = r.nextInt((3)); // Choisit un nombre al�atoire entre 0 et 3
    return capList[numAttaque];
  }
}
