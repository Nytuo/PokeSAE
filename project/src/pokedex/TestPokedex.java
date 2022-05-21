
package pokedex;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import pokemon.Species;

public class TestPokedex {
    public static void main(String[] args) {
        Pokedex pokedex = new Pokedex();
        
        while (true) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a pokemon name or ID: ");
        String name = scanner.nextLine();
        if (name.equals("0")) {
            return;
        }
            if (name.matches("^[0-9]+$")) {
                pokedex.searchPokemon(Integer.parseInt(name));
            } else {
                pokedex.searchPokemon(name);
            }
        }
        
    }
}