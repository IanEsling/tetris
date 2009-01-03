package shapes;

import board.Board;
import board.Cell;
import org.junit.Test;
import static shapes.ShapeTestUtils.assertBoardPopulation;


/**
 */
public class TestLShape extends AbstractShapeTest {

    @Test
    public void rotateOnceToRight() {
        board.addNewShape(getNewShape());
        board.tick();
        board.rotateShapeRight();
        populateFirstRightRotationCells();
        assertBoardPopulation(board, populatedCells);
    }

    @Test
    public void rightRotations() {
        board.addNewShape(getNewShape());
        board.tick();
        board.rotateShapeRight();
        populateFirstRightRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.rotateShapeRight();
        populateSecondRightRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.rotateShapeRight();
        board.tick();
        populateThirdRightRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.tick();
        board.rotateShapeRight();
        populateFourthRightRotationCells();
        assertBoardPopulation(board, populatedCells);
    }

    /*
           ----        -X--
           -XXX    >   -X--
           -X--        -XX-
           ----        ----
    */
    private void populateFourthRightRotationCells() {
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 4, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 5, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 5, Board.START_COL + 2));
    }

    /*
           ----        ----
           -XX-    >   -XXX
           --X-        -X--
           --X-        ----
    */
    private void populateThirdRightRotationCells() {
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 3));
        populatedCells.add(new Cell(Board.START_ROW + 4, Board.START_COL + 1));
    }

    /*
           ----        ----
           --X-    >   -XX-
           XXX-        --X-
           ----        --X-
    */
    private void populateSecondRightRotationCells() {
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 4, Board.START_COL + 2));
    }

    /*
           ----        ----
           -X--    >   --X-
           -X--        XXX-
           -XX-        ----
    */
    private void populateFirstRightRotationCells() {
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 2));
    }


    LShape getNewShape() {
        return new LShape();
    }

    void populateNewShapeOnBoardCells() {
        populatedCells.add(new Cell(Board.START_ROW, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 2));
    }

    void populateSecondShapeAtBottomCells() {
        populatedCells.add(new Cell(24, Board.START_COL + 1));
        populatedCells.add(new Cell(25, Board.START_COL + 1));
        populatedCells.add(new Cell(26, Board.START_COL + 1));
        populatedCells.add(new Cell(26, Board.START_COL + 2));
    }

    void populateFirstShapeAtBottomCells() {
        populatedCells.add(new Cell(27, Board.START_COL + 1));
        populatedCells.add(new Cell(28, Board.START_COL + 1));
        populatedCells.add(new Cell(29, Board.START_COL + 1));
        populatedCells.add(new Cell(29, Board.START_COL + 2));
    }

    void populateOneRowDownCells() {
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 2));
    }
}