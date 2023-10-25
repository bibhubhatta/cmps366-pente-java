package edu.ramapo.bbhatta.cmps366_pente_java;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    Board defaultBoard;

    @Before
    public void setUp() {
        defaultBoard = new Board(19, 19);
    }


    @Test
    public void testBoardConstructor() {
        Board board = new Board(19, 21);

        assertEquals(19, board.numRows());
        assertEquals(21, board.numCols());

        // Iterate through the board and check that all positions are empty.
        for (int row = 0; row < board.numRows(); row++) {
            for (int col = 0; col < board.numCols(); col++) {
                assertEquals(Board.EMPTY, board.get(new Position(row, col)));
            }
        }
    }

    @Test
    public void testBoardCopyConstructor() {
        Board board = new Board(defaultBoard);

        board.set(9, 9, Board.WHITE);

        Board copiedBoard = new Board(board);

        // Check that the copied board is the same as the original board.
        assertEquals(board.numRows(), copiedBoard.numRows());
        assertEquals(board.numCols(), copiedBoard.numCols());

        // Iterate through the board and check that all positions are the same.
        for (int row = 0; row < board.numRows(); row++) {
            for (int col = 0; col < board.numCols(); col++) {
                assertEquals(board.get(new Position(row, col)), copiedBoard.get(new Position(row, col)));
            }
        }

        // Check that the copied board is not the same object as the original board.
        Position origin = new Position(0, 0);
        board.set(0, 0, Board.BLACK);
        assertNotEquals(board.get(origin), copiedBoard.get(origin));
    }


    @Test
    public void testSet() {

        Position center = new Position(9, 9);
        defaultBoard.set(center, Board.WHITE);
        assertEquals(Board.WHITE, defaultBoard.get(center));

        Position position = new Position(0, 0);
        defaultBoard.set(position, Board.BLACK);
        assertEquals(Board.BLACK, defaultBoard.get(position));
    }

    @Test
    public void testToStringTopRow() {

        Position position = new Position(0, 0);
        String str = defaultBoard.positionToString(position);
        assertEquals("S1", str);
    }

    @Test
    public void testToStringBottomRow() {

        Position position = new Position(18, 18);
        String str = defaultBoard.positionToString(position);
        assertEquals("A19", str);
    }

    @Test
    public void testToStringWithUpRight() {

        Position center = new Position(9, 9);
        String centerStr = defaultBoard.positionToString(center);
        assertEquals("J10", centerStr);

        Position upRight = center.upRight();
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
        board.set(new Position(0, 0), Board.BLACK);
        board.set(new Position(0, 1), Board.WHITE);
        board.set(new Position(0, 2), Board.BLACK);
        board.set(new Position(1, 0), Board.WHITE);
        board.set(new Position(1, 1), Board.BLACK);
        board.set(new Position(1, 2), Board.WHITE);
        board.set(new Position(2, 0), Board.BLACK);
        board.set(new Position(2, 1), Board.WHITE);
        board.set(new Position(2, 2), Board.BLACK);
        assertTrue(board.isFull());
    }

    @Test
    public void testIsEmpty() {
        Board board = new Board(2, 2);
        assertTrue(board.isEmpty(0, 0));
        assertTrue(board.isEmpty(0, 1));
        assertTrue(board.isEmpty(1, 0));
        assertTrue(board.isEmpty(1, 1));
        board.set(new Position(0, 0), Board.BLACK);
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
}