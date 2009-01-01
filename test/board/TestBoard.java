package board;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import shapes.Square;

import java.util.*;

public class TestBoard {

    Board testee;

    @Before
    public void createBoard() {
        testee = new Board(30,10);//30 rows, 10 columns
    }

    @Test
    public void moveSquareAllWayDownBoard()
    {
        testee.addNewShape(new Square());
        for (int i=0;i<50;i++)
        {
            testee.tick();
        }
        List<Map<Integer, Integer>> populatedCells = new ArrayList<Map<Integer, Integer>>();
        populatedCells.add(map(28, Board.START_COL+1));
        populatedCells.add(map(28, Board.START_COL+2));
        populatedCells.add(map(29, Board.START_COL+1));
        populatedCells.add(map(29, Board.START_COL+2));
        assertBoardPopulation(populatedCells);
    }

    @Test
    public void moveSquareDownOneRowOnBoardTick()
    {
        testee.addNewShape(new Square());
        testee.tick();
        List<Map<Integer, Integer>> populatedCells = new ArrayList<Map<Integer, Integer>>();
        populatedCells.add(map(Board.START_ROW+1, Board.START_COL+1));
        populatedCells.add(map(Board.START_ROW+1, Board.START_COL+2));
        populatedCells.add(map(Board.START_ROW+2, Board.START_COL+1));
        populatedCells.add(map(Board.START_ROW+2, Board.START_COL+2));
        assertBoardPopulation(populatedCells);
    }

    @Test
    public void cellsInBoard() {
        assertEquals("testee cells width wrong", 30, testee.getCells().length);
        assertEquals("testee cells height wrong", 10, testee.getCells()[0].length);
        assertEquals("cells not initialised to zero", 0, testee.getCells()[0][0]);
    }

    @Test
    public void putSquareOnBoard() {
        testee.addNewShape(new Square());
        List<Map<Integer, Integer>> populatedCells = new ArrayList<Map<Integer, Integer>>();
        populatedCells.add(map(Board.START_ROW, Board.START_COL+1));
        populatedCells.add(map(Board.START_ROW, Board.START_COL+2));
        populatedCells.add(map(Board.START_ROW+1, Board.START_COL+1));
        populatedCells.add(map(Board.START_ROW+1, Board.START_COL+2));
        assertBoardPopulation(populatedCells);
    }

    private Map<Integer, Integer> map(int x, int y){
        Map<Integer, Integer> map = new HashMap<Integer,Integer>();
        map.put(x, y);
        return map;
    }

    private void assertBoardPopulation(List<Map<Integer, Integer>> populatedCells)
    {
        for (int row=0;row<testee.getCells().length;row++){
            for (int col=0;col<testee.getCells()[0].length;col++){
                if (cellPopulated(populatedCells, row, col))
                    assertBoardCellHasShape(row, col);
                else
                    assertBoardCellIsEmpty(row, col);
            }
        }
    }

    private boolean cellPopulated(List<Map<Integer, Integer>> populatedCells, int row, int col) {
        for (Map<Integer, Integer> cell : populatedCells){
            if (cell.containsKey(row) && cell.containsValue(col))
            {
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
        assertEquals("testee cell " + x + "," + y + " not populated as expected: "
                + Arrays.deepToString(testee.getCells()),
                populated ? 1 : 0, testee.getCells()[x][y]);
    }

}
