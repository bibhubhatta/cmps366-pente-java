package edu.ramapo.bbhatta.cmps366_pente_java;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static edu.ramapo.bbhatta.cmps366_pente_java.Player.COMPUTER;
import static edu.ramapo.bbhatta.cmps366_pente_java.Player.HUMAN;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class DemoTest {

    private final File serial1File = new File("C:\\Users\\bibhu\\Documents\\Projects\\cmps366-pente-java\\app\\src\\test\\resources\\serials\\case1-prioritizing_capturing.txt");


    @Test
    public void testSerial1() {

        Pente pente;
        try {
            pente = Pente.fromFile(serial1File);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assert pente != null;
        assertEquals(0, pente.getCaptures(HUMAN));
        assertEquals(0, pente.getCaptures(HUMAN));

        assertEquals(0, pente.getRoundScore(HUMAN));
        assertEquals(0, pente.getRoundScore(COMPUTER));

        assertEquals(HUMAN, pente.getCurrentPlayer());
        assertEquals(Stone.BLACK, pente.getCurrentStone());

        System.out.printf(pente.getBoard().displayString());

//        assertEquals(pente.getBoard().stringToPosition("J12"), pente.getBestMove());

        pente = pente.makeMove("J12");

        System.out.printf(pente.getBoard().displayString());

        assertEquals(2, pente.getNoCaptures(HUMAN));
        assertEquals(0, pente.getNoCaptures(COMPUTER));

        assertEquals(2, pente.getRoundScore(HUMAN));
        assertEquals(0, pente.getRoundScore(COMPUTER));

        assertEquals(COMPUTER, pente.getCurrentPlayer());
        assertEquals(Stone.WHITE, pente.getCurrentStone());

        assertThat(pente.getBestMove(), anyOf(
                        is(pente.getBoard().stringToPosition("C2")),
                        is(pente.getBoard().stringToPosition("C18"))
                )
        );
    }
}
