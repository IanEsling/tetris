package shapes;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 */
public class TestShapes {

    @Test
    public void squareShape() {
        Shape square = new Square();
        Integer[][] squareCells = new Integer[][]{
                new Integer[]{0, 1, 1, 0},
                new Integer[]{0, 1, 1, 0},
                new Integer[]{0, 0, 0, 0},
                new Integer[]{0, 0, 0, 0}};
        assertArrayEquals("square shape wrong", squareCells, square.getCells());
    }
}


