package shapes;

import board.Board;
import board.Cell;
import org.junit.Before;
import org.junit.Test;
import static shapes.ShapeTestUtils.assertBoardPopulation;

import java.util.ArrayList;
import java.util.List;

/**
 */
public abstract class AbstractShapeTest {
    Board board;
    List<Cell> populatedCells;

    abstract Shape getNewShape();

    abstract void populateNewShapeOnBoardCells();

    abstract void populateFirstShapeAtBottomCells();

    abstract void populateSecondShapeAtBottomCells();

    abstract void populateOneRowDownCells();

    @Test
    public void moveShapeDownOneRowOnBoardTick() {
        board.addNewShape(getNewShape());
        board.tick();
        populateOneRowDownCells();
        assertBoardPopulation(board, populatedCells);
    }

    @Test
    public void moveShapesAllWayDownBoard() {
        addNewShapeAndMoveToBottom();
        populateFirstShapeAtBottomCells();
        assertBoardPopulation(board, populatedCells);
        addNewShapeAndMoveToBottom();
        populateSecondShapeAtBottomCells();
        assertBoardPopulation(board, populatedCells);
    }

    void addNewShapeAndMoveToBottom() {
        board.addNewShape(getNewShape());
        moveShapeToBottom();
    }

    void moveShapeToBottom() {
        while (board.movingShapeCanMoveDown()) {
            board.tick();
        }
    }

    @Test
    public void putShapeOnBoard() {
        board.addNewShape(getNewShape());
        populateNewShapeOnBoardCells();
        assertBoardPopulation(board, populatedCells);
    }

    @Before
    public void createBoard() {
        board = new Board(30, 10) {
            @Override
            public void addNewShapeAtRandom() {
                addNewShape(getNewShape());
            }
        };//30 rows, 10 columns
        populatedCells = new ArrayList<Cell>();
    }
}
