package edu.ramapo.bbhatta.cmps366_pente_java;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ramapo.bbhatta.cmps366_pente_java.models.Board;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Position;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Stone;

public class BoardTest {

    Board defaultBoard;

    @Before
    public void setUp() {
        defaultBoard = new Board(19, 19);
    }


    @Test
    public void testBoardConstructor() {
        Board board = new Board(19, 21);

        assertEquals(19, board.getNoRows());
        assertEquals(21, board.getNoCols());

        // Iterate through the board and check that all positions are empty.
        for (int row = 0; row < board.getNoRows(); row++) {
            for (int col = 0; col < board.getNoCols(); col++) {
                assertEquals(Board.EMPTY, board.get(new Position(row, col)));
            }
        }
    }

    @Test
    public void testBoardCopyConstructor() {
        Board board = new Board(defaultBoard);

        board.set(9, 9, Stone.WHITE);

        Board copiedBoard = new Board(board);

        // Check that the copied board is the same as the original board.
        assertEquals(board.getNoRows(), copiedBoard.getNoRows());
        assertEquals(board.getNoCols(), copiedBoard.getNoCols());

        // Iterate through the board and check that all positions are the same.
        for (int row = 0; row < board.getNoRows(); row++) {
            for (int col = 0; col < board.getNoCols(); col++) {
                assertEquals(board.get(new Position(row, col)), copiedBoard.get(new Position(row, col)));
            }
        }

        // Check that the copied board is not the same object as the original board.
        Position origin = new Position(0, 0);
        board = board.set(0, 0, Stone.BLACK);
        assertNotEquals(board.get(origin), copiedBoard.get(origin));
    }


    @Test
    public void testSet() {

        Position center = new Position(9, 9);
        defaultBoard = defaultBoard.set(center, Stone.WHITE);
        assertEquals(Stone.WHITE, defaultBoard.get(center));

        Position position = new Position(0, 0);
        defaultBoard = defaultBoard.set(position, Stone.BLACK);
        assertEquals(Stone.BLACK, defaultBoard.get(position));
    }

    @Test
    public void testToStringTopRow() {

        Position position = new Position(0, 0);
        String str = defaultBoard.positionToString(position);
        assertEquals("A19", str);
    }

    @Test
    public void testToStringBottomRow() {

        Position position = new Position(18, 18);
        String str = defaultBoard.positionToString(position);
        assertEquals("S1", str);
    }

    @Test
    public void testToStringWithUpRight() {

        Position center = new Position(9, 9);
        String centerStr = defaultBoard.positionToString(center);
        assertEquals("J10", centerStr);

        Position upRight = Position.upRight(center);
        String upRightStr = defaultBoard.positionToString(upRight);
        assertEquals("K11", upRightStr);
    }

    @Test
    public void testIsInBoard() {
        Board board = new Board(8, 8);
        assertTrue(board.isInBoard(new Position(0, 0)));
        assertTrue(board.isInBoard(new Position(7, 7)));
        assertFalse(board.isInBoard(new Position(-1, 0)));
        assertFalse(board.isInBoard(new Position(0, -1)));
        assertFalse(board.isInBoard(new Position(8, 0)));
        assertFalse(board.isInBoard(new Position(0, 8)));
    }

    @Test
    public void testIsFull() {
        Board board = new Board(3, 3);
        assertFalse(board.isFull());
        board = board.set(new Position(0, 0), Stone.BLACK);
        board = board.set(new Position(0, 1), Stone.WHITE);
        board = board.set(new Position(0, 2), Stone.BLACK);
        board = board.set(new Position(1, 0), Stone.WHITE);
        board = board.set(new Position(1, 1), Stone.BLACK);
        board = board.set(new Position(1, 2), Stone.WHITE);
        board = board.set(new Position(2, 0), Stone.BLACK);
        board = board.set(new Position(2, 1), Stone.WHITE);
        board = board.set(new Position(2, 2), Stone.BLACK);
        assertTrue(board.isFull());
    }

    @Test
    public void testIsEmpty() {
        Board board = new Board(2, 2);
        assertTrue(board.isEmpty(0, 0));
        assertTrue(board.isEmpty(0, 1));
        assertTrue(board.isEmpty(1, 0));
        assertTrue(board.isEmpty(1, 1));
        board = board.set(new Position(0, 0), Stone.BLACK);
        assertFalse(board.isEmpty(0, 0));
        assertTrue(board.isEmpty(0, 1));
        assertTrue(board.isEmpty(1, 0));
        assertTrue(board.isEmpty(new Position(1, 1)));
    }

    @Test
    public void testStringToPositionValid() {
        Position pos = defaultBoard.stringToPosition("A1");
        assertEquals(new Position(18, 0), pos);

        pos = defaultBoard.stringToPosition("J10");
        assertEquals(new Position(9, 9), pos);

        pos = defaultBoard.stringToPosition("S19");
        assertEquals(new Position(0, 18), pos);
    }

    @Test
    public void testStringToPositionInvalid() {
        Position pos = defaultBoard.stringToPosition("A0");
        assertNull(pos);

        pos = defaultBoard.stringToPosition("T1");
        assertNull(pos);

        pos = defaultBoard.stringToPosition("A20");
        assertNull(pos);

        pos = defaultBoard.stringToPosition("BA");
        assertNull(pos);

        pos = defaultBoard.stringToPosition("A-1");
        assertNull(pos);

        pos = defaultBoard.stringToPosition("A");
        assertNull(pos);

        pos = defaultBoard.stringToPosition("1");
        assertNull(pos);

        pos = defaultBoard.stringToPosition("");
        assertNull(pos);
    }

    @Test
    public void testGetInvalidPosition() {
        Board board = new Board(19, 19);
        Stone testCell = board.get(-1, 0);
        assertNull(testCell);

        testCell = board.get(0, -1);
        assertNull(testCell);

        testCell = board.get(19, 0);
        assertNull(testCell);

        testCell = board.get(0, 19);
        assertNull(testCell);
    }

    @Test
    public void testGetRow() {
        Board board = new Board(3, 3);
        board = board.set(new Position(0, 0), Stone.BLACK);
        board = board.set(new Position(0, 1), Stone.WHITE);
        board = board.set(new Position(0, 2), Stone.BLACK);
        Stone[] row = board.getRow(0);
        assertEquals(Stone.BLACK, row[0]);
        assertEquals(Stone.WHITE, row[1]);
        assertEquals(Stone.BLACK, row[2]);
    }

    @Test
    public void testGetColumn() {
        Board board = new Board(3, 3);
        board = board.set(new Position(0, 0), Stone.BLACK);
        board = board.set(new Position(1, 0), Stone.WHITE);
        board = board.set(new Position(2, 0), Stone.BLACK);
        Stone[] column = board.getColumn(0);
        assertEquals(Stone.BLACK, column[0]);
        assertEquals(Stone.WHITE, column[1]);
        assertEquals(Stone.BLACK, column[2]);
    }

    @Test
    public void testGetNegativeDiagonal() {
        Board board = new Board(3, 3);
        board = board.set(new Position(0, 0), Stone.BLACK);
        board = board.set(new Position(1, 1), Stone.WHITE);
        board = board.set(new Position(2, 2), Stone.BLACK);
        Stone[] diagonal = board.getNegativeDiagonal(new Position(1, 1));
        assertEquals(Stone.BLACK, diagonal[0]);
        assertEquals(Stone.WHITE, diagonal[1]);
        assertEquals(Stone.BLACK, diagonal[2]);
    }

    @Test
    public void testGetPositiveDiagonal() {
        Board board = new Board(3, 3);
        board = board.set(new Position(0, 2), Stone.BLACK);
        board = board.set(new Position(1, 1), Stone.WHITE);
        board = board.set(new Position(2, 0), Stone.BLACK);
        Stone[] diagonal = board.getPositiveDiagonal(new Position(1, 1));
        assertEquals(Stone.BLACK, diagonal[0]);
        assertEquals(Stone.WHITE, diagonal[1]);
        assertEquals(Stone.BLACK, diagonal[2]);
    }

    @Test
    public void testGetAllPositiveDiagonalStarts() {
        Board board = new Board(3, 3);

        Position[] starts = board.getAllPositiveDiagonalStarts();

        Position[] expected = new Position[5];
        expected[0] = new Position(0, 0);
        expected[1] = new Position(1, 0);
        expected[2] = new Position(2, 0);
        expected[3] = new Position(2, 1);
        expected[4] = new Position(2, 2);

        assertArrayEquals(expected, starts);
    }

    @Test
    public void testGetAllNegativeDiagonalStarts() {
        Board board = new Board(3, 3);

        Position[] starts = board.getAllNegativeDiagonalStarts();

        Position[] expected = new Position[5];
        expected[0] = new Position(0, 0);
        expected[1] = new Position(0, 1);
        expected[2] = new Position(0, 2);
        expected[3] = new Position(1, 2);
        expected[4] = new Position(2, 2);

        assertArrayEquals(expected, starts);
    }
}
