package game;
import pokemon.*;

import java.time.LocalTime;
import java.util.TreeMap;

import pokedex.Pokedex;

public class MainGame {
    public static void main(String[] args) {
        /*Types[] types = new Types[2];
        types[0] = new Types("Feu");
        types[1] = new Types("Eau");
        Stats stats = new Stats(100, 100, 100, 100, 100);
        Capacite[] capacites = new Capacite[2];
        Categorie[] cat = new Categorie[2];
        cat[0] = new Categorie("Plante", false);
        capacites[0] = new Capacite("Plante",types[0],cat[0],20,10,11);
        Pokemon pokemon = new Pokemon("Bulbizarre", "Bulbizarre", types, stats, 1, new TreeMap<>(), capacites, 1, 0, stats, capacites, 1, stats);
        System.out.println(pokemon.nameOfSpecies);
        */
        Pokedex pkdx=new Pokedex();
        pkdx.searchPokemon(1);
        Pokemon[] ranch=(Pokemon[])pkdx.engendreRanch();
        ranch.toString();
    }
}
