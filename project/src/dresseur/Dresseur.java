package dresseur;

import game.Echange;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;
import pokedex.Pokedex;
import pokemon.Capacite;
import pokemon.Pokemon;

import java.util.Scanner;

/**
 * La classe Dresseur représente un dresseur caractérisé par : un nom, un niveau (somme des niveaux
 * des pokémons et une liste de pokemons. Dresseur implémente l'interface IDresseur.
 */
public class Dresseur implements IDresseur {
  /** Le nom du dresseur */
  public String name;

  /** Le niveau du dresseur */
  public int level = 0;

  /** La liste des pokemons */
  public Pokemon[] pokemons;

  /** Nombre d'échanges restants pour le dresseur pendant le Combat */
  public int echangeRestant = 5;

  /** Le nombre de pokémons du Dresseur encore en vie */
  public int pokeEnVie = 6;

  /**
   * Le constructeur de la classe Dresseur initialise le niveau du dresseur en fonction des
   * pokémons, son nom et la liste de pokémons
   *
   * @param name Le nom du dresseur
   * @param pokemons La liste des pokemons du dresseur
   */
  public Dresseur(String name, Pokemon[] pokemons) {

    this.name = name;
    this.pokemons = pokemons;

    for (Pokemon pok : pokemons) {
      this.level += pok.getNiveau();
    }
  }

  /**
   * Permet au dresseur de faire apprendre de nouvelles capacitées à un de ses pokémons
   *
   * @param pok Le pokémon qui apprendra la nouvelle capacité
   * @param caps La liste des capacités disponibles à l'apprentissage
   */
  @Override
  public void enseigne(IPokemon pok, ICapacite[] caps) {

    pok.apprendCapacites(caps);
  }

  /** Permet de soigner l'ensemble des pokémons du dresseur */
  @Override
  public void soigneRanch() {
    for (Pokemon pok : pokemons) {
      pok.soigne();
    }
  }

  /**
   * Méthode qui permet de choisir le premier pokemon du dresseur lors 1er tour de combat
   *
   * @return Le premier pokemon de la liste des pokemons du dresseur
   */
  @Override
  public IPokemon choisitCombattant() {
    Scanner scanner3 = new Scanner(System.in);

    int numPok = 0;

    while ((numPok < 1 || numPok > 6)) {

      System.out.print("\nWhich pokemon will you send first ?\n> ");
      numPok = scanner3.nextInt();
      System.out.println();
    }
    return getPokemon(numPok - 1);
  }

  /**
   * Permet à l'utilisateur de choisir un pokemon de remplacement contre un pokémon ennemi
   *
   * @param pok Le pokémon ennemi
   * @return Le pokemon choisi par l'utilisateur (non évanoui)
   */
  @Override
  public IPokemon choisitCombattantContre(IPokemon pok) {

    Scanner scanner3 = new Scanner(System.in);
    String spaceCarac = " ";
    int i = 1;
    System.out.print("\nWhich pokemon will you send against " + pok.getNom() + " ?\n");
    int maxPokeNameLength = 0;
    afficherPokeKO(i, maxPokeNameLength, spaceCarac);

    int numPok = Integer.parseInt(scanner3.nextLine());
    if (getPokemon(numPok - 1).estEvanoui()) {
      System.out.println("Argh, " + getPokemon(numPok - 1).getNom() + " is KO !");
      numPok = 0;
    }
    while ((numPok < 1 || numPok > 6)) {

      System.out.print("\nWhich pokemon will you send against " + pok.getNom() + " ?\n");
      afficherPokeKO(i, maxPokeNameLength, spaceCarac);

      numPok = scanner3.nextInt();
      if (getPokemon(numPok - 1).estEvanoui()) {
        System.out.println("Argh, " + getPokemon(numPok - 1).getNom() + " is KO !");
        numPok = 0;
      }
    }

    return getPokemon(numPok - 1);
  }

  /**
   * Permet d'afficher les pokémons avec leur état
   *
   * @param i
   * @param maxPokeNameLength
   * @param spaceCarac
   */
  private void afficherPokeKO(int i, int maxPokeNameLength, String spaceCarac) {
    // TODO changer les paramètres.
    for (IPokemon poke : this.pokemons) {
      int pokeNameLength = poke.getNom().length();
      if (pokeNameLength > maxPokeNameLength) {
        maxPokeNameLength = pokeNameLength;
      }
    }

    for (IPokemon poke : this.pokemons) {
      String down = "";
      if (poke.estEvanoui()) {
        down = " [KO]";
      }
      System.out.println(
          "    "
              + i
              + " - "
              + poke.getNom()
              + spaceCarac.repeat(maxPokeNameLength - poke.getNom().length())
              + down);
      i++;
    }
  }

  /**
   * Permet de choisir une attaque à utiliser par un pokémon contre un pokémon ennemi Gère également
   * le cas de l'échange de pokémon L'attaque sera "Lutte" si le pokémon n'a plus de PP sur ses
   * capacités
   *
   * @param attaquant le pokémon du dresseur
   * @param defenseur le pokémon ennemi
   * @return l'attaque choisie
   */
  @Override
  public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
    System.out.println("Choose the move to use : ");
    Capacite[] capList = (Capacite[]) attaquant.getCapacitesApprises(); // Récupère toutes les
    // capacités de
    // l'attaquant
    String msg = "change pokemon";
    int maxCapLength = msg.length();
    String spaceCarac = " ";
    Scanner scanner = new Scanner(System.in);
    int i = 1;
    
      // Gestion de l'affichage
    for (ICapacite cap : attaquant.getCapacitesApprises()) {
      int capLength = cap.getNom().length();
      if (capLength > maxCapLength) {
        maxCapLength = capLength;
      }
    }

    for (ICapacite cap : attaquant.getCapacitesApprises()) {
      int capLength = cap.getNom().length();
      String newSpaceCarac = spaceCarac.repeat(maxCapLength - capLength + 1);
      System.out.println(
          "    " + i + " : " + cap.getNom() + newSpaceCarac + "|   PP : " + cap.getPP());
      i++;
    }
    
    if (echangeRestant > 0) {
      System.out.println(
          "    "
              + i
              + " : "
              + msg
              + " ".repeat(maxCapLength - msg.length() + 1)
              + "|   left : "
              + this.echangeRestant);
    }
    System.out.println(
        "    " + (i + 1) + " : Consult the Pokedex ".repeat(maxCapLength - msg.length() + 1) + "\n");

    int numAttaque;
    do {
      System.out.print("> ");
      numAttaque = scanner.nextInt();
      try {
        if (numAttaque == 6) {
          Scanner podexScan = new Scanner(System.in);
          System.out.print("Please enter the name (or id) of the pokemon you want to search: ");
          if (podexScan.hasNextInt()) {
            new Pokedex().searchPokemon(podexScan.nextInt());
          } else {
            new Pokedex().searchPokemon(podexScan.nextLine());
          }
          System.out.println("Thanks for using the Pokedex !\n---------------------------------\nChoose the move to use : ");
          numAttaque = 0;
        }
        if ((numAttaque == 5) && (echangeRestant < 1)) {
          System.out.println("You can't change pokemon anymore");
          numAttaque = 0;
        }
        if (capList[numAttaque - 1].getPP() <= 0) {
          System.out.println(
              "Argh, " + capList[numAttaque - 1].getNom() + " cannot be used anymore !");
          numAttaque = 0;
        }

      } catch (ArrayIndexOutOfBoundsException ignored) {

      }
    } while ((numAttaque == 5 && echangeRestant < 1) || numAttaque < 1 || numAttaque > 5);

    if ((numAttaque == 5)) {
      return new Echange(this, attaquant, defenseur);
    }

    return capList[numAttaque - 1];
  }

  /**
   * Permet d'obtenir le niveau du Dresseur (somme des niveaux de tous ses pokémons)
   *
   * @return le niveau du Dresseur
   */
  @Override
  public int getNiveau() {

    return this.level;
  }

  /**
   * Permet d'obtenir le nom du Dresseur
   *
   * @return le nom du Dresseur
   */
  @Override
  public String getNom() {

    return this.name;
  }

  /**
   * Permet d'obtenir le pokémon au rang i dans la liste des pokémons du dresseur
   *
   * @param i le rang du pokémon dans la liste
   * @return le pokémon au rang i
   */
  @Override
  public IPokemon getPokemon(int i) {

    return pokemons[i];
  }

  /**
   * Permet de savoir si tout les pokemons du dresseur sont évanouis
   *
   * @return true si tout les pokémons sont évanouis, false sinon
   */
  public Boolean allDown() {

    for (Pokemon pok : this.pokemons) {
      if (!pok.estEvanoui()) {
        return false;
      }
    }
    return true;
  }

  public Pokemon[] getRanchCopy() {
    Pokemon[] pokecopy = new Pokemon[6];
    System.arraycopy(this.pokemons, 0, pokecopy, 0, this.pokemons.length);
    return pokecopy;
  }

  public int getPokeEnVie() {
    return this.pokeEnVie;
  }
}
