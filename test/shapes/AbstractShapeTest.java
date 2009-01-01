package shapes;

import board.Board;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public abstract class AbstractShapeTest {
    Board board;
    List<Map<Integer, Integer>> populatedCells;

    abstract Shape getNewShape();
    abstract void populateNewShapeOnBoardCells();
    abstract void populateFirstShapeAtBottomCells();
    abstract void populateSecondShapeAtBottomCells();
    abstract void populateOneRowDownCells();

    @Test
    public void moveShapeDownOneRowOnBoardTick() {
        board.addNewShape(getNewShape());
        board.tick();
        populateOneRowDownCells();
        assertBoardPopulation(populatedCells);
    }

    @Test
    public void moveShapesAllWayDownBoard() {
        board.addNewShape(getNewShape());
        for (int i = 0; i < 50; i++) {
            board.tick();
        }
        populateFirstShapeAtBottomCells();
        assertBoardPopulation(populatedCells);
        board.addNewShape(getNewShape());
        for (int i = 0; i < 50; i++) {
            board.tick();
        }
        populateSecondShapeAtBottomCells();
        assertBoardPopulation(populatedCells);
    }

    @Test
    public void putShapeOnBoard() {
        board.addNewShape(getNewShape());
        populateNewShapeOnBoardCells();
        assertBoardPopulation(populatedCells);
    }

    @Before
    public void createBoard() {
        board = new Board(30, 10);//30 rows, 10 columns
        populatedCells = new ArrayList<Map<Integer, Integer>>();
    }

    protected Map<Integer, Integer> map(int x, int y) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(x, y);
        return map;
    }

    protected void assertBoardPopulation(List<Map<Integer, Integer>> populatedCells) {
        for (Board.Cell cell : board.getCells()) {
            if (cellPopulated(populatedCells, cell.row, cell.column))
                assertBoardCellHasShape(cell.row, cell.column);
            else
                assertBoardCellIsEmpty(cell.row, cell.column);
        }
    }

    private boolean cellPopulated(List<Map<Integer, Integer>> populatedCells, int row, int col) {
        for (Map<Integer, Integer> cell : populatedCells) {
            if (cell.containsKey(row) && cell.containsValue(col)) {
                return true;
            }
        }
        return false;
    }

    private void assertBoardCellIsEmpty(int x, int y) {
        assertBoardCellStatus(x, y, false);
    }

    private void assertBoardCellHasShape(int x, int y) {
        assertBoardCellStatus(x, y, true);
    }

    private void assertBoardCellStatus(int x, int y, boolean populated) {
        assertEquals("board cell " + x + "," + y + " not populated as expected: "
                + board.getCells(),
                populated, board.getCell(x, y).isPopulated());
    }
}
