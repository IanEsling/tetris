package shapes;

import board.Board;

/**
 */
public class TestSquare extends AbstractShapeTest {

    @Override
    Shape getNewShape() {
        return new Square();
    }

    @Override
    void populateNewShapeOnBoardCells() {
        populatedCells.add(map(Board.START_ROW, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW, Board.START_COL + 2));
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 2));
    }

    @Override
    void populateFirstShapeAtBottomCells() {
        populatedCells.add(map(28, Board.START_COL + 1));
        populatedCells.add(map(28, Board.START_COL + 2));
        populatedCells.add(map(29, Board.START_COL + 1));
        populatedCells.add(map(29, Board.START_COL + 2));
    }

    @Override
    void populateSecondShapeAtBottomCells() {
        populatedCells.add(map(26, Board.START_COL + 1));
        populatedCells.add(map(26, Board.START_COL + 2));
        populatedCells.add(map(27, Board.START_COL + 1));
        populatedCells.add(map(27, Board.START_COL + 2));
    }

    @Override
    void populateOneRowDownCells() {
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 2));
        populatedCells.add(map(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 2, Board.START_COL + 2));
    }
}


