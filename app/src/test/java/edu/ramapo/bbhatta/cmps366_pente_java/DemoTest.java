package edu.ramapo.bbhatta.cmps366_pente_java;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static edu.ramapo.bbhatta.cmps366_pente_java.Player.COMPUTER;
import static edu.ramapo.bbhatta.cmps366_pente_java.Player.HUMAN;
import static org.junit.Assert.assertEquals;

public class DemoTest {

    private final File serial1File = new File("C:\\Users\\bibhu\\Documents\\Projects\\cmps366-pente-java\\app\\src\\test\\resources\\serials\\case1-prioritizing_capturing.txt");


    @Test
    public void testInitialCaptures() {
        Pente pente = getPenteFromFile();
        assertEquals(0, pente.getCaptures(HUMAN));
        assertEquals(0, pente.getCaptures(COMPUTER));
    }

    @Test
    public void testInitialRoundScore() {
        Pente pente = getPenteFromFile();
        assertEquals(0, pente.getRoundScore(HUMAN));
        assertEquals(0, pente.getRoundScore(COMPUTER));
    }

    @Test
    public void testCurrentPlayerAndStone() {
        Pente pente = getPenteFromFile();
        assertEquals(HUMAN, pente.getCurrentPlayer());
        assertEquals(Stone.BLACK, pente.getCurrentStone());
    }

    @Test
    public void testMakeMove() {
        Pente pente = getPenteFromFile();
        pente = pente.makeMove("J12");
        assertEquals(2, pente.getNoCaptures(HUMAN));
        assertEquals(0, pente.getNoCaptures(COMPUTER));
    }

    private Pente getPenteFromFile() {
        try {
            return Pente.fromFile(serial1File);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
