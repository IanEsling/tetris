package shapes;

import board.Board;
import board.Cell;
import static shapes.ShapeTestUtils.map;

/**
 */
public class TestSquare extends AbstractShapeTest {

    @Override
    Shape getNewShape() {
        return new Square();
    }

    @Override
    void populateNewShapeOnBoardCells() {
        populatedCells.add(new Cell(Board.START_ROW, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 2));
    }

    @Override
    void populateFirstShapeAtBottomCells() {
        populatedCells.add(new Cell(28, Board.START_COL + 1));
        populatedCells.add(new Cell(28, Board.START_COL + 2));
        populatedCells.add(new Cell(29, Board.START_COL + 1));
        populatedCells.add(new Cell(29, Board.START_COL + 2));
    }

    @Override
    void populateSecondShapeAtBottomCells() {
        populatedCells.add(new Cell(26, Board.START_COL + 1));
        populatedCells.add(new Cell(26, Board.START_COL + 2));
        populatedCells.add(new Cell(27, Board.START_COL + 1));
        populatedCells.add(new Cell(27, Board.START_COL + 2));
    }

    @Override
    void populateOneRowDownCells() {
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 2));
    }
}


