package board;

import static board.Movement.Left;
import static board.Movement.Right;
import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;
import shapes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBoard {

    Board testee;
    Integer index;

    @Before
    public void createBoard() {
        testee = new Board(30, 10);//30 rows, 10 columns
        index = 0;
    }

    @Test
    public void knowWhichShapeIsComingNext() {
        NonRandomShapeGenerator testRsg = new NonRandomShapeGenerator();
        Shape square = testRsg.put(0, new Square());
        Shape lShape = testRsg.put(1, new LShape());
        Shape bar = testRsg.put(2, new Bar());
        testee.setRandomShapeGenerator(testRsg);
        testee.addNewShapeAtRandom();
        moveShapeToBottom();
        assertTrue("square should be on board", testee.mapper.getShape() == square);
        assertTrue("lshape should be coming next", testee.getNextShape() == lShape);
        moveShapeToBottom();
        assertTrue("lshape should be on board", testee.mapper.getShape() == lShape);
        assertTrue("bar should be next", testee.getNextShape() == bar);
    }

    class NonRandomShapeGenerator extends RandomShapeGenerator {
        Integer index = 0;
        Map<Integer, Shape> shapes = new HashMap<Integer, Shape>();

        Shape put(Integer index, Shape shape){
            shapes.put(index, shape);
            return shape;
        }

        @Override
        public Shape getNewShapeAtRandom() {
            return shapes.get(index++);
        }
    }


    @Test
    public void completeRowOnlyRemovedWhenShapeHasStoppedMoving() {
        testee = new Board(30, 10) {
            @Override
            public void addNewShape(Shape shape) {
                super.addNewShape(new Square());
            }
        };
        testee.addNewShapeAtRandom();
        moveSidewaysThenToBottom(-4);
        moveSidewaysThenToBottom(-4);
        moveSidewaysThenToBottom(-2);
        moveSidewaysThenToBottom(-2);
        moveSidewaysThenToBottom(2);
        moveSidewaysThenToBottom(2);
        moveSidewaysThenToBottom(4);
        moveSidewaysThenToBottom(4);
        for (int i = 0; i < 23; i++) {
            testee.tick();
        }
        assertRowPopulation(26, true);
        testee.tick();
        assertRowPopulation(26, true);
        assertRowPopulation(27, true);
        testee.tick();
        assertRowPopulation(27, true);
        assertRowPopulation(28, true);
        testee.tick();
        assertRowPopulation(28, true);
        assertRowPopulation(29, true);
        testee.tick();
        assertRowPopulation(26, false);
        assertRowPopulation(27, false);
        for (Cell cell : testee.getBoardCells()) {
            if (cell.row == 28 || cell.row == 29) {
                if (cell.column == 4 || cell.column == 5) {
                    assertFalse("bottom cell not empty as expected: " + populatedCells(), cell.isPopulated());
                } else {
                    assertTrue("bottom cell not populated as expected: " + populatedCells(), cell.isPopulated());
                }
            }
        }
    }

    private void assertRowPopulation(int row, boolean populated) {
        for (Cell cell : testee.getBoardCells()) {
            if (cell.row == row) assertTrue("row " + row + " not populated: " + populatedCells(),
                    populated ? cell.isPopulated() : !cell.isPopulated());
        }
    }

    @Test
    public void removeCompleteBottomRow() {
        testee = new Board(30, 10) {
            @Override
            public void addNewShape(Shape shape) {
                super.addNewShape(new LShape());
            }
        };
        testee.addNewShapeAtRandom();
        moveSidewaysThenToBottom(-4);
        moveSidewaysThenToBottom(-2);
        moveShapeToBottom();
        moveSidewaysThenToBottom(2);
        moveSidewaysThenToBottom(4);
        assertTrue("new moving shape in wrong position: " + testee.getBoardCells(),
                testee.getCell(1, 4).isPopulated());
        for (Cell cell : testee.getBoardCells()) {
            if (cell.row == 29 || cell.row == 28) assertTrue("bottom row not populated correctly: " + populatedCells(),
                    cell.column % 2 == 1 ? !cell.isPopulated() : cell.isPopulated());
            if (cell.row == 27) assertFalse(cell.isPopulated());
        }
        assertNewMovingShapeHasNotMoved();
    }

    @Test
    public void removeCompletedRowNotAtBottom() {
        testee = new Board(30, 12) {
            @Override
            public void addNewShape(Shape shape) {
                super.addNewShape(new LShape());
            }
        };
        testee.addNewShapeAtRandom();
        rotateClockwiseMoveSidewaysThenToBottom(-4);
        rotateClockwiseMoveSidewaysThenToBottom(-1);
        rotateClockwiseMoveSidewaysThenToBottom(2);
        rotateClockwiseMoveSidewaysThenToBottom(5);
        assertNewMovingShapeHasNotMoved();
        for (Cell cell : testee.getBoardCells()) {
            if (cell.row == 29) assertTrue("bottom row not populated correctly: " + populatedCells(),
                    cell.column % 3 > 0 ? !cell.isPopulated() : cell.isPopulated());
            if (cell.row == 28) assertFalse("row 28 shouldn't be populated: " + populatedCells(), cell.isPopulated());
        }
    }

    private void assertNewMovingShapeHasNotMoved() {
        assertTrue("new moving shape shouldn't have moved when row removed: " + testee.getBoardCells(),
                testee.getCell(1, 4).isPopulated());
        assertTrue("new moving shape shouldn't have moved when row removed: " + testee.getBoardCells(),
                testee.getCell(2, 4).isPopulated());
        assertTrue("new moving shape shouldn't have moved when row removed: " + testee.getBoardCells(),
                testee.getCell(3, 4).isPopulated());
        assertTrue("new moving shape shouldn't have moved when row removed: " + testee.getBoardCells(),
                testee.getCell(3, 5).isPopulated());
    }

    private void rotateClockwiseMoveSidewaysThenToBottom(int colsToMove) {
        testee.rotateShapeClockwise();
        moveSidewaysThenToBottom(colsToMove);
    }

    private void moveSidewaysThenToBottom(int colsToMove) {
        Movement movement = colsToMove > 0 ? Right : Left;

        for (int i = 0; i < Math.abs(colsToMove); i++) {
            testee.moveShape(movement);
        }

        moveShapeToBottom();
    }

    private List<Cell> populatedCells() {
        List<Cell> populatedCells = new ArrayList<Cell>();
        for (Cell cell : testee.getBoardCells()) {
            if (cell.isPopulated()) populatedCells.add(cell);
        }
        return populatedCells;
    }

    private void moveShapeToBottom() {
        while (testee.movingShapeCanMoveDown()) {
            testee.tick();
        }
        testee.tick();
    }

    @Test
    public void gameOver() {
        assertFalse(testee.gameOver());
        testee.addNewShape(new Square());
        assertFalse(testee.gameOver());
        testee.addNewShape(new LShape());
        assertTrue(testee.gameOver());
    }

    @Test
    public void shapeCannotMoveSidewaysIfCellAlreadyPopulated() {
        testee = new SquareGeneratingBoard(30, 10);
        Shape originalSquare = new Square();
        testee.addNewShape(originalSquare);

        for (int i = 0; i < 28; i++) {
            testee.tick();
        }
        assertNotNull("moving shape is null", testee.mapper.getShape());
        assertFalse("moving shape is still original square", testee.mapper.getShape() == originalSquare);
        originalSquare = testee.mapper.getShape();
        testee.moveShapeToLeft();
        testee.moveShapeToLeft();
        for (int i = 0; i < 26; i++) {
            testee.tick();
        }
        assertTrue("moving shape isn't original square", testee.mapper.getShape() == originalSquare);
        List<Cell> originalCells = testee.mapper.shapeCellsAsList();
        testee.moveShapeToRight();
        assertEquals(originalCells, testee.mapper.shapeCellsAsList());

    }

    @Test
    public void addNewShapeWhenMovingShapeStops() {
        Shape originalShape = new Square();
        testee.addNewShape(originalShape);
        for (int i = 0; i < 27; i++) {
            testee.tick();
        }
        assertEquals("moving shape not original", originalShape, testee.mapper.getShape());
        testee.tick();
        assertNotNull("moving shape is null", testee.mapper.getShape());
        assertNotSame("still moving original shape", testee.mapper.getShape(), originalShape);
    }

    @Test
    public void moveShapeAround() {
        testee.addNewShape(new LShape());
        List<Cell> startCells = testee.mapper.shapeCellsAsList();
        testee.moveShapeToLeft();
        assertShapeHasMoved(startCells, 0, -1);
        testee.moveShapeToLeft();
        assertShapeHasMoved(startCells, 0, -2);
        testee.tick();
        assertShapeHasMoved(startCells, 1, -2);
        testee.moveShapeToRight();
        assertShapeHasMoved(startCells, 1, -1);
        testee.tick();
        assertShapeHasMoved(startCells, 2, -1);
        testee.moveShapeToRight();
        testee.moveShapeToRight();
        assertShapeHasMoved(startCells, 2, 1);
        testee.tick();
        testee.moveShapeToLeft();
        testee.moveShapeToLeft();
        testee.moveShapeToLeft();
        testee.moveShapeToLeft();
        testee.moveShapeToLeft();
        testee.moveShapeToLeft();
        assertShapeHasMoved(startCells, 3, -Board.START_COL - 1);
        for (int i = 0; i < 20; i++) {
            testee.moveShapeToRight();
        }
        assertShapeHasMoved(startCells, 3, 4);
        testee.tick();
        assertShapeHasMoved(startCells, 4, 4);
    }

    private void assertShapeHasMoved(List<Cell> startCells, int rows, int columns) {
        List<Cell> newCells = testee.mapper.shapeCellsAsList();
        assertEquals("start and new cells not same size", startCells.size(), newCells.size());
        for (Cell cell : startCells) {
            assertTrue("testee doesn't contain new cell, rows:" + rows + ", columns:" + columns +
                    " for cell: " + cell + ".  Testee Cells: " + testee.getBoardCells(),
                    testee.getBoardCells().contains(new Cell(cell.row + rows, cell.column + columns)));
            assertTrue("shape not moved as expected, rows=" + rows + ",columns=" + columns +
                    ", newCells: " + newCells +
                    ", startCells: " + startCells,
                    newCells.contains(testee.getCell(cell.row + rows, cell.column + columns)));
        }
    }

    @Test
    public void boardKnowsShapeIsMoving() {
        Shape movingShape = new Square();
        testee.addNewShape(movingShape);
        assertNotNull("failed to set moving shape", testee.mapper.getShape());
        for (int i = 0; i < 27; i++) {//relies on square being 2 cells tall
            testee.tick();
        }
        assertNotNull("shape should still be moving", testee.mapper.getShape());

        testee.tick();
        assertNotSame("shape shouldn't be moving anymore", movingShape, testee.mapper.getShape());
    }

    @Test
    public void cellsInBoard() {
        assertEquals("testee cells height wrong", 29, maxCellHeight());
        assertEquals("testee cells width wrong", 9, maxCellWidth());
        assertNotNull("cells not initialised", testee.getBoardCells().get(0));
    }

    private int maxCellHeight() {
        int height = 0;
        for (Cell cell : testee.getBoardCells()) {
            if (cell.row > height) height = cell.row;
        }
        return height;
    }

    private int maxCellWidth() {
        int width = 0;
        for (Cell cell : testee.getBoardCells()) {
            if (cell.column > width) width = cell.column;
        }
        return width;
    }

    class SquareGeneratingBoard extends Board {

        public SquareGeneratingBoard(int rows, int columns) {
            super(rows, columns);
        }

        @Override
        public void addNewShapeAtRandom() {
            addNewShape(new Square());
        }
    }
}
