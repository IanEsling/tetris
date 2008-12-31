package board;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import shapes.Square;

public class TestBoard {

    Board testee;

    @Before
    public void createBoard() {
        testee = new Board(10, 30);
    }

    @Test
    public void cellsInBoard() {
        assertEquals("testee cells width wrong", 10, testee.getCells().length);
        assertEquals("testee cells height wrong", 30, testee.getCells()[0].length);
        assertEquals("cells not intialised to zero", new Integer(0), testee.getCells()[0][0]);
    }

    @Test
    public void putSquareOnBoard() {
        testee.addNewShape(new Square());
        assertBoardCellHasShape(Board.START_ROW, Board.START_COL + 1);
        assertBoardCellHasShape(Board.START_ROW, Board.START_COL + 2);
        assertBoardCellHasShape(Board.START_ROW + 1, Board.START_COL + 1);
        assertBoardCellHasShape(Board.START_ROW + 1, Board.START_COL + 2);
    }

    private void assertBoardCellHasShape(int x, int y) {
        assertEquals("testee cell " + x + "," + y + "not populated as expected: "
                + Arrays.deepToString(testee.getCells()),
                new Integer(1), testee.getCells()[x][y]);
    }

}
