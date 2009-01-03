package shapes;

import board.Cell;
import board.Board;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShapeTestUtils {

    public static Map<Integer, Integer> map(int x, int y) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(x, y);
        return map;
    }

    public static void assertBoardPopulation(Board board, List<Cell> populatedCells) {
        for (Cell cell : board.getCells()) {
            if (populatedCells.contains(cell))
                assertBoardCellHasShape(board, cell.row, cell.column);
            else
                assertBoardCellIsEmpty(board, cell.row, cell.column);
        }
    }


    private static void assertBoardCellIsEmpty(Board board, int x, int y) {
        assertBoardCellStatus(board, x, y, false);
    }

    private static void assertBoardCellHasShape(Board board, int x, int y) {
        assertBoardCellStatus(board, x, y, true);
    }

    private static void assertBoardCellStatus(Board board, int x, int y, boolean populated) {
        assertEquals("board cell " + x + "," + y + " not populated as expected: "
                + listOfPopulatedCells(board),
                populated, board.getCell(x, y).isPopulated());
    }

    public static List<Cell> listOfPopulatedCells(Board board) {
        List<Cell> populatedCells = new ArrayList<Cell>();
        for (Cell cell : board.getCells()) {
            if (cell.isPopulated()) populatedCells.add(cell);
        }
        return populatedCells;
    }
}