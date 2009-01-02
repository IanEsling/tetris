package shapes;

import board.Board;


/**
 */
public class TestXLShape extends AbstractShapeTest {

    Shape getNewShape() {
        return new XLShape();
    }

    void populateNewShapeOnBoardCells() {
        populatedCells.add(map(Board.START_ROW, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW, Board.START_COL + 2));
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 2, Board.START_COL + 1));
    }

    void populateSecondShapeAtBottomCells() {
        populatedCells.add(map(24, Board.START_COL + 1));
        populatedCells.add(map(24, Board.START_COL + 2));
        populatedCells.add(map(25, Board.START_COL + 1));
        populatedCells.add(map(26, Board.START_COL + 1));
    }

    void populateFirstShapeAtBottomCells() {
        populatedCells.add(map(27, Board.START_COL + 1));
        populatedCells.add(map(27, Board.START_COL + 2));
        populatedCells.add(map(28, Board.START_COL + 1));
        populatedCells.add(map(29, Board.START_COL + 1));
    }

    void populateOneRowDownCells() {
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 1, Board.START_COL + 2));
        populatedCells.add(map(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(map(Board.START_ROW + 3, Board.START_COL + 1));
    }
}