package dresseur;

import pokemon.Pokemon;

import java.util.ArrayList;

public class Dresseur {
    public String name;
    public int level;
    public ArrayList<Pokemon> pokemons;

    public Dresseur(String name, int level, ArrayList<Pokemon> pokemons) {
        this.name = name;
        this.level = level;
        this.pokemons = pokemons;
    }
    private void summonPokemon(Pokemon pokemon) {
        pokemons.set(0, pokemon);
    }
    private void ChooseAttack(Pokemon pokemon) {
    }

    private void ChooseSquadmon(Pokemon pokemon) {
    }

}
