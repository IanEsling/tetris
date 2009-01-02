package shapes;

import board.Board;
import org.junit.Test;


/**
 */
public class TestXLShape extends AbstractShapeTest {

    /* should look like this at the bottom, X=first shape, Y=second

            YY
            YXX
            YX
             X
     */
    @Test
    public void notBottomCellsCausingFallToStop(){
        board.addNewShape(getNewShape());
        for (int i = 0; i < 50; i++) {
            board.tick();
        }
        populateFirstShapeAtBottomCells();
        assertBoardPopulation(populatedCells);
        board.addNewShape(getNewShape());
        board.moveShapeToLeft();
        for (int i = 0; i < 50; i++) {
            board.tick();
        }
        populateSecondShapeHookedOntoFirst();
        assertBoardPopulation(populatedCells);
    }


    Shape getNewShape() {
        return new XLShape();
    }


    void populateSecondShapeHookedOntoFirst() {
        populatedCells.add(map(26, Board.START_COL));
        populatedCells.add(map(26, Board.START_COL + 1));
        populatedCells.add(map(27, Board.START_COL));
        populatedCells.add(map(28, Board.START_COL));
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