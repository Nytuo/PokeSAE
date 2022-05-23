package dresseur;

import java.util.ArrayList;
import pokemon.Pokemon;

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

  private void ChooseAttack(Pokemon pokemon) {}
}
