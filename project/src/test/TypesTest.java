package test;

import org.junit.jupiter.api.Test;
import pokemon.Types;

import static org.junit.jupiter.api.Assertions.*;

class TypesTest {

    @Test
    void getNom() {
        Types types = new Types("Feu");
        assertEquals("Feu", types.getNom());
    }
}