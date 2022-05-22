package game;

import pokedex.Pokedex;
import pokemon.*;

import java.util.TreeMap;

public class MainGame {
    public static void main(String[] args) {

        Pokedex pokedex = new Pokedex();
        Pokemon[] pokes = (Pokemon[]) pokedex.engendreRanch();
        for (Pokemon p : pokes) {
        System.out.println(p.getNom());
        System.out.println(p.getNiveau());
        System.out.println(p.getStat());
        System.out.println(p.getDV());
        System.out.println(p.getEV());
        System.out.println(p.getGainsStat());
        }

    }
}
