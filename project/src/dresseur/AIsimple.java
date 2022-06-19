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
 * dresseur humain n'utilise pas IStrategy. Chaque méthode de IStrategy correspond à la méthode
 * homonyme de IDresseur
 */
public class AIsimple extends Dresseur implements IStrategy {
  /**
   * Constructeur de la classe AIsimple qui se charge de remplir le constructeur de Dresseur
   *
   * @param name Nom du dresseur (IA)
   * @param pokemons Liste des pokemons du dresseur (IA)
   */
  public AIsimple(String name, Pokemon[] pokemons) {
    super(name, pokemons);
  }

  /**
   * Méthode qui permet de choisir le premier pokemon du dresseur (IA) lors 1er tour de combat
   *
   * @return Le premier pokemon de la liste des pokemons du dresseur (IA)
   */
  @Override
  public IPokemon choisitCombattant() {
    return getPokemon(0);
  }

  /**
   * Méthode qui permet de choisir un pokemon au hasard dans la liste des pokemons du dresseur (IA)
   * (pendant le tour)
   *
   * @param pok Pokemon Adverse
   * @return Un pokemon au hasard dans la liste des pokemons du dresseur (IA) (non évanouie)
   */
  @Override
  public IPokemon choisitCombattantContre(IPokemon pok) {
    // TODO Auto-generated method stub
    Random r = new Random();

    int numPok = 6;
    while (numPok == 6) {

      numPok = r.nextInt((6));

      if (getPokemon(numPok).estEvanoui()) {
        numPok = 6;
      }
    }

    return getPokemon(numPok);
  }

  /**
   * Méthode qui permet de choisir une attaque au hasard dans la liste des attaques du pokemon (IA).
   * La capacité choisie doit avoir des PPs restants.
   *
   * @param attaquant Pokemon du dresseur (IA)
   * @param defenseur Pokemon Adverse
   * @return Une attaque au hasard dans la liste des attaques du pokemon (IA), sous conditions
   */
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
    }

    return capList[numAttaque];
  }
}
