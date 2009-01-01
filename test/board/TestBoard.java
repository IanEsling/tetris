package board;

import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;
import shapes.Shape;
import shapes.Square;

public class TestBoard {

    Board testee;

    @Before
    public void createBoard() {
        testee = new Board(30, 10);//30 rows, 10 columns
    }

    @Test
    public void boardKnowsShapeIsMoving() {
        Shape square = new Square();
        testee.addNewShape(square);
        assertNotNull("failed to set moving shape", testee.getMovingShape());
        for (int i = 0; i < 27; i++) {
            testee.tick();
        }
        assertNotNull("shape should still be moving", testee.getMovingShape());

        testee.tick();
        assertNull("shape shouldn't be moving anymore", testee.getMovingShape());
    }

    @Test
    public void cellsInBoard() {
        assertEquals("testee cells height wrong", 29, maxCellHeight());
        assertEquals("testee cells width wrong", 9, maxCellWidth());
        assertNotNull("cells not initialised", testee.getCells().get(0));
    }

    private int maxCellHeight() {
        int height = 0;
        for (Board.Cell cell : testee.getCells()) {
            if (cell.row > height) height = cell.row;
        }
        return height;
    }


    private int maxCellWidth() {
        int width = 0;
        for (Board.Cell cell : testee.getCells()) {
            if (cell.column > width) width = cell.column;
        }
        return width;
    }
}
