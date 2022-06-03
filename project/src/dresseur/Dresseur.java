package dresseur;

import game.Echange;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;
import java.util.ArrayList;
import java.util.Scanner;
import pokemon.Capacite;
import pokemon.Pokemon;

public class Dresseur implements IDresseur {
  public String name;
  public int level = 0;
  public ArrayList<Pokemon> pokemons;

  public int echangeRestant = 5;

  public Dresseur(String name, int level, ArrayList<Pokemon> pokemons) {

    this.name = name;
    this.pokemons = pokemons;

    for (Pokemon pok : pokemons) {
      this.level += pok.getNiveau();
    }
  }

  @Override
  public void enseigne(IPokemon pok, ICapacite[] caps) {

    pok.apprendCapacites(caps);
  }

  @Override
  public void soigneRanch() {
    for (Pokemon pok : pokemons) {
      pok.soigne();
    }
  }

  @Override
  public IPokemon choisitCombattant() {

    return getPokemon(0);
  }

  @Override
  public IPokemon choisitCombattantContre(IPokemon pok) {

    Scanner scanner = new Scanner(System.in);

    System.out.print("Which pokemon will you send against \n" + pok + "\n" + this.pokemons);
    int numPok = scanner.nextInt();
    System.out.println();

    scanner.close();

    return pokemons.get(numPok);
  }

  @Override
  public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {

    Capacite[] capList =
        (Capacite[]) attaquant.getCapacitesApprises(); // Rï¿½cupï¿½re toutes les capacitï¿½s de
    // l'attaquant

    Scanner scanner = new Scanner(System.in);

    System.out.print(
        "Choose the move to use\n" + attaquant.getCapacitesApprises() + "4 : change pokemon");
    int numAttaque = scanner.nextInt();
    System.out.println();

    scanner.close();

    if (numAttaque == 4 && echangeRestant > 0) {

      return new Echange(this, attaquant, defenseur);
    }
    return capList[numAttaque];
  }

  @Override
  public int getNiveau() {

    return this.level;
  }

  @Override
  public String getNom() {

    return this.name;
  }

  @Override
  public IPokemon getPokemon(int i) {

    return pokemons.get(i);
  }
}
