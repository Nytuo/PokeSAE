package game;

import dresseur.AIcomplexe;
import dresseur.AIsimple;
import dresseur.Dresseur;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.*;
import pokedex.Pokedex;
import pokemon.*;

/** Classe de lancement du jeu et menu principal */
public class MainGame {

  /** Numéro de la sauvegarde sélectionner */
  static int nbSave = 0;

  /** methode main de lancement du menu
   * @param args les arguments de la ligne de commande
   * @throws UnsupportedAudioFileException exception levée si le format de l'audio n'est pas supporté
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
          "What do you want to do ?\n1. Go Single Player\n2. Go Multi Player\n3. View all pokémons\n4. Search a pokemon\n5. Exit");
      System.out.print("> ");
      Scanner scanner = new Scanner(System.in);
      int mode = scanner.nextInt();
      selectGameMode = mode != 1 && mode != 2;
      if (mode == 1) { // Campagne
        System.out.println(
            "You have chosen Single Player mode.\n"
                + "Insert the slot number of your save (Between 1 and infinite, will be created if doesn't exist): ");
        System.out.print("> ");

        Pokemon[] pokes = loadSave(setSave(scanner), scanner);
        String dName = getNameFromSave(nbSave);

        Dresseur joueur = new Dresseur(dName, pokes);

        System.out.println("[SAVE] - Loading complete.\n");

        showPokemon(pokes);

        Pokedex pokedex = new Pokedex();
        AIsimple dresseurIA = new AIsimple("Dimitry", (Pokemon[]) pokedex.engendreRanch());

        Combat combat = new Combat(joueur, dresseurIA);
        combat.commence();

      } else if (mode == 2) { // Multijoueur
        System.out.println(
            "\n————————————————————————————————————————————————\nWelcome to the Pokemon Game -- ULTIMATE WARRIORS!\n————————————————————————————————————————————————");
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
        
        
      }else if (mode == 12) { //DEBUG: IA TESTER

          System.out.println("\n————————————————————————————————————————————————\nIA Tester!\n————————————————————————————————————————————————");
          String nomIA1 = "Nule";
          String nomIA2 = "Fort";
          int winIA1 = 0;
          int winIA2 = 0;
          int gameNum=100;
          for (int i=0; i<gameNum;i++) {
        	  
              
              Pokedex pokedex = new Pokedex();
              AIcomplexe IA1 = new AIcomplexe(nomIA1, (Pokemon[]) pokedex.engendreRanch(),1);
              AIcomplexe IA2 = new AIcomplexe(nomIA2, (Pokemon[]) pokedex.engendreRanch(),1);
              Combat combat = new Combat(IA2, IA1);
              combat.commence();
             
             if (combat.gagnant == nomIA1) {
            	 winIA1 ++;
             }
             else  {
            	 winIA2 ++;
             }
          }
          float winrateIA1=winIA1;
          float winrateIA2=winIA2;
          System.out.println("Winrates:\n"+nomIA1+": "+winrateIA1+"\n"+nomIA2+": "+winrateIA2);
          
        
         
              

      } 
      else if (mode == 5) {
        System.out.println("See you next time !");
        System.exit(0);
        
      } else {
        System.out.println("Invalid input. Please try again.");
      }
    }
  }

  /**
   * Permet d'afficher les pokémons d'un joueur au début de partie
   *
   * @param pokes pokémons du joueur
   */
  private static void showPokemon(Pokemon[] pokes) {
	  String[] nb = {"first","second","third","fourth","fifth","sixth"};
	  for (int i = 0; i < pokes.length; i++ ) {
		  System.out.println(
			        "Your "+nb[i]+" pokémon is " + pokes[i].getNom() + "!" + " Level : " + pokes[i].getNiveau());
	  }
  }

  /**
   * Mets le numéro de sauvegarde choisi par l'utilisateur dans la variable nbSave.
   *
   * @param scanner Scanner
   * @return nbSave int
   */
  public static int setSave(Scanner scanner) {

    nbSave = scanner.nextInt();
    return nbSave;
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
          "Hi, I'm the professor Raoult! Welcome to the world of Pokémon ! You love to fight against poor creatures, then you are at the right place!\n"
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
          "[SAVE] - Creating new save...\n[SAVE] - Please do not power off the computer during the process.");
      saveGame(pokes, name, saveNumber);

      System.out.println("[SAVE] - Save created.");

    } else {
      System.out.println("[SAVE] - Save found.\n[SAVE] - Loading...");

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

  /**
   * Sauvegarde le nom du dresseur et les attributs principales des pokémons dans un fichier CSV
   *
   * @param pokemon les pokémons à sauvegarder
   * @param name le nom du dresseur
   * @param Slotnb le numéro du slot de sauvegarde
   */
  static void saveGame(Pokemon[] pokemon, String name, int Slotnb) {
    ArrayList<String[]> data = new ArrayList<>();
    // Ajout du nom du dresseur
    data.add(new String[] {name});
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
