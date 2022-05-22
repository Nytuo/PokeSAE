package test;

import org.junit.jupiter.api.Test;
import pokedex.Pokedex;
import pokemon.Capacite;
import pokemon.Categorie;
import pokemon.Pokemon;
import pokemon.Types;

import static org.junit.jupiter.api.Assertions.*;

class CapaciteTest {

    @Test
    void getNom() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        assertEquals("Coupe", cap.getNom());
    }

    @Test
    void getPrecision() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        assertEquals(70, cap.getPrecision());
    }

    @Test
    void getPuissance() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        assertEquals(60, cap.getPuissance());
    }

    @Test
    void getPP() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        assertEquals(80, cap.getPP());
    }

    @Test
    void resetPP() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        cap.utilise();
        cap.utilise();
        cap.utilise();
        cap.resetPP();
        assertEquals(80, cap.getPP());
    }

    @Test
    void getCategorie() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        assertEquals(categorie, cap.getCategorie());
    }

    @Test
    void getType() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        assertEquals(types, cap.getType());
    }

    @Test
    void calculeDommage() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        Pokemon p1 = pokes[0];
        Pokemon p2 = pokes[1];
        assertNotEquals(0, cap.calculeDommage(p1, p2));
    }

    @Test
    void utilise() {
        Types types = new Types("Feu");
        Categorie categorie = new Categorie("Physique",false);
        Capacite cap = new Capacite("Coupe",types,categorie,60,70,80);
        cap.utilise();
        assertEquals(79, cap.getPP());
    }
}