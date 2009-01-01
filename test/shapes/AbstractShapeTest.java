package shapes;

import org.junit.Before;
import board.Board;

import java.util.*;

/**
 */
public class AbstractShapeTest {
    Board board;
    List<Map<Integer, Integer>> populatedCells;

    @Before
    public void createBoard() {
        board = new Board(30,10);//30 rows, 10 columns
        populatedCells = new ArrayList<Map<Integer, Integer>>();
    }

    protected Map<Integer, Integer> map(int x, int y){
        Map<Integer, Integer> map = new HashMap<Integer,Integer>();
        map.put(x, y);
        return map;
    }

    protected void assertBoardPopulation(List<Map<Integer, Integer>> populatedCells)
    {
        for (int row=0;row<board.getCells().length;row++){
            for (int col=0;col<board.getCells()[0].length;col++){
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
        org.junit.Assert.assertEquals("board cell " + x + "," + y + " not populated as expected: "
                + Arrays.deepToString(board.getCells()),
                populated ? 1 : 0, board.getCells()[x][y]);
    }
}
