package game;

import dresseur.AIcomplexe;
import dresseur.AIsimple;
import dresseur.Dresseur;
import java.io.*;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.*;

import interfaces.IDresseur;
import pokedex.Pokedex;
import pokemon.*;

/** Classe de lancement du jeu et menu principal */
public class MainGame {

  /** Numéro de la sauvegarde sélectionner */
  static int nbSave = 0;

  static int difficulty = 0;

  /**
   * methode main de lancement du menu
   *
   * @param args les arguments de la ligne de commande
   * @throws UnsupportedAudioFileException exception levée si le format de l'audio n'est pas
   *     supporté
   * @throws LineUnavailableException exception levée si la ligne audio n'est pas disponible
   * @throws IOException exception levée si une erreur d'entrée/sortie est rencontrée
   * @throws InterruptedException exception levée si l'exécution du programme est interrompue
   */
  public static void main(String[] args)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException,
          InterruptedException {
    try {

      playClip(new File("external/pokemonMainTitle.wav"));
    } catch (FileNotFoundException e) {
      try {
        playClip(new File("project/external/pokemonMainTitle.wav"));

      } catch (Exception e2) {
        System.out.println("Error while playing sound");
      }
    }
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
          "————————————————————————————————————————————————————————————————————————————————");
      System.out.println(
          "What do you want to do ?\n1. Campaign\n2. Go ULTIMATE WARRIORS\n3. View all pokémons\n4. Search a pokemon\n5. Exit");
      System.out.print("> ");
      Scanner scanner = new Scanner(System.in);
      int mode = scanner.nextInt();
      selectGameMode = mode != 1 && mode != 2;
      if (mode == 1) { // Campagne
        System.out.println(
            "You have chosen Campaign mode.\n"
                + "Insert the slot number of your save (Between 1 and infinite, will be created if doesn't exist): ");
        System.out.print("> ");

        Pokemon[] pokes = loadSave(setSave(scanner), scanner);
        String dName = getNameFromSave(nbSave);
        int dLevel = getLevelFromSave(nbSave);
        int dPrestige = getPrestigeFromSave(nbSave);
        System.out.println("AI autopilot ? (y/n)");
        String autopilot = scanner.next();
        boolean autopilotBool = autopilot.equals("y");
        Dresseur joueur;
        if (autopilotBool) {
          joueur = new AIcomplexe(dName, pokes, 3);

        } else {
          joueur = new Dresseur(dName, pokes);
        }

        System.out.println("[SAVE] - Loading complete.\n");

        Campagne campagne = new Campagne(joueur, dLevel, dPrestige);
        campagne.start();

      } else if (mode == 2) { // Multijoueur
        System.out.println(
            "\n————————————————————————————————————————————————\nWelcome to ULTIMATE WARRIORS!\n————————————————————————————————————————————————");
        System.out.print("Please enter your name: ");
        Scanner scanner2 = new Scanner(System.in);
        String name = scanner2.nextLine();
        Pokedex pokedex = new Pokedex();
        Pokemon[] pokes = (Pokemon[]) pokedex.engendreRanch();
        showPokemon(pokes);

        System.out.println("select the difficulty : \n");
        System.out.println("1. I'm too young to die");
        System.out.println("2. Hey, not too rough");
        System.out.println("3. Hurt me plenty");
        System.out.println("4. Ultra-Violence (not implemented)");

        Scanner diffScan = new Scanner(System.in);
        int difficulty = diffScan.nextInt();
        if (difficulty < 1 || difficulty > 4) {

          System.out.println("Too hard to choose? Then I'll choose for you!");
          difficulty = 3;
        }

        System.out.println("AI autopilot ? (y/n)");
        String autopilot = scanner2.next();
        boolean autopilotBool = autopilot.equals("y");
        IDresseur joueur;
        int nbPlayerInt = 0;
        ArrayList<IDresseur> adversaires = new ArrayList<>();
        if (autopilotBool) {
          joueur = new AIcomplexe(name, pokes, 3);
          adversaires.add(joueur);

        } else {
          System.out.println("How many players ? (1 minimum) : ");
          Scanner nbPlayer = new Scanner(System.in);
          nbPlayerInt = nbPlayer.nextInt();
          if (nbPlayerInt < 1) {
            nbPlayerInt = 1;
          }

          for (int i = 0; i < nbPlayerInt; i++) {
            if (i == 0) joueur = new Dresseur(name, pokes);
            else {
              System.out.println("Player " + (i + 1) + " name : ");
              String playerName = scanner2.next();
              joueur = new Dresseur(playerName, (Pokemon[]) pokedex.engendreRanch());
            }
            adversaires.add(joueur);
          }
        }

        for (int i = 0; i < 31 - nbPlayerInt; i++) {

          IDresseur dresseurAI;
          if (difficulty == 1) {
            dresseurAI =
                new AIcomplexe(
                    "Dimitry clone " + i, (Pokemon[]) pokedex.engendreRanch(), difficulty);
          } else if (difficulty == 2) {
            dresseurAI =
                new AIcomplexe(
                    "Dimitry clone " + i, (Pokemon[]) pokedex.engendreRanch(), difficulty);
          } else if (difficulty == 3) {
            dresseurAI =
                new AIcomplexe(
                    "Dimitry clone " + i, (Pokemon[]) pokedex.engendreRanch(), difficulty);
          } else if (difficulty == 4) {
            dresseurAI =
                new AIcomplexe(
                    "Dimitry clone " + i, (Pokemon[]) pokedex.engendreRanch(), difficulty);
          } else {
            System.out.println("Too difficult to choose? I'll choose for you!");
            dresseurAI =
                new AIcomplexe("Dimitry clone " + i, (Pokemon[]) pokedex.engendreRanch(), 3);
          }
          adversaires.add(dresseurAI);
        }

        System.out.println("[TOURNAMENT] -- 32 opponents are ready to fight!");
        for (int i = 0; i < 16; i++) {
          tournamentManager(adversaires);
        }
        System.out.println("[TOURNAMENT] -- 16 opponents are ready to fight!");
        for (int i = 0; i < 8; i++) {
          tournamentManager(adversaires);
        }
        System.out.println("[TOURNAMENT] -- 8 opponents are ready to fight!");
        for (int i = 0; i < 4; i++) {
          tournamentManager(adversaires);
        }
        System.out.println("[TOURNAMENT] -- 4 opponents are ready to fight!");
        for (int i = 0; i < 2; i++) {
          tournamentManager(adversaires);
        }
        System.out.println("[TOURNAMENT] -- 2 opponents are ready to fight!");
        tournamentManager(adversaires);

        System.out.println("[TOURNAMENT] -- " + adversaires.get(0).getNom() + " is the winner!");

      } else if (mode == 3) { // Voir tout les pokémons
        System.out.println(new Pokedex().toString());

      } else if (mode == 4) { // rechercher un pokemon
        Scanner podexScan = new Scanner(System.in);
        System.out.print("Please enter the name (or id) of the pokemon you want to search: ");
        if (podexScan.hasNextInt()) {
          new Pokedex().searchPokemon(podexScan.nextInt());
        } else {
          new Pokedex().searchPokemon(podexScan.nextLine());
        }
        } else if (mode == 12) {

        System.out.println(
            "\n————————————————————————————————————————————————\nIA Tester!\n————————————————————————————————————————————————");
        String nomIA1 = "A lvl1";
        String nomIA2 = "B lvl3";
        int winIA1 = 0;
        int winIA2 = 0;
        int gameNum = 100;
        for (int i = 0; i < gameNum; i++) {

          Pokedex pokedex = new Pokedex();
          AIcomplexe IA1 = new AIcomplexe(nomIA1, (Pokemon[]) pokedex.engendreRanch(), 1);
          AIcomplexe IA2 = new AIcomplexe(nomIA2, (Pokemon[]) pokedex.engendreRanch(), 3);
          Combat combat = new Combat(IA1, IA2);
          combat.commence();

          if (combat.gagnant == nomIA1) {
            winIA1++;
          }
          if (combat.gagnant == nomIA2) {
            winIA2++;
          }
        }
        float winrateIA1 = winIA1;
        float winrateIA2 = winIA2;
        System.out.println(
            "Winrates:\n"
                + nomIA1
                + ": "
                + winrateIA1 / gameNum * 100
                + "% ("
                + winrateIA1
                + ")"
                + "\n"
                + nomIA2
                + ": "
                + winrateIA2 / gameNum * 100
                + "% ("
                + winrateIA2
                + ")");

      } else if (mode == 5) {
        System.out.println("See you next time !");
        System.exit(0);
      } else {
        System.out.println("Invalid input. Please try again.");
      }
    }
  }

  private static void tournamentManager(ArrayList<IDresseur> adversaires) {
    int opponent1 = new Random().nextInt(0, adversaires.size());
    int opponent2 = new Random().nextInt(0, adversaires.size());
    while (opponent1 == opponent2) {
      opponent2 = new Random().nextInt(0, adversaires.size());
    }
    Combat c = new Combat(adversaires.get(opponent1), adversaires.get(opponent2));
    c.commence();
    if (Objects.equals(c.gagnant, adversaires.get(opponent1).getNom())) {
      adversaires.remove(opponent2);
    } else {
      adversaires.remove(opponent1);
    }
  }

  private static int getLevelFromSave(int saveNumber) {
    ArrayList<String[]> data = getFromCSV("saveSlot" + saveNumber);
    return Integer.parseInt(data.get(0)[1]);
  }

  private static int getPrestigeFromSave(int saveNumber) {
    try {
      ArrayList<String[]> data = getFromCSV("saveSlot" + saveNumber);
      return Integer.parseInt(data.get(0)[2]);
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * Permet d'afficher les pokémons d'un joueur au début de partie
   *
   * @param pokes pokémons du joueur
   */
  static void showPokemon(Pokemon[] pokes) {
    String[] nb = {"first", "second", "third", "fourth", "fifth", "sixth"};
    for (int i = 0; i < pokes.length; i++) {
      System.out.println(
          "Your "
              + nb[i]
              + " pokémon is "
              + pokes[i].getNom()
              + "!"
              + " Level : "
              + pokes[i].getNiveau());
    }
  }

  /**
   * Mets le numéro de sauvegarde choisi par l'utilisateur dans la variable nbSave.
   *
   * @param scanner Scanner
   * @return nbSave int
   */
  public static int setSave(Scanner scanner) {
    while (!scanner.hasNextInt()) {
      scanner.nextLine();
      System.out.println("Invalid input. Please try again.");
      System.out.print("> ");
    }

    return nbSave = scanner.nextInt();
  }

  /**
   * Charge le fichier csv et renvoie un tableau 2D contenant les données
   *
   * @param filename le nom du fichier csv
   * @return un tableau 2D contenant les données (ArrayList<String[]>)
   */
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

  /**
   * Joue la musique du fond du jeu
   *
   * @param clipFile le fichier de musique
   * @throws IOException Si une erreur est survenue lors de la lecture du fichier
   * @throws UnsupportedAudioFileException Si le format de la musique n'est pas supporté
   * @throws LineUnavailableException Si le format de la musique n'est pas supporté
   */
  private static void playClip(File clipFile)
      throws IOException, UnsupportedAudioFileException, LineUnavailableException {
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

  /**
   * Ecrit les données dans un fichier CSV
   *
   * @param filename nom du fichier
   * @param data données à écrire
   */
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

  /**
   * Chargement de la sauvegarde si existante, sinon création d'une nouvelle sauvegarde
   *
   * @param saveNumber numéro de la sauvegarde à charger
   * @param scanner scanner pour lire le numéro de la sauvegarde
   * @return la liste des pokémons (au format Pokemon) obtenu depuis la sauvegarde
   */
  static Pokemon[] loadSave(int saveNumber, Scanner scanner) {
    System.out.println("[SAVE] - You have chosen Slot " + saveNumber + ".\n[SAVE] - Loading...");
    ArrayList<String[]> data = getFromCSV("saveSlot" + saveNumber);
    Pokemon[] pokes;
    // Si la sauvegarde n'existe pas, on crée une nouvelle
    if (data.size() == 0) {
      System.out.println(
          "You arrive at the Raoult Tower, approch the lobby desk and the secretary asks you your name. You tell her : \" \n");
      System.out.print("My name is : ");
      Scanner scanner2 = new Scanner(System.in);
      String name = scanner2.nextLine();
      System.out.println("She also needs a security code.\n");
      System.out.print("Your security code will be : ");
      Scanner securityScan = new Scanner(System.in);
      String securityCode = securityScan.nextLine();
      System.out.println("select the difficulty : \n");
      System.out.println("1. I'm too young to die");
      System.out.println("2. Hey, not too rough");
      System.out.println("3. Hurt me plenty");
      System.out.println("4. Ultra-Violence (not implemented yet)");
      Scanner diffScan = new Scanner(System.in);
      if (diffScan.hasNextInt()) {
        int diff = diffScan.nextInt();
        if (diff < 1 || diff >4) {
          System.out.println("Too hard to choose? Then I'll choose for you!");
          difficulty = 3;
        }
      }

      System.out.println(
          "She says : \"Welcome " + name + "!, the Professor will see you in a moment.\"\n");
      Pokedex pokedex = new Pokedex();
      pokes = (Pokemon[]) pokedex.engendreRanch();
      System.out.println(
          "[SAVE] - Creating new save...\n[SAVE] - Please do not power off the computer during the process.");
      saveGame(pokes, name, saveNumber, 0, 0, securityCode);

      System.out.println("[SAVE] - Save created.");

    } else {

      System.out.println("[SAVE] - Save found.\n[SAVE] - Loading...");
      String securityCode;
      do {

        System.out.println("Please enter your security code : ");
        Scanner securityScan = new Scanner(System.in);
        securityCode = securityScan.nextLine();
        if (!md5Password(securityCode).equals(data.get(0)[3]))
          System.out.println("[SAVE] - Security code incorrect. Please try again.");
      } while (!md5Password(securityCode).equals(data.get(0)[3]));

      System.out.println("[SAVE] - Security code correct.");

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
      difficulty = Integer.parseInt(data.get(0)[4]);
      System.out.println("[SAVE] - Save loaded.");
    }
    return pokes;
  }

  /**
   * Récupération du nom du dresseur depuis la sauvegarde
   *
   * @param saveNumber numéro de la sauvegarde
   * @return nom du dresseur
   */
  static String getNameFromSave(int saveNumber) {
    ArrayList<String[]> data = getFromCSV("saveSlot" + saveNumber);
    return data.get(0)[0];
  }

  static String md5Password(String password) {
    StringBuilder md5Pass = null;
    try {
      MessageDigest m = MessageDigest.getInstance("MD5");
      m.update(password.getBytes());
      byte[] bytes = m.digest();
      md5Pass = new StringBuilder();
      for (byte aByte : bytes) {
        md5Pass.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    assert md5Pass != null;
    return md5Pass.toString();
  }

  /**
   * Sauvegarde le nom du dresseur et les attributs principales des pokémons dans un fichier CSV
   *
   * @param pokemon les pokémons à sauvegarder
   * @param name le nom du dresseur
   * @param Slotnb le numéro du slot de sauvegarde
   */
  static void saveGame(
      Pokemon[] pokemon,
      String name,
      int Slotnb,
      int storyLevel,
      int prestigeLevel,
      String securityCode) {

    ArrayList<String[]> data = new ArrayList<>();
    if (securityCode == null) {
      try {
        ArrayList<String[]> oldSave = getFromCSV("saveSlot" + Slotnb);
        securityCode = oldSave.get(0)[4];
      } catch (Exception e) {
        System.out.println(
            "[SAVE] - Error when retrieving security code from old save. Default code will be used (pokemon).");
        securityCode = "pokemon";
      }
    }
    // Ajout du nom du dresseur
    data.add(
        new String[] {
          name,
          String.valueOf(storyLevel),
          String.valueOf(prestigeLevel),
          md5Password(securityCode),
          String.valueOf(difficulty)
        });
    // Ajout des attributs principaux des pokémons
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
    // enregistrement des données dans un fichier CSV
    writeToCSV("saveSlot" + Slotnb, data);
  }
}
