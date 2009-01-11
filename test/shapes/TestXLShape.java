package shapes;

import board.Board;
import board.Cell;
import org.junit.Test;
import static shapes.ShapeTestUtils.assertBoardPopulation;


/**
 */
public class TestXLShape extends AbstractShapeTest {

    @Test
    public void doNotRotateIfLeftEdgeOfBoard() {
        board.addNewShape(getNewShape());
        populatedCells.add(new Cell(Board.START_ROW, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        assertBoardPopulation("XLShape in starting position", board, populatedCells);
        board.moveShapeToLeft();
        board.moveShapeToLeft();
        board.moveShapeToLeft();
        board.moveShapeToLeft();
        board.rotateShapeAntiClockwise();
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW, 0));
        populatedCells.add(new Cell(Board.START_ROW, 1));
        populatedCells.add(new Cell(Board.START_ROW + 1, 0));
        populatedCells.add(new Cell(Board.START_ROW + 2, 0));
        assertBoardPopulation("XLShape on left edge after anti-clockwise attempt", board, populatedCells);
    }


    /* should look like this at the bottom, X=first shape, Y=second

            YY
            YXX
            YX
             X
     */
    @Test
    public void notBottomCellsCausingFallToStop() {
        addNewShapeAndMoveToBottom();
        populateFirstShapeAtBottomCells();
        assertBoardPopulation(board, populatedCells);
        board.addNewShape(getNewShape());
        board.moveShapeToLeft();
        moveShapeToBottom();
        populateSecondShapeHookedOntoFirst();
        assertBoardPopulation(board, populatedCells);
    }

    Shape getNewShape() {
        return new XLShape();
    }

    void populateSecondShapeHookedOntoFirst() {
        populatedCells.add(new Cell(26, Board.START_COL));
        populatedCells.add(new Cell(26, Board.START_COL + 1));
        populatedCells.add(new Cell(27, Board.START_COL));
        populatedCells.add(new Cell(28, Board.START_COL));
    }

    void populateNewShapeOnBoardCells() {
        populatedCells.add(new Cell(Board.START_ROW, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
    }

    void populateSecondShapeAtBottomCells() {
        populatedCells.add(new Cell(24, Board.START_COL + 1));
        populatedCells.add(new Cell(24, Board.START_COL + 2));
        populatedCells.add(new Cell(25, Board.START_COL + 1));
        populatedCells.add(new Cell(26, Board.START_COL + 1));
    }

    void populateFirstShapeAtBottomCells() {
        populatedCells.add(new Cell(27, Board.START_COL + 1));
        populatedCells.add(new Cell(27, Board.START_COL + 2));
        populatedCells.add(new Cell(28, Board.START_COL + 1));
        populatedCells.add(new Cell(29, Board.START_COL + 1));
    }

    void populateOneRowDownCells() {
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
    }
}