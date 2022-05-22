package game;
import pokedex.Pokedex;
import pokemon.*;

import java.util.TreeMap;

public class MainGame {
    public static void main(String[] args) {
        Types[] types = new Types[2];
        types[0] = new Types("Feu");
        types[1] = new Types("Eau");
        Stats stats = new Stats(100, 100, 100, 100, 100);
        Capacite[] capacites = new Capacite[2];
        Categorie[] cat = new Categorie[2];
        cat[0] = new Categorie("Plante", false);
        capacites[0] = new Capacite("Plante",types[0],cat[0],20,10,11);
        Pokedex pokedex = new Pokedex();

        Pokemon pokemon = new Pokemon("Bulbizarre", "Bulbizarre", types, stats, 1, new TreeMap<>(), capacites, 1, 0, stats, capacites, 1, new Stats(0, 0, 0, 0, 0));
        System.out.println(pokemon.getNom());
        System.out.println(pokemon.getNiveau());
        System.out.println(pokemon.getExperience());
        System.out.println(pokemon.getStat().getPV());
        System.out.println(pokemon.getStat().getVitesse());
        System.out.println(pokemon.getStat().getDefense());
        System.out.println(pokemon.evolution);
        System.out.println(pokemon.getDV());
        System.out.println(pokemon.getEV());
    }
}
