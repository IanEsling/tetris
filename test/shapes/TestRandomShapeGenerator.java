package shapes;

import static junit.framework.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class TestRandomShapeGenerator {

    RandomShapeGenerator testee;
    Map<Shape, Integer> shapeCount;

    @Before
    public void createGenerator() {
        testee = new RandomShapeGenerator();
    }

    @Before
    public void shapeCounter() {
        shapeCount = new HashMap<Shape, Integer>();
        shapeCount.put(new Square(), 0);
        shapeCount.put(new LShape(), 0);
        shapeCount.put(new XLShape(), 0);
    }

    @Test
    public void randomShapeGeneration() throws RandomShapeGenerationException {
        for (int i = 0; i < 1000; i++) {
            Shape newShape = testee.getNewShapeAtRandom();
            for (Shape shape : shapeCount.keySet()) {
                if (newShape.getClass().getName().equals(shape.getClass().getName())) {
                    Integer count = shapeCount.get(shape);
                    assertFalse("new shape " + newShape + " same object as key " + shape,
                            shape.equals(newShape));
                    shapeCount.put(shape, ++count);
                }
            }
        }
        System.out.println(shapeCount.toString());
    }
}
