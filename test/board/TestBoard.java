package board;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
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
        assertEquals(square, testee.getMovingShape());
        for (int i = 0; i < 50; i++) {
            testee.tick();
        }
        assertNull(testee.getMovingShape());
    }

    @Test
    public void cellsInBoard() {
        assertEquals("testee cells width wrong", 30, testee.getCells().length);
        assertEquals("testee cells height wrong", 10, testee.getCells()[0].length);
        assertEquals("cells not initialised to zero", 0, testee.getCells()[0][0]);
    }
}
