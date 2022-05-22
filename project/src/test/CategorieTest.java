package test;

import org.junit.jupiter.api.Test;
import pokemon.Categorie;

import static org.junit.jupiter.api.Assertions.*;

class CategorieTest {

    @Test
    void isSpecial() {
        Categorie categorie = new Categorie("Feu",false);
        assertFalse(categorie.isSpecial());
        Categorie categorie2 = new Categorie("Feu",true);
        assertTrue(categorie2.isSpecial());
    }

    @Test
    void getNom() {
        Categorie categorie = new Categorie("Feu",false);
        assertEquals("Feu", categorie.getNom());
    }
}