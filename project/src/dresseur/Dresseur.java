package dresseur;

import game.Echange;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;
import java.util.Scanner;
import pokedex.Pokedex;
import pokemon.Capacite;
import pokemon.Pokemon;

public class Dresseur implements IDresseur {
  public String name;
  public int level = 0;
  public Pokemon[] pokemons;

  public int echangeRestant = 5;

  public int pokeEnVie = 6;

  public Dresseur(String name, Pokemon[] pokemons) {

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

    Scanner scanner3 = new Scanner(System.in);

    int i = 0;
    for (IPokemon poke : this.pokemons) {
      System.out.println(i + " : " + poke.getNom());
      i++;
    }

    int numPok = Integer.parseInt(scanner3.nextLine());

    while ((numPok < 0 || numPok > 5)) {

      System.out.print("\nWhich pokemon will you send against " + pok.getNom() + "\n");
      numPok = scanner3.nextInt();
      System.out.println();
      if (getPokemon(numPok).estEvanoui()) {
        System.out.println("Argh, " + getPokemon(numPok).getNom() + " is KO !");
        numPok = 6;
      }
    }

    return getPokemon(numPok);
  }

  @Override
  public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {

    Capacite[] capList = (Capacite[]) attaquant.getCapacitesApprises(); // Récupère toutes les
    // capacités de
    // l'attaquant

    Scanner scanner = new Scanner(System.in);
    int i = 0;

    if (capList[0].getPP() <= 0
        && capList[1].getPP() <= 0
        && capList[2].getPP() <= 0
        && capList[3].getPP() <= 0) {
      for (i = 0; i < 4; i++) {
        System.out.println(i + " : Lutte");
      }
    } else {
      for (ICapacite cap : attaquant.getCapacitesApprises()) {
        System.out.println(i + " : " + cap.getNom() + "	PP : " + cap.getPP());
        i++;
      }
    }
    if (echangeRestant > 0) {
      System.out.println("4 : change pokemon	left : " + this.echangeRestant + "\n");
    }

    int numAttaque = Integer.parseInt(scanner.nextLine());

    while (numAttaque < 0 || numAttaque > (3 - Boolean.compare(false, echangeRestant > 0))) {
      System.out.print("Choose the move to use :\n");
      numAttaque = scanner.nextInt();
      if (capList[numAttaque].getPP() <= 0) {
        System.out.println(
            "Argh, " + getPokemon(numAttaque).getNom() + " cannot be used anymore !");
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

    if ((numAttaque == 4) && (echangeRestant > 0)) {

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

    return pokemons[i];
  }

  public Boolean allDown() {

    for (Pokemon pok : this.pokemons) {
      if (!pok.estEvanoui()) {
        return false;
      }
    }
    return true;
  }
}
