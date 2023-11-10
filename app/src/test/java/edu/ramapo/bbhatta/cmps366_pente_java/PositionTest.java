package edu.ramapo.bbhatta.cmps366_pente_java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import edu.ramapo.bbhatta.cmps366_pente_java.models.Position;

public class PositionTest {
    @Test
    public void testConstructor() {
        Position position = new Position(1, 2);
        assertEquals(1, position.getRow());
        assertEquals(2, position.getCol());
    }

    @Test
    public void testCopyConstructor() {
        Position position1 = new Position(1, 2);
        Position position2 = new Position(position1);
        assertEquals(position1.getRow(), position2.getRow());
        assertEquals(position1.getCol(), position2.getCol());
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
        assertEquals(1, position.getRow());
    }

    @Test
    public void testCol() {
        Position position = new Position(1, 2);
        assertEquals(2, position.getCol());
    }

    @Test
    public void testUp() {
        Position position = new Position(2, 3);
        Position up = Position.up(position);
        assertEquals(1, up.getRow());
        assertEquals(3, up.getCol());
    }

    @Test
    public void testDown() {
        Position position = new Position(2, 3);
        Position down = Position.down(position);
        assertEquals(3, down.getRow());
        assertEquals(3, down.getCol());
    }

    @Test
    public void testLeft() {
        Position position = new Position(2, 3);
        Position left = Position.left(position);
        assertEquals(2, left.getRow());
        assertEquals(2, left.getCol());
    }

    @Test
    public void testRight() {
        Position position = new Position(2, 3);
        Position right = Position.right(position);
        assertEquals(2, right.getRow());
        assertEquals(4, right.getCol());
    }

    @Test
    public void testUpLeft() {
        Position position = new Position(2, 3);
        Position upLeft = Position.upLeft(position);
        assertEquals(1, upLeft.getRow());
        assertEquals(2, upLeft.getCol());
    }

    @Test
    public void testUpRight() {
        Position position = new Position(2, 3);
        Position upRight = Position.upRight(position);
        assertEquals(1, upRight.getRow());
        assertEquals(4, upRight.getCol());
    }

    @Test
    public void testDownLeft() {
        Position position = new Position(2, 3);
        Position downLeft = Position.downLeft(position);
        assertEquals(3, downLeft.getRow());
        assertEquals(2, downLeft.getCol());
    }

    @Test
    public void testDownRight() {
        Position position = new Position(2, 3);
        Position downRight = Position.downRight(position);
        assertEquals(3, downRight.getRow());
        assertEquals(4, downRight.getCol());
    }

}