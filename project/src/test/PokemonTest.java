package test;

import interfaces.ICapacite;
import org.junit.jupiter.api.Test;
import pokedex.Pokedex;
import pokemon.*;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void getBaseStat() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertNotNull(pokes[0].getBaseStat());

    }

    @Test
    void getNom() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].setNom("Bulbizarre");
        assertEquals("Bulbizarre", pokes[0].getNom());
    }

    @Test
    void getNiveauDepart() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertEquals(1, pokes[0].getNiveauDepart());
    }

    @Test
    void getBaseExp() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertEquals(0, pokes[0].getBaseExp());
    }

    @Test
    void getGainsStat() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertNotNull(pokes[0].getGainsStat());
    }

    @Test
    void getCapSet() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertNotNull(pokes[0].getCapSet());
    }


    @Test
    void getTypes() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertNotNull(pokes[0].getTypes());
    }

    @Test
    void setNom() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].setNom("Bulbizarre");
        assertEquals("Bulbizarre", pokes[0].getNom());
    }

    @Test
    void getStat() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertNotNull(pokes[0].getStat());
    }

    @Test
    void getDV() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertNotNull(pokes[0].getDV());
    }

    @Test
    void getEV() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertNotNull(pokes[0].getEV());
    }

    @Test
    void setEV() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].setEV("PV",0);
        assertEquals(0,pokes[0].getEV().get("PV"));
    }

    @Test
    void getExperience() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertEquals(0, pokes[0].getExperience());
    }

    @Test
    void getNiveau() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertEquals(1, pokes[0].getNiveau());
    }


    @Test
    void getPourcentagePV() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertEquals(100, pokes[0].getPourcentagePV());
    }

    @Test
    void getEspece() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertEquals(pokes[0].nameOfSpecies, pokes[0].getEspece().getNom());
    }

    @Test
    void vaMuterEn() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].vaMuterEn(pokes[1]);
        assertEquals(pokes[1].nameOfSpecies, pokes[0].getEspece().getNom());
    }

    @Test
    void getCapacitesApprises() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertNotNull(pokes[0].getCapacitesApprises());

    }

    @Test
    void apprendCapacites() {
        Types[] types = {new Types("Feu"),new Types("Eau")};

        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};


        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].apprendCapacites(capListe);
        assertNotEquals(capListe, pokes[0].capacities);
    }

    @Test
    void remplaceCapacite() throws Exception {
        Types[] types = {new Types("Feu"),new Types("Eau")};

        Capacite[] capListe = {new Capacite("Coupe", types[0], new Categorie("Physique", false), 60, 70, 80), new Capacite("Coupe", types[1], new Categorie("Physique", false), 60, 70, 80)};


        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].remplaceCapacite(0, capListe[1]);
        assertNotEquals(pokes[0].capacities[0], pokes[0].capacities[1]);
    }

    @Test
    void gagneExperienceDe() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].gagneExperienceDe(pokes[1]);
        assertEquals(pokes[0].getExperience(), pokes[1].getExperience());
    }

    @Test
    void subitAttaqueDe() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].subitAttaqueDe(pokes[1], pokes[1].capacities[0]);
        assertNotEquals(pokes[0].getPourcentagePV(), pokes[1].getPourcentagePV());
    }

    @Test
    void estEvanoui() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertFalse(pokes[0].estEvanoui());
    }

    @Test
    void aChangeNiveau() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].aChangeNiveau();
        assertEquals(1, pokes[0].getNiveau());
    }


    @Test
    void peutMuter() {

        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        assertFalse(pokes[0].peutMuter());

    }

    @Test
    void soigne() {
        Pokemon[] pokes = (Pokemon[]) new Pokedex().engendreRanch();
        pokes[0].soigne();
        assertEquals(100, pokes[0].getPourcentagePV());
    }
}