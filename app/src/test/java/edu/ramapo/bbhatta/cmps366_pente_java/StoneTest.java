package edu.ramapo.bbhatta.cmps366_pente_java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import edu.ramapo.bbhatta.cmps366_pente_java.models.Stone;

public class StoneTest {

    @Test
    public void testBlackStone() {
        Stone black = Stone.BLACK;
        assertEquals('B', black.color());
    }

    @Test
    public void testWhiteStone() {
        Stone white = Stone.WHITE;
        assertEquals('W', white.color());
    }

    @Test
    public void testCopyConstructor() {
        Stone black = Stone.BLACK;
        Stone copy = new Stone(black);
        assertEquals(black.color(), copy.color());
    }

    @Test
    public void testEquals() {
        Stone black1 = new Stone('B');
        Stone black2 = new Stone('B');
        Stone white = new Stone('W');

        assertEquals(black1, black2);
        assertEquals(black2, black1);
        assertNotEquals(black1, white);
        assertNotEquals(white, black1);
    }
}