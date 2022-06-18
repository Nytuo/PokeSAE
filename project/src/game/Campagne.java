package game;

import dresseur.AIcomplexe;
import dresseur.AIsimple;
import dresseur.Dresseur;
import pokedex.Pokedex;
import pokemon.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static game.MainGame.showPokemon;

public class Campagne {

  private String[] AIsNames = {
    "Dimitry",
    "Covid-19 Lambda",
    "Covid-19 Gamma",
    "Covid-19 Beta",
    "Covid-19 Delta",
    "Covid-19 XD",
    "Covid-19",
    "Elyan GRELOT",
    "Etan TESLA",
    "Arnaud Beutanique",
    "Jules MARAIS",
  };

  public int storyLevel = 0;
  public int prestige = 0;
  Dresseur dresseur;
  public int storyNb = 11;

  ArrayList<Dresseur> AIs = new ArrayList<>();
  ArrayList<Dresseur> DevAI = new ArrayList<>();

  public int getStoryLevel() {
    return storyLevel;
  }

  public void setStoryLevel(int storyLevel) {
    this.storyLevel = storyLevel;
  }

  public int getPrestige() {
    return prestige;
  }

  public void setPrestige(int prestige) {
    this.prestige = prestige;
  }

  public Dresseur getDresseur() {
    return dresseur;
  }

  private Pokemon[] pokeForge(
      String name1,
      String name2,
      String name3,
      String name4,
      String name5,
      String name6,
      Pokedex pokedex) {
    Pokemon[] pokeList = new Pokemon[6];
    Species[] speciesList = new Species[6];
    speciesList[0] = (Species) pokedex.getInfo(name1);
    speciesList[1] = (Species) pokedex.getInfo(name2);
    speciesList[2] = (Species) pokedex.getInfo(name3);
    speciesList[3] = (Species) pokedex.getInfo(name4);
    speciesList[4] = (Species) pokedex.getInfo(name5);
    speciesList[5] = (Species) pokedex.getInfo(name6);

    for (int i = 0; i < pokeList.length; i++) {
      pokeList[i] =
          new Pokemon(
              speciesList[i].getNom(),
              speciesList[i].getNom(),
              (Types[]) speciesList[i].getTypes(),
              speciesList[i].baseStats,
              speciesList[i].startLevel,
              speciesList[i].evolution,
              speciesList[i].capacities,
              0.0,
              speciesList[i].getBaseExp(),
              capacitiesForge(speciesList[i]),
              speciesList[i].ID,
              (Stats) speciesList[i].getGainsStat());
    }
    return pokeList;
  }

  private Capacite[] capacitiesForge(Species esp) {
    Capacite[] capacitePoke = new Capacite[4];
    ArrayList<Capacite> capList = new ArrayList<Capacite>();
    int j = 0;
    while (capList.size() < 4) {
      Capacite currentCap = esp.capacities[(int) (Math.random() * (esp.capacities.length))];
      if (!capList.contains(currentCap)) {
        capList.add(currentCap);
        capacitePoke[j] = currentCap;
        j++;
      }
    }
    return capacitePoke;
  }

  private void finalBossForge(Pokedex pokedex) {
    Pokemon[] pokeG =
        pokeForge(
            "Métamorph", "Crustabri", "Lamantine", "Artikodin", "Lippoutou", "Fantominus", pokedex);
    Pokemon[] pokeB =
        pokeForge(
            "Florizarre", "Noadkoko", "Saquedeneu", "Empiflor", "Parasect", "Rafflesia", pokedex);
    Pokemon[] pokeT =
        pokeForge(
            "Électhor", "Voltali", "Élektek", "Électrode", "Magnéton", "Raichu", pokedex);
    Pokemon[] pokeM = pokeForge("Tortank", "Akwakwak", "Lokhlass", "Tentacruel", "Léviator", "Aquali", pokedex);

    DevAI.add(new AIcomplexe(AIsNames[AIsNames.length - 4], pokeG));
    DevAI.add(new AIcomplexe(AIsNames[AIsNames.length - 3], pokeT));
    DevAI.add(new AIcomplexe(AIsNames[AIsNames.length - 2], pokeB));
    DevAI.add(new AIcomplexe(AIsNames[AIsNames.length - 1], pokeM));
  }

  public Campagne(Dresseur dresseur, int storyLevel, int prestige) {
    this.storyLevel = storyLevel;
    this.prestige = prestige;
    this.dresseur = dresseur;
    Pokedex pokedex = new Pokedex();
    for (int i = 0; i < storyNb - 4; i++) {
      if (i < 3) this.AIs.add(new AIsimple(AIsNames[i], (Pokemon[]) pokedex.engendreRanch()));
      else this.AIs.add(new AIcomplexe(AIsNames[i], (Pokemon[]) pokedex.engendreRanch()));
    }
    finalBossForge(pokedex);
  }

  public void start() {
    if (this.storyLevel == storyNb) {
      while (true) {

        System.out.println(
            "\n\n<--------------------------[ Prestige "
                + (this.prestige + 1)
                + " ]--------------------------->\n");
        if (!story()) {
          break;
        } else {
          prestige++;
          if (wouldQuit()) return;
        }
      }

    } else {
      do {
        System.out.println(
            "\n\n<--------------------------[ Story "
                + (storyLevel + 1)
                + " ]--------------------------->\n");
        if (!story()) {
          break;
        } else {
          storyLevel++;
          if (wouldQuit()) return;
        }
      } while (storyLevel < storyNb);
      printCredits();
    }
  }

  private boolean wouldQuit() {
    Scanner continuer = new Scanner(System.in);
    System.out.println("\n\nWould you like to continue ? (y/n)");
    String reponse = continuer.nextLine();
    if (reponse.equals("y")) {
      return false;
    } else {
      System.out.println("[SAVE] - Saving...");
      MainGame.saveGame(
          dresseur.pokemons, dresseur.getNom(), MainGame.nbSave, storyLevel, prestige);
      System.out.println("[SAVE] - Save done");
      System.out.println("[INFO] - Exiting...");
      return true;
    }
  }

  private void printCredits() {
    System.out.println("\n\n<--------------------------[ Credits ]--------------------------->\n");
    System.out.println("Based on the Pokémon game created by Nintendo.");
    System.out.println("Pokémon is a trademark from Nintendo, Inc. All rights reserved.");
    System.out.println("For a project given by Leo DONATI.");
    System.out.println("Game Director: Arnaud BEUX");
    System.out.println("Conception by: Arnaud BEUX / Jules MAHÉ / Elyan GRUAU / Ethan TESTA");
    System.out.println("Game programmers: Arnaud BEUX / Jules MAHÉ / ELYAN GRUAU / Ethan TESTA");
    System.out.println("AI programmers: ELYAN GRUAU / Jules MAHÉ");
    System.out.println("Base classes by : Arnaud BEUX / Ethan TESTA");
    System.out.println("Dresseur / Combat / Tour by : Arnaud BEUX / Jules MAHÉ / Elyan GRUAU");
    System.out.println("Campaign by: Arnaud BEUX");
    System.out.println("Debugging by: Arnaud BEUX / Jules MAHÉ / Elyan GRUAU / Ethan TESTA");
    System.out.println("Documentation by: Arnaud BEUX / Jules MAHÉ / Elyan GRUAU / Ethan TESTA");
    System.out.println("Github and PDF by: Arnaud BEUX / Jules MAHÉ / Elyan GRUAU / Ethan TESTA\n");
    System.out.println("Thank you for playing!\n\n");

    System.out.println("\n\n<--------------------------[ End ]--------------------------->\n");
    System.out.println(
        "You have finished the story... Congratulation! But it's not over yet...\nYou can continue to play the campaign and make prestige, or, you can try the UTLIMATE WARRIORS mode !\nNo save, you start with base Pokemons and try to survive !\n\n");
  }

  private boolean story() {
    if (this.storyLevel < storyNb) {

      ArrayList<String> story = getStory(this.storyLevel);
      for (int i = 0; i < story.size(); i++) {
        System.out.println(story.get(i));
      }
    }

    System.out.println("\n\n" + "\u001B[32m" + "Press enter to fight !" + "\u001B[0m");
    try {
      System.in.read();
    } catch (Exception e) {
      System.out.println("error");
    }
    if (this.storyLevel < storyNb) {
      return combatStart(this.AIs.get(this.storyLevel));
    } else {
      return combatStart(this.AIs.get((int) Math.random() * storyNb));
    }
  }

  private ArrayList<String> getStory(int id) {
    ArrayList<String> story = new ArrayList<>();
    try {
      Scanner sc;
      try {
        sc = new Scanner(new File("project/stories/" + id + ".txt"));
      } catch (Exception e) {
        sc = new Scanner(new File("stories/" + id + ".txt"));
      }
      sc.useDelimiter("\n");
      while (sc.hasNext()) {
        story.add(sc.next());
      }
      sc.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: " + e.getMessage());
    }
    return story;
  }

  private boolean combatStart(Dresseur AI) {
    showPokemon(dresseur.pokemons);
    Combat combat = new Combat(dresseur, AI);
    combat.commence();
    return combat.gagnant == dresseur.getNom();
  }
}
