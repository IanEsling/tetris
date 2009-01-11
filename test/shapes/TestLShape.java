package shapes;

import board.Board;
import board.Cell;
import org.junit.Test;
import static shapes.ShapeTestUtils.assertBoardPopulation;


/**
 */
public class TestLShape extends AbstractShapeTest {

    @Test
    public void doNotRotateIfLeftEdgeOfBoard() {
        board.addNewShape(getNewShape());
        board.moveShapeToLeft();
        board.moveShapeToLeft();
        board.moveShapeToLeft();
        board.moveShapeToLeft();
        board.rotateShapeClockwise();
        populatedCells.add(new Cell(Board.START_ROW + 1, 0));
        populatedCells.add(new Cell(Board.START_ROW + 1, 1));
        populatedCells.add(new Cell(Board.START_ROW + 1, 2));
        populatedCells.add(new Cell(Board.START_ROW + 2, 0));
        assertBoardPopulation("original L-Shape on left of board", board, populatedCells);
        board.rotateShapeAntiClockwise();
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW, 0));
        populatedCells.add(new Cell(Board.START_ROW + 1, 0));
        populatedCells.add(new Cell(Board.START_ROW + 2, 0));
        populatedCells.add(new Cell(Board.START_ROW + 2, 1));
        assertBoardPopulation("L-Shape rotated anti-clockwise on left of board", board, populatedCells);
        board.moveShapeToRight();
        board.rotateShapeClockwise();
        board.moveShapeToLeft();
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 1, 0));
        populatedCells.add(new Cell(Board.START_ROW + 1, 1));
        populatedCells.add(new Cell(Board.START_ROW + 1, 2));
        populatedCells.add(new Cell(Board.START_ROW + 2, 0));
        assertBoardPopulation("L-Shape rotated clockwise on left edge", board, populatedCells);
        board.rotateShapeClockwise();
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 1, 0));
        populatedCells.add(new Cell(Board.START_ROW + 1, 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, 1));
        assertBoardPopulation("L-Shape rotated clockwise once more on left edge", board, populatedCells);
    }

    @Test
    public void doNotRotateIfRightEdgeOfBoard() {
        board.addNewShape(getNewShape());
        board.moveShapeToRight();
        board.moveShapeToRight();
        board.moveShapeToRight();
        board.moveShapeToRight();
        board.rotateShapeClockwise();
        populatedCells.add(new Cell(Board.START_ROW, Board.START_COL + 5));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 5));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 5));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 6));
        assertBoardPopulation("L-Shape rotated clockwise on right edge", board, populatedCells);
        board.rotateShapeAntiClockwise();
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 4));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 5));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 6));
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 6));
        assertBoardPopulation("L-Shape rotated anti-clockwise on right edge", board, populatedCells);
    }

    @Test
    public void rotateMoveAndDrop() {
        board.addNewShape(getNewShape());
        board.rotateShapeAntiClockwise();
        board.moveShapeToLeft();
        moveShapeToBottom();
        populatedCells.add(new Cell(Board.START_ROW + 29, Board.START_COL - 1));
        populatedCells.add(new Cell(Board.START_ROW + 29, Board.START_COL));
        populatedCells.add(new Cell(Board.START_ROW + 29, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 28, Board.START_COL + 1));
        assertBoardPopulation(board, populatedCells);
    }

    @Test
    public void clockwiseRotations() {
        board.addNewShape(getNewShape());
        board.tick();
        board.rotateShapeClockwise();
        populateFirstClockwiseRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.rotateShapeClockwise();
        populateSecondClockwiseRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.rotateShapeClockwise();
        populateThirdClockwiseRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.rotateShapeClockwise();
        populateFourthClockwiseRotationCells();
        assertBoardPopulation(board, populatedCells);
    }

    @Test
    public void antiClockwiseRotations() {
        board.addNewShape(getNewShape());
        board.tick();
        board.rotateShapeAntiClockwise();
        populateFirstAntiClockwiseRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.rotateShapeAntiClockwise();
        populateSecondAntiClockwiseRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.rotateShapeAntiClockwise();
        board.tick();
        populateThirdAntiClockwiseRotationCells();
        assertBoardPopulation(board, populatedCells);
        board.tick();
        board.rotateShapeAntiClockwise();
        populateFourthAntiClockwiseRotationCells();
        assertBoardPopulation(board, populatedCells);
    }

    /*
           ----        -X--
           -XXX    >   -X--
           -X--        -XX-
           ----        ----
    */
    private void populateFourthAntiClockwiseRotationCells() {
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
    private void populateThirdAntiClockwiseRotationCells() {
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
    private void populateSecondAntiClockwiseRotationCells() {
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
    private void populateFirstAntiClockwiseRotationCells() {
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 2));
    }

    /*
           ----        -X--
           --X-    >   -X--
           XXX-        -XX-
           ----        ----
    */
    private void populateFourthClockwiseRotationCells() {
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 1, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 2));
    }

    /*
           ----        ----
           -XX-    >   --X-
           --X-        XXX-
           --X-        ----
    */
    private void populateThirdClockwiseRotationCells() {
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 2));
    }

    /*
           ----        ----
           -XXX    >   -XX-
           -X--        --X-
           ----        --X-
    */
    private void populateSecondClockwiseRotationCells() {
        populatedCells.clear();
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 4, Board.START_COL + 2));
    }

    /*
           ----        ----
           -X--    >   -XXX
           -X--        -X--
           -XX-        ----
    */
    private void populateFirstClockwiseRotationCells() {
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 1));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 2));
        populatedCells.add(new Cell(Board.START_ROW + 2, Board.START_COL + 3));
        populatedCells.add(new Cell(Board.START_ROW + 3, Board.START_COL + 1));
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