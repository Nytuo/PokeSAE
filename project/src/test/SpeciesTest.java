package test;

import org.junit.jupiter.api.Test;
import pokedex.Pokedex;
import pokemon.*;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class SpeciesTest {

    @Test
    void getBaseStat() {
        Types[] types = {new Types("Feu"), new Types("Dragon")};
        Stats stats = new Stats(10, 10, 10, 10, 10);
        TreeMap<Integer, String> evolutions = new TreeMap<>();
        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};
        Stats gainsStat = new Stats(20, 20, 20, 20, 20);
        Species spe = new Species("Dracofeu", types, stats, 15, evolutions, capListe,190,gainsStat);
        assertEquals(stats, spe.getBaseStat());
    }

    @Test
    void getNom() {
        Types[] types = {new Types("Feu"), new Types("Dragon")};
        Stats stats = new Stats(10, 10, 10, 10, 10);
        TreeMap<Integer, String> evolutions = new TreeMap<>();
        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};
        Stats gainsStat = new Stats(20, 20, 20, 20, 20);
        Species spe = new Species("Dracofeu", types, stats, 15, evolutions, capListe,190,gainsStat);
        assertEquals("Dracofeu", spe.getNom());
    }

    @Test
    void getNiveauDepart() {
        Types[] types = {new Types("Feu"), new Types("Dragon")};
        Stats stats = new Stats(10, 10, 10, 10, 10);
        TreeMap<Integer, String> evolutions = new TreeMap<>();
        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};
        Stats gainsStat = new Stats(20, 20, 20, 20, 20);
        Species spe = new Species("Dracofeu", types, stats, 15, evolutions, capListe,190,gainsStat);
        assertEquals(15, spe.getNiveauDepart());
    }

    @Test
    void getBaseExp() {
        Types[] types = {new Types("Feu"), new Types("Dragon")};
        Stats stats = new Stats(10, 10, 10, 10, 10);
        TreeMap<Integer, String> evolutions = new TreeMap<>();
        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};
        Stats gainsStat = new Stats(20, 20, 20, 20, 20);
        Species spe = new Species("Dracofeu", types, stats, 15, evolutions, capListe,190,gainsStat);
        assertEquals(190, spe.getBaseExp());
    }

    @Test
    void getGainsStat() {
        Types[] types = {new Types("Feu"), new Types("Dragon")};
        Stats stats = new Stats(10, 10, 10, 10, 10);
        TreeMap<Integer, String> evolutions = new TreeMap<>();
        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};
        Stats gainsStat = new Stats(20, 20, 20, 20, 20);
        Species spe = new Species("Dracofeu", types, stats, 15, evolutions, capListe,190,gainsStat);
        assertEquals(gainsStat, spe.getGainsStat());
    }

    @Test
    void getCapSet() {
        Types[] types = {new Types("Feu"), new Types("Dragon")};
        Stats stats = new Stats(10, 10, 10, 10, 10);
        TreeMap<Integer, String> evolutions = new TreeMap<>();
        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};
        Stats gainsStat = new Stats(20, 20, 20, 20, 20);
        Species spe = new Species("Dracofeu", types, stats, 15, evolutions, capListe,190,gainsStat);
        assertEquals(capListe, spe.getCapSet());
    }

/*    @Test
    //TODO: test getEvolutions
    void getEvolution() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].getEspece();
        assertEquals(pokes[0].getEspece(), );
    }*/

    @Test
    void getTypes() {
        Types[] types = {new Types("Feu"), new Types("Dragon")};
        Stats stats = new Stats(10, 10, 10, 10, 10);
        TreeMap<Integer, String> evolutions = new TreeMap<>();
        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};
        Stats gainsStat = new Stats(20, 20, 20, 20, 20);
        Species spe = new Species("Dracofeu", types, stats, 15, evolutions, capListe,190,gainsStat);
        assertEquals(types, spe.getTypes());

    }
}