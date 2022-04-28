package dresseur;

import pokemon.Pokemon;

public class Dresseur {
    public String name;
    public int level;
    public Pokemon[] pokemons;

    public Dresseur(String name, int level, Pokemon[] pokemons) {
        this.name = name;
        this.level = level;
        this.pokemons = pokemons;
    }

}
