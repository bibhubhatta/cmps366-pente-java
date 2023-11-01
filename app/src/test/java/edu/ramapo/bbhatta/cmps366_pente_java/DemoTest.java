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
        Tournament tournament = getPenteFromFile();
        assertEquals(0, (int) tournament.getCaptures(HUMAN));
        assertEquals(0, (int) tournament.getCaptures(COMPUTER));
    }

    @Test
    public void testInitialRoundScore() {
        Tournament tournament = getPenteFromFile();
        assertEquals(0, (int) tournament.getRoundScore(HUMAN));
        assertEquals(0, (int) tournament.getRoundScore(COMPUTER));
    }

    @Test
    public void testCurrentPlayerAndStone() {
        Tournament tournament = getPenteFromFile();
        assertEquals(HUMAN, tournament.getCurrentPlayer());
        assertEquals(Stone.BLACK, tournament.getCurrentStone());
    }

    @Test
    public void testMakeMove() {
        Tournament tournament = getPenteFromFile();
        tournament = tournament.makeMove("J12");
        assertEquals(2, (int) tournament.getNoCaptures(HUMAN));
        assertEquals(0, (int) tournament.getNoCaptures(COMPUTER));
    }

    private Tournament getPenteFromFile() {
        try {
            return Tournament.fromFile(serial1File);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
