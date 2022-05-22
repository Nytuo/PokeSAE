package test;

import org.junit.jupiter.api.Test;
import pokemon.Stats;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {

    @Test
    void getPV() {
    Stats stat = new Stats(10,10,10,10,10);
    assertEquals(10, stat.getPV());
    }

    @Test
    void getForce() {
    Stats stat = new Stats(10,10,10,10,10);
    assertEquals(10, stat.getForce());
    }

    @Test
    void getDefense() {
    Stats stat = new Stats(10,10,10,10,10);
    assertEquals(10, stat.getDefense());
    }

    @Test
    void getSpecial() {
    Stats stat = new Stats(10,10,10,10,10);
    assertEquals(10, stat.getSpecial());
    }

    @Test
    void getVitesse() {
    Stats stat = new Stats(10,10,10,10,10);
    assertEquals(10, stat.getVitesse());
    }

    @Test
    void setPV() {
    Stats stat = new Stats(10,10,10,10,10);
    stat.setPV(20);
    assertEquals(20, stat.getPV());
    }

    @Test
    void setForce() {
    Stats stat = new Stats(10,10,10,10,10);
    stat.setForce(20);
    assertEquals(20, stat.getForce());
    }

    @Test
    void setDefense() {
    Stats stat = new Stats(10,10,10,10,10);
    stat.setDefense(20);
    assertEquals(20, stat.getDefense());
    }

    @Test
    void setVitesse() {
    Stats stat = new Stats(10,10,10,10,10);
    stat.setVitesse(20);
    assertEquals(20, stat.getVitesse());
    }

    @Test
    void setSpecial() {
    Stats stat = new Stats(10,10,10,10,10);
    stat.setSpecial(20);
    assertEquals(20, stat.getSpecial());
    }
}