package edu.ramapo.bbhatta.cmps366_pente_java;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PositionTest {
    @Test
    public void testConstructor() {
        Position position = new Position(1, 2);
        assertEquals(1, position.row());
        assertEquals(2, position.col());
    }

    @Test
    public void testCopyConstructor() {
        Position position1 = new Position(1, 2);
        Position position2 = new Position(position1);
        assertEquals(position1.row(), position2.row());
        assertEquals(position1.col(), position2.col());
    }

    @Test
    public void testEquals() {
        Position position1 = new Position(1, 2);
        Position position2 = new Position(1, 2);
        Position position3 = new Position(2, 3);
        assertEquals(position1, position2);
        assertNotEquals(position1, position3);
    }

    @Test
    public void testRow() {
        Position position = new Position(1, 2);
        assertEquals(1, position.row());
    }

    @Test
    public void testCol() {
        Position position = new Position(1, 2);
        assertEquals(2, position.col());
    }

    @Test
    public void testUp() {
        Position position = new Position(2, 3);
        Position up = Position.up(position);
        assertEquals(1, up.row());
        assertEquals(3, up.col());
    }

    @Test
    public void testDown() {
        Position position = new Position(2, 3);
        Position down = Position.down(position);
        assertEquals(3, down.row());
        assertEquals(3, down.col());
    }

    @Test
    public void testLeft() {
        Position position = new Position(2, 3);
        Position left = Position.left(position);
        assertEquals(2, left.row());
        assertEquals(2, left.col());
    }

    @Test
    public void testRight() {
        Position position = new Position(2, 3);
        Position right = Position.right(position);
        assertEquals(2, right.row());
        assertEquals(4, right.col());
    }

    @Test
    public void testUpLeft() {
        Position position = new Position(2, 3);
        Position upLeft = Position.upLeft(position);
        assertEquals(1, upLeft.row());
        assertEquals(2, upLeft.col());
    }

    @Test
    public void testUpRight() {
        Position position = new Position(2, 3);
        Position upRight = Position.upRight(position);
        assertEquals(1, upRight.row());
        assertEquals(4, upRight.col());
    }

    @Test
    public void testDownLeft() {
        Position position = new Position(2, 3);
        Position downLeft = Position.downLeft(position);
        assertEquals(3, downLeft.row());
        assertEquals(2, downLeft.col());
    }

    @Test
    public void testDownRight() {
        Position position = new Position(2, 3);
        Position downRight = Position.downRight(position);
        assertEquals(3, downRight.row());
        assertEquals(4, downRight.col());
    }

}