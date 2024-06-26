package pokedex;

import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokedex;
import interfaces.IPokemon;
import interfaces.IType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import pokemon.*;

/**
 * La classe Pokedex permet au dresseur de se documenter sur chaque espèce de Pokémon et chaque
 * attaque, et aussi d'établir des stratégies Elle implémente l'interface: IPokedex
 */
public class Pokedex implements IPokedex {
  /** La liste des pokémons depuis le CSV */
  private final ArrayList<String[]> pokedata = new ArrayList<String[]>();
  /** La liste des capacités des pokémons depuis le CSV */
  private final ArrayList<String[]> capacitedata = new ArrayList<String[]>();

  /** Constructeur de la classe Pokedex qui va associer aux attributs les données des CSV */
  public Pokedex() {
    try {
      Scanner sc = new Scanner(new File("external/listePokemon1G.csv"));

      sc.useDelimiter(",");
      while (sc.hasNext()) {
        pokedata.add(sc.nextLine().split(";"));
      }
      sc.close();
    } catch (FileNotFoundException e) {
      try {
        Scanner sc = new Scanner(new File("project/external/listePokemon1G.csv"));

        sc.useDelimiter(",");
        while (sc.hasNext()) {
          pokedata.add(sc.nextLine().split(";"));
        }
        sc.close();

      } catch (Exception e2) {
        System.out.println("File not found");
      }
    }

    try {
      Scanner sc = new Scanner(new File("external/listeCapacites.csv"));
      sc.useDelimiter(",");
      while (sc.hasNext()) {
        capacitedata.add(sc.nextLine().split(";"));
      }
      sc.close();
    } catch (FileNotFoundException e) {
      try {
        Scanner sc = new Scanner(new File("project/external/listeCapacites.csv"));
        sc.useDelimiter(",");
        while (sc.hasNext()) {
          capacitedata.add(sc.nextLine().split(";"));
        }
        sc.close();
      } catch (Exception e2) {
        System.out.println("File not found");
      }
    }
  }

  /**
   * Affiche les touts les pokémons de la liste
   *
   * @return La liste des pokémons dans une String bien formatée
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int stockLine = 4;
    int byLine = stockLine;
    int maxLength = 0;
    for (String[] line : pokedata) {
      if (line[1].length() > maxLength) {
        maxLength = line[1].length() + 6;
      }
    }
    System.out.println(maxLength);
    for (String[] s : pokedata) {
      if (byLine == 0) {
        sb.append("\n");
        byLine = stockLine;
      }
      sb.append(String.format("%10s%16s" + maxLength + "s", "| " + s[0] + " : ", s[1] + " |"));

      byLine--;
    }
    return sb.toString();
  }

  /**
   * Permet de rechercher des informations sur un Pokémon à partir de son nom
   *
   * @param name Le nom du Pokémon
   */
  public void searchPokemon(String name) {
    System.out.println("Searching for " + name + "...");
    for (String[] s : pokedata) {
      try {
        if (s[1].equalsIgnoreCase(name)) {
          System.out.println("Found !");
          for (int i = 0; i < s.length; i++) {
            System.out.println(pokedata.get(0)[i] + " : " + s[i]);
          }
          return;
        }
      } catch (NumberFormatException ignored) {
      }
    }
    System.out.println("Pokemon not found");
  }

  /**
   * Permet de rechercher des informations sur un Pokémon à partir de son numéro
   *
   * @param id Le numéro du Pokémon
   */
  public void searchPokemon(int id) {
    System.out.println("Searching for " + id + "...");
    for (String[] s : pokedata) {
      try {

        if (Integer.parseInt(s[0]) == (id)) {
          System.out.println("Found !");
          for (int i = 0; i < s.length; i++) {
            System.out.println(pokedata.get(0)[i] + " : " + s[i]);
          }
          return;
        }
      } catch (NumberFormatException ignored) {
      }
    }
    System.out.println("Pokemon not found");
  }

  /**
   * Crée les 6 Pokémons qui seront attribués à un dresseur par la suite.
   *
   * @return un tableau de Pokémons.
   */
  @Override
  public IPokemon[] engendreRanch() {
    ArrayList<String> pokeLvl1 = new ArrayList<String>();
    Pokemon[] ranch = new Pokemon[6];
    Species esp;
    for (String[] s : pokedata) {
      if (s[15].equals("1")) pokeLvl1.add(s[1]);
    }
    for (int i = 0; i < 6; i++) {
      esp = (Species) getInfo(pokeLvl1.get((int) (Math.random() * pokeLvl1.size())));
      // Capacites au hasard parmi les capacites dispo de l'espece
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
      // création du ranch
      ranch[i] =
          new Pokemon(
              esp.nameOfSpecies,
              esp.nameOfSpecies,
              esp.types,
              esp.baseStats,
              esp.startLevel,
              esp.evolution,
              esp.capacities,
              0.0,
              esp.getBaseExp(),
              capacitePoke,
              (int) (Math.random() * 15151) + 1,
              esp.gainsStat);
    }
    return ranch;
  }

  /**
   * Donne un objet Species d'un Pokémon grâce à son nom.
   *
   * @param nomEspece Le nom de l'espece du Pokémon
   * @return un objet Species.
   */
  @Override
  public IEspece getInfo(String nomEspece) {
    Stats baseStats;
    Stats gainStats;
    Types[] types = new Types[2];
    ArrayList<Capacite> capacitesArray = new ArrayList<Capacite>();

    for (String[] s : pokedata) {
      try {
        if (s[1].equalsIgnoreCase(nomEspece)) {
          // Stats de base
          baseStats =
              new Stats(
                  Integer.parseInt(s[2]),
                  Integer.parseInt(s[3]),
                  Integer.parseInt(s[4]),
                  Integer.parseInt(s[5]),
                  Integer.parseInt(s[6]));
          // Niveau de base
          int baseLvl = Integer.parseInt(s[15]);
          // XP de base
          int baseXp = Integer.parseInt(s[7]);
          // évolutions
          int id = Integer.parseInt(s[0]);

          TreeMap<Integer, String> evolution = new TreeMap<Integer, String>();

          while (Integer.parseInt(pokedata.get(id)[16]) != 0) {
            evolution.put(Integer.parseInt(pokedata.get(id)[16]), pokedata.get(id)[17]);
            id++;
          }
          // types
          types[0] = new Types(s[13]);
          if (s[14] != " ") types[1] = new Types(s[14]);
          // gainStats
          gainStats =
              new Stats(
                  Integer.parseInt(s[8]),
                  Integer.parseInt(s[9]),
                  Integer.parseInt(s[10]),
                  Integer.parseInt(s[11]),
                  Integer.parseInt(s[12]));
          // Capacite
          for (String[] s1 : capacitedata) {
            if (s1[6].equals(types[0].getNom())
                || s1[6].equals(types[1].getNom())
                || s1[6].equals("Normal")) {
              if (s1[6].equals("Physique"))
                capacitesArray.add(
                    new Capacite(
                        s1[0],
                        new Types(s1[6]),
                        new Categorie(s1[5], false),
                        Integer.parseInt(s1[1]),
                        Double.parseDouble(s1[2]),
                        Integer.parseInt(s1[3])));
              else
                capacitesArray.add(
                    new Capacite(
                        s1[0],
                        new Types(s1[6]),
                        new Categorie(s1[5], true),
                        Integer.parseInt(s1[1]),
                        Double.parseDouble(s1[2]),
                        Integer.parseInt(s1[3])));
            }
          }
          // Species accepte uniquement tableau de Capacite, pas ArrayList
          Capacite[] capacites = new Capacite[capacitesArray.size()];
          for (int i1 = 0; i1 < capacitesArray.size(); i1++) capacites[i1] = capacitesArray.get(i1);
          // Création espèce
          Species esp =
              new Species(
                  nomEspece, types, baseStats, baseLvl, evolution, capacites, baseXp, id,
                  gainStats);
          return esp;
        }
      } catch (NumberFormatException ignored) {
      }
    }
    return null;
  }
  // cases CSV: 2-12 (stats),13-14 (types),15 (baselvl),

  /**
   * Donne l'efficacité d'un type sur un autre.
   *
   * @return la valeur de l'efficacité.
   */
  @Override
  public Double getEfficacite(IType attaque, IType defense) {
    HashMap<String, String[]> csv = initializeFromCSV();
    return Double.valueOf(
        csv.get(attaque.getNom())[EnumCapacite.valueOf(defense.getNom()).getId()]);
  }

  /**
   * Initialise une HashMap reprennant le CSV.
   *
   * @return une HashMap reprennant le CSV.
   */
  public HashMap<String, String[]> initializeFromCSV() {
    HashMap<String, String[]> lines = new HashMap<>();
    try {

      Scanner sc = new Scanner(new File("external/efficacites.csv"));
      sc.useDelimiter(";");
      sc.nextLine();
      while (sc.hasNext()) {
        String[] line = sc.nextLine().split(";");
        lines.put(line[0], line);
      }
      sc.close();
    } catch (FileNotFoundException e) {
      try {
        Scanner sc = new Scanner(new File("project/external/efficacites.csv"));
        sc.useDelimiter(";");
        sc.nextLine();
        while (sc.hasNext()) {
          String[] line = sc.nextLine().split(";");
          lines.put(line[0], line);
        }
        sc.close();
      } catch (Exception e2) {
        System.out.println("File not found");
      }
    }
    return lines;
  }

  /** Permet d'associer chaque type à un chiffre pour l'indexer. */
  public enum EnumCapacite {
    /** Capacité Combat */
    Combat(1),
    /** Capacité Dragon */
    Dragon(2),
    /** Capacité Eau */
    Eau(3),
    /** Capacité Electrik */
    Electrik(4),
    /** Capacité Feu */
    Feu(5),
    /** Capacité Glace */
    Glace(6),
    /** Capacité Insecte */
    Insecte(7),

    /** Capacité Normal */
    Normal(8),
    /** Capacité Plante */
    Plante(9),
    /** Capacité Poison */
    Poison(10),
    /** Capacité Psy */
    Psy(11),
    /** Capacité Roche */
    Roche(12),
    /** Capacité Sol */
    Sol(13),
    /** Capacité spectre */
    Spectre(14),
    /** Capacité de vol */
    Vol(15);

    /** id de la capacité */
    private final int id;

    /**
     * Constructeur de l'enum.
     *
     * @param id id de la capacité
     */
    EnumCapacite(int id) {
      this.id = id;
    }

    /**
     * Retourne l'id de la capacité.
     *
     * @return l'id de la capacité
     */
    public int getId() {
      return id;
    }
  }

  /**
   * Donne la capacité à partir de son nom.
   *
   * @return un objet Capacite correspondant.
   */
  @Override
  public ICapacite getCapacite(String nom) {
    for (String[] s : capacitedata) {
      try {

        if (s[0].equalsIgnoreCase(nom)) {

          Types type = new Types(s[6]);
          Categorie categorie;
          if (s[5].equals("Physique")) categorie = new Categorie(s[5], false);
          else categorie = new Categorie(s[5], true);
          // Création capacite
          Capacite cap =
              new Capacite(
                  s[0],
                  type,
                  categorie,
                  Integer.parseInt(s[1]),
                  Double.parseDouble(s[2]),
                  Integer.parseInt(s[3]));
          return cap;
        }
      } catch (NumberFormatException e) {
        System.out.println("Erreur de format" + e.getMessage());
      }
    }
    return null;
  }

  /**
   * Donne la capacité à partir de son numéro.
   *
   * @return un objet Capacite correspondant.
   */
  @Override
  public ICapacite getCapacite(int n) {
    for (String[] s : capacitedata) {
      try {
        if (Integer.parseInt(s[4]) == n) {
          // Creation type
          Types type = new Types(s[6]);
          // Creation categorie
          Categorie categorie;
          if (s[5].equals("Physique")) categorie = new Categorie(s[5], false);
          else categorie = new Categorie(s[5], true);

          // Création capacite
          Capacite cap =
              new Capacite(
                  s[0],
                  type,
                  categorie,
                  Integer.parseInt(s[1]),
                  Integer.parseInt(s[2]),
                  Integer.parseInt(s[3]));
          return cap;
        }
      } catch (NumberFormatException ignored) {
      }
    }
    return null;
  }
}
