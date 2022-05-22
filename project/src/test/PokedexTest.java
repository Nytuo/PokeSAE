package test;

import org.junit.jupiter.api.Test;
import pokedex.Pokedex;
import pokemon.Pokemon;

import static org.junit.jupiter.api.Assertions.*;

class PokedexTest {

    @Test
    void engendreRanch() {
        Pokedex pokedex = new Pokedex();
        assertNotNull(pokedex.engendreRanch());
    }


    @Test
    void getCapacite() {
        Pokedex pokedex = new Pokedex();
        assertNull(pokedex.getCapacite("Feu"));

    }

}