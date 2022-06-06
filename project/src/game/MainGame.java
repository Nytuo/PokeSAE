package game;

import dresseur.AIsimple;
import dresseur.Dresseur;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.*;
import pokedex.Pokedex;
import pokemon.*;

public class MainGame {

  static int nbSave = 0;

  public static void main(String[] args)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException,
          InterruptedException {
    playClip(new File("project/external/pokemonMainTitle.wav"));
    System.out.println(
        "                                 ,'\\\n"
            + "    _.----.        ____         ,'  _\\   ___    ___     ____\n"
            + "_,-'       `.     |    |  /`.   \\,-'    |   \\  /   |   |    \\  |`.\n"
            + "\\      __    \\    '-.  | /   `.  ___    |    \\/    |   '-.   \\ |  |\n"
            + " \\.    \\ \\   |  __  |  |/    ,','_  `.  |          | __  |    \\|  |\n"
            + "   \\    \\/   /,' _`.|      ,' / / / /   |          ,' _`.|     |  |\n"
            + "    \\     ,-'/  /   \\    ,'   | \\/ / ,`.|         /  /   \\  |     |\n"
            + "     \\    \\ |   \\_/  |   `-.  \\    `'  /|  |    ||   \\_/  | |\\    |\n"
            + "      \\    \\ \\      /       `-.`.___,-' |  |\\  /| \\      /  | |   |\n"
            + "       \\    \\ `.__,'|  |`-._    `|      |__| \\/ |  `.__,'|  | |   |\n"
            + "        \\_.-'       |__|    `-._ |              '-.|     '-.| |   |\n"
            + "                                `'                            '-._|");
    System.out.println("Welcome to the Pokemon Game!");
    boolean selectGameMode = true;
    while (selectGameMode) {
      System.out.println(
          "--------------------------------------------------------------------------------");
      System.out.println(
          "What do you want to do ?\n1. Go Single Player\n2. Go Multi Player\n3. View all pokémons\n4. Search a pokemon\n5. Exit");
      System.out.print("> ");
      Scanner scanner = new Scanner(System.in);
      int mode = scanner.nextInt();
      selectGameMode = mode != 1 && mode != 2;
      if (mode == 1) {
        System.out.println(
            "You have chosen Single Player mode.\n"
                + "Insert the slot number of your save (Between 1 and infinite, will be created if doesn't exist): ");
        System.out.print("> ");

        Pokemon[] pokes = loadSave(setSave(scanner), scanner);
        String dName = getNameFromSave(nbSave);

        Dresseur joueur = new Dresseur(dName, pokes);

        System.out.println("Loading complete.\n");

        showPokemon(pokes);

        Pokedex pokedex = new Pokedex();
        AIsimple dresseurIA = new AIsimple("dimitry", (Pokemon[]) pokedex.engendreRanch());

        Combat combat = new Combat(joueur, dresseurIA);
        combat.commence();

      } else if (mode == 2) {
        System.out.println(
            "\n------------------------------------------\nWelcome to the Pokemon Game -- ULTIMATE WARRIORS!\n------------------------------------------");
        System.out.print("Please enter your name: ");
        Scanner scanner2 = new Scanner(System.in);
        String name = scanner2.nextLine();
        Pokedex pokedex = new Pokedex();
        Pokemon[] pokes = (Pokemon[]) pokedex.engendreRanch();
        showPokemon(pokes);

        Dresseur joueur = new Dresseur(name, pokes);

        AIsimple dresseurAI = new AIsimple("Dimitry", (Pokemon[]) pokedex.engendreRanch());
        Combat combat = new Combat(joueur, dresseurAI);
        combat.commence();
      } else if (mode == 3) {
        System.out.println(new Pokedex().toString());

      } else if (mode == 4) {
        Scanner podexScan = new Scanner(System.in);
        System.out.print("Please enter the name (or id) of the pokemon you want to search: ");
        if (podexScan.hasNextInt()) {
          new Pokedex().searchPokemon(podexScan.nextInt());
        } else {
          new Pokedex().searchPokemon(podexScan.nextLine());
        }
      } else if (mode == 5) {
        System.out.println("See you next time !");
        System.exit(0);
      } else {
        System.out.println("Invalid input. Please try again.");
        selectGameMode = true;
      }
    }
  }

  private static void showPokemon(Pokemon[] pokes) {
    System.out.println(
        "Your first pokémon is " + pokes[0].getNom() + "!" + " Level : " + pokes[0].getNiveau());
    System.out.println(
        "Your second pokémon is "
            + pokes[1].getNom()
            + "!"
            + " Level : "
            + pokes[1].getNiveau());
    System.out.println(
        "Your third pokémon is " + pokes[2].getNom() + "!" + " Level : " + pokes[2].getNiveau());
    System.out.println(
        "Your fourth pokémon is "
            + pokes[3].getNom()
            + "!"
            + " Level : "
            + pokes[3].getNiveau());
    System.out.println(
        "Your fifth pokémon is " + pokes[4].getNom() + "!" + " Level : " + pokes[4].getNiveau());
    System.out.println(
        "Your sixth pokémon is " + pokes[5].getNom() + "!" + " Level : " + pokes[5].getNiveau());
  }

  public static int setSave(Scanner scanner) {

    nbSave = scanner.nextInt();
    return nbSave;
  }

  static ArrayList<String[]> getFromCSV(String filename) {
    ArrayList<String[]> data = new ArrayList<>();
    try {
      Scanner sc =
          new Scanner(new File(System.getenv("APPDATA") + "\\PokemonSAE\\" + filename + ".csv"));

      sc.useDelimiter(",");
      while (sc.hasNext()) {
        data.add(sc.nextLine().split(","));
      }
      sc.close();
    } catch (FileNotFoundException e) {
      return new ArrayList<String[]>();
    }
    return data;
  }

  private static void playClip(File clipFile)
      throws IOException, UnsupportedAudioFileException, LineUnavailableException,
          InterruptedException {
    class AudioListener implements LineListener {
      private boolean done = false;

      @Override
      public synchronized void update(LineEvent event) {
        LineEvent.Type eventType = event.getType();
        if (eventType == LineEvent.Type.STOP || eventType == LineEvent.Type.CLOSE) {
          done = true;
          notifyAll();
        }
      }

      public synchronized void waitUntilDone() throws InterruptedException {
        while (!done) {
          wait();
        }
      }
    }

    AudioListener listener = new AudioListener();
    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
    try {
      Clip clip = AudioSystem.getClip();
      clip.addLineListener(listener);
      clip.open(audioInputStream);
      try {
        clip.start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } finally {
      audioInputStream.close();
    }
  }

  static void writeToCSV(String filename, ArrayList<String[]> data) {
    try {
      new File(System.getenv("APPDATA") + "\\PokemonSAE\\").mkdirs();

      FileWriter fileWriter =
          new FileWriter(System.getenv("APPDATA") + "\\PokemonSAE\\" + filename + ".csv");
      BufferedWriter writer = new BufferedWriter(fileWriter);
      for (String[] line : data) {
        for (int i = 0; i < line.length; i++) {
          writer.write(line[i]);
          if (i < line.length - 1) {
            writer.write(",");
          }
        }
        writer.newLine();
      }
      writer.close();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static Pokemon[] loadSave(int saveNumber, Scanner scanner) {
    System.out.println("You have chosen Slot " + saveNumber + ".\nLoading...");
    ArrayList<String[]> data = getFromCSV("saveSlot" + saveNumber);
    Pokemon[] pokes;
    if (data.size() == 0) {
      System.out.println(
          "Hi, I'm the professor Raoult! Welcome to the world of Pokémon ! You love to fight against poor creatures, then you are at the good place!\n"
              + "I'm here to give you your pokémons! But First things first, what's your name ?\n");
      System.out.print("My name is : ");
      Scanner scanner2 = new Scanner(System.in);
      String name = scanner2.nextLine();

      System.out.println("\nHello " + name + "!\n");
      System.out.println("Are you ready to get your first pokemons?\n1. Yes\n2. No");
      System.out.print("> ");
      int ready = scanner.nextInt();

      if (ready == 1) {
        System.out.println("Great! Let's get started!\n");
      } else {
        System.out.println(
            "Oh, you're not ready yet? Anyway, your not the only one I have to see, so, take them!\n");
      }
      Pokedex pokedex = new Pokedex();
      pokes = (Pokemon[]) pokedex.engendreRanch();
      System.out.println(
          "Creating new save...\nPlease do not power off the computer during the process.");
      saveGame(pokes, name, saveNumber);

      System.out.println("Save created.");

    } else {
      System.out.println("Save found.\nLoading...");

      Pokedex pokedex = new Pokedex();

      pokes = new Pokemon[6];
      for (int i = 0; i < 6; i++) {

        Species esp = (Species) pokedex.getInfo(data.get(i + 1)[0]);
        Capacite[] caps = new Capacite[4];
        for (int j = 0; j < 4; j++) {
          caps[j] = (Capacite) pokedex.getCapacite(data.get(i + 1)[2 + j]);
        }

        pokes[i] =
            new Pokemon(
                data.get(i + 1)[0],
                data.get(i + 1)[1],
                (Types[]) esp.getTypes(),
                (Stats) esp.getBaseStat(),
                Integer.parseInt(data.get(i + 1)[7]),
                esp.evolution,
                (Capacite[]) esp.getCapSet(),
                Double.parseDouble(data.get(i + 1)[6]),
                esp.getBaseExp(),
                caps,
                Integer.parseInt(data.get(i + 1)[8]),
                (Stats) esp.getGainsStat());
      }

      System.out.println("Save loaded.");
    }
    return pokes;
  }

  static String getNameFromSave(int saveNumber) {
    ArrayList<String[]> data = getFromCSV("saveSlot" + saveNumber);
    return data.get(0)[0];
  }

  static void saveGame(Pokemon[] pokemon, String name, int Slotnb) {
    ArrayList<String[]> data = new ArrayList<>();
    data.add(new String[] {name});
    for (int i = 0; i < 6; i++) {
      String[] laLigne = new String[14];
      laLigne[0] = pokemon[i].getEspece().getNom();
      laLigne[1] = pokemon[i].getNom();
      laLigne[2] = pokemon[i].getCapacitesApprises()[0].getNom();
      laLigne[3] = pokemon[i].getCapacitesApprises()[1].getNom();
      laLigne[4] = pokemon[i].getCapacitesApprises()[2].getNom();
      laLigne[5] = pokemon[i].getCapacitesApprises()[3].getNom();
      laLigne[6] = String.valueOf(pokemon[i].getExperience());
      laLigne[7] = String.valueOf(pokemon[i].getNiveau());
      laLigne[8] = String.valueOf(pokemon[i].getId());
      laLigne[9] = String.valueOf(pokemon[i].getStat().getPV());
      laLigne[10] = String.valueOf(pokemon[i].getStat().getForce());
      laLigne[11] = String.valueOf(pokemon[i].getStat().getDefense());
      laLigne[12] = String.valueOf(pokemon[i].getStat().getSpecial());
      laLigne[13] = String.valueOf(pokemon[i].getStat().getVitesse());
      data.add(laLigne);
    }

    writeToCSV("saveSlot" + Slotnb, data);
  }
}
