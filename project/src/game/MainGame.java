package game;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import javax.sound.sampled.*;
import pokedex.Pokedex;
import pokemon.*;

public class MainGame {
  public static void main(String[] args)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException,
          InterruptedException {
    playClip(new File("project/external/pokemonMainTitle.wav"));

    System.out.println("Welcome to the Pokemon Game!\n---------------------------------");
    System.out.println("Choose the gamemode:\n1. Single Player\n2. Multi Player");
    boolean selectGameMode = true;
    while (selectGameMode == true) {
      System.out.print("> ");
      Scanner scanner = new Scanner(System.in);
      int mode = scanner.nextInt();
      if (mode == 1 || mode == 2) {
        selectGameMode = false;
      } else {
        selectGameMode = true;
      }
      if (mode == 1) {
        System.out.println(
            "You have chosen Single Player mode.\n"
                + "Insert the slot number of your save (will be created if doesn't exist): ");
        System.out.print("> ");
        Pokemon[] pokes = loadSave(scanner.nextInt(), scanner);
        System.out.println("Loading complete.\n");

        System.out.println("Your first pokémon is " + pokes[0].getNom() + "!");
        System.out.println("Your second pokémon is " + pokes[1].getNom() + "!");
        System.out.println("Your third pokémon is " + pokes[2].getNom() + "!");
        System.out.println("Your fourth pokémon is " + pokes[3].getNom() + "!");
        System.out.println("Your fifth pokémon is " + pokes[4].getNom() + "!");
        System.out.println("Your sixth pokémon is " + pokes[5].getNom() + "!");

      } else if (mode == 2) {
        System.out.println(
            "Welcome to the Pokemon Game -- ULTIMATE WARRIORS!\n---------------------------------");
        Pokedex pokedex = new Pokedex();
        Pokemon[] pokes = (Pokemon[]) pokedex.engendreRanch();
        System.out.println("Your first pokémon is " + pokes[0].getNom() + "!");
        System.out.println("Your second pokémon is " + pokes[1].getNom() + "!");
        System.out.println("Your third pokémon is " + pokes[2].getNom() + "!");
        System.out.println("Your fourth pokémon is " + pokes[3].getNom() + "!");
        System.out.println("Your fifth pokémon is " + pokes[4].getNom() + "!");
        System.out.println("Your sixth pokémon is " + pokes[5].getNom() + "!");
        System.out.println("---------------------------------\n");

      } else {
        System.out.println("Invalid input. Please try again.");
        selectGameMode = true;
      }
    }
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
          "No save found.\n"
              + "Creating new save...\n"
              + " Please do not power off the computer during the process.");
      System.out.println(
          "Hi, I'm the professor Raoult! I'm here to give you your pokémons! But First things"
              + " first, what's your name ?\n");
      System.out.print("Please enter your name: ");
      Scanner scanner2 = new Scanner(System.in);
      String name = scanner2.nextLine();
      System.out.println("Hello " + name + "!\n");
      System.out.println("Are you ready to get your first pokemons?\n1. Yes\n2. No");
      System.out.print("> ");
      int ready = scanner.nextInt();
      if (ready == 1) {
        System.out.println("Great! Let's get started!\n");
      } else {
        System.out.println(
            "Oh, you're not ready yet? Anyway, I'm will give you your first pokémons!\n");
      }
      Pokedex pokedex = new Pokedex();
      pokes = (Pokemon[]) pokedex.engendreRanch();
      saveGame(pokes, name, saveNumber);

      System.out.println("Save created.");

    } else {
      System.out.println("Save found.\nLoading...");

      pokes = new Pokemon[6];
      for (int i = 0; i < 6; i++) {
        Pokedex pokedex = new Pokedex();
        Species z = (Species) pokedex.getInfo(data.get(i + 1)[0]);
        Capacite[] cap = new Capacite[4];
        for (int j = 0; j < 4; j++) {
          cap[j] = (Capacite) pokedex.getCapacite(data.get(i + 1)[2 + j]);
        }
        pokes[i] =
            new Pokemon(
                data.get(i)[0],
                data.get(i + 1)[1],
                (Types[]) z.getTypes(),
                (Stats) z.getBaseStat(),
                z.getBaseExp(),
                (TreeMap<Integer, String>) z.getEvolution(Integer.parseInt(data.get(i + 1)[7])),
                cap,
                Double.parseDouble(data.get(i + 1)[6]),
                (Capacite[]) z.getCapSet(),
                Integer.parseInt(data.get(i + 1)[8]),
                (Stats) z.getGainsStat());
      }

      System.out.println("Save loaded.");
    }

    return pokes;
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
