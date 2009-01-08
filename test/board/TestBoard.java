package board;

import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;
import shapes.LShape;
import shapes.Shape;
import shapes.Square;

import java.util.List;

public class TestBoard {

    Board testee;

    @Before
    public void createBoard() {
        testee = new Board(30, 10);//30 rows, 10 columns
    }

    @Test
    public void gameOver(){
        assertFalse(testee.gameOver());
        testee.addNewShape(new Square());
        assertFalse(testee.gameOver());
        testee.addNewShape(new LShape());
        assertTrue(testee.gameOver());
    }

    @Test
    public void addNewShapeWhenMovingShapeStops() {
        Shape originalShape = new Square();
        testee.addNewShape(originalShape);
        for (int i = 0; i < 27; i++) {
            testee.tick();
        }
        assertEquals("moving shape not original", originalShape, testee.getMovingShape().getShape());
        testee.tick();
        assertNotNull("moving shape is null", testee.getMovingShape());
        assertNotSame("still moving original shape", testee.getMovingShape().getShape(), originalShape);
    }

    @Test
    public void moveShapeAround() {
        testee.addNewShape(new LShape());
        List<Cell> startCells = testee.getMovingShape().listOfCells();
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
        List<Cell> newCells = testee.getMovingShape().listOfCells();
        assertEquals("start and new cells not same size", startCells.size(), newCells.size());
        for (Cell cell : startCells) {
            assertTrue("testee doesn't contain new cell, rows:" + rows + ", columns:" + columns +
                    " for cell: " + cell + ".  Testee Cells: " + testee.getCells(),
                    testee.getCells().contains(new Cell(cell.row + rows, cell.column + columns)));
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
        assertNotNull("failed to set moving shape", testee.getMovingShape());
        for (int i = 0; i < 27; i++) {//relies on square being 2 cells tall
            testee.tick();
        }
        assertNotNull("shape should still be moving", testee.getMovingShape());

        testee.tick();
        assertNotSame("shape shouldn't be moving anymore", movingShape, testee.getMovingShape());
    }

    @Test
    public void cellsInBoard() {
        assertEquals("testee cells height wrong", 29, maxCellHeight());
        assertEquals("testee cells width wrong", 9, maxCellWidth());
        assertNotNull("cells not initialised", testee.getCells().get(0));
    }

    private int maxCellHeight() {
        int height = 0;
        for (Cell cell : testee.getCells()) {
            if (cell.row > height) height = cell.row;
        }
        return height;
    }

    private int maxCellWidth() {
        int width = 0;
        for (Cell cell : testee.getCells()) {
            if (cell.column > width) width = cell.column;
        }
        return width;
    }
}
