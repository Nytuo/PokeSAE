package pokedex;

import java.util.Scanner;

public class TestPokedex {
    public static void main(String[] args) {
        Pokedex pokedex = new Pokedex();
/*
        System.out.println(pokedex.toString());
*/


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a pokemon name or ID: ");
        String name = scanner.nextLine();
        if (name.matches("^[0-9]+$")) {
            pokedex.searchPokemon(Integer.parseInt(name));
        }else {
            pokedex.searchPokemon(name);
        }
    }
}
