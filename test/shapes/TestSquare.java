package shapes;

import board.Board;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 */
public class TestSquare extends AbstractShapeTest {

    @Test
    public void putSquareOnBoard() {
        board.addNewShape(new Square());
        List<Map<Integer, Integer>> populatedCells = new ArrayList<Map<Integer, Integer>>();
        populatedCells.add(map(Board.START_ROW, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW, Board.START_COL + 2));
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 2));
        assertBoardPopulation(populatedCells);
    }

    @Test
    public void moveSquaresAllWayDownBoard() {
        board.addNewShape(new Square());
        for (int i = 0; i < 50; i++) {
            board.tick();
        }
        populatedCells.add(map(28, Board.START_COL + 1));
        populatedCells.add(map(28, Board.START_COL + 2));
        populatedCells.add(map(29, Board.START_COL + 1));
        populatedCells.add(map(29, Board.START_COL + 2));
        assertBoardPopulation(populatedCells);
        board.addNewShape(new Square());
        for (int i = 0; i < 50; i++) {
            board.tick();
        }
        populatedCells.add(map(26, Board.START_COL + 1));
        populatedCells.add(map(26, Board.START_COL + 2));
        populatedCells.add(map(27, Board.START_COL + 1));
        populatedCells.add(map(27, Board.START_COL + 2));
        assertBoardPopulation(populatedCells);
    }

    @Test
    public void moveSquareDownOneRowOnBoardTick() {
        board.addNewShape(new Square());
        board.tick();
        List<Map<Integer, Integer>> populatedCells = new ArrayList<Map<Integer, Integer>>();
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 2));
        populatedCells.add(map(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 2, Board.START_COL + 2));
        assertBoardPopulation(populatedCells);
    }
}


