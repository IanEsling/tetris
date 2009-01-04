package shapes;

import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class TestRandomShapeGenerator {

    Map<Shape, Integer> shapeCount;

    @Before
    public void shapeCounter() {
        shapeCount = new HashMap<Shape, Integer>();
        for (RandomShapeGenerator.Shapes shape : RandomShapeGenerator.Shapes.values()){
            shapeCount.put(shape.getShape(), 0);
        }
    }

    @Test
    public void randomShapeGeneration() {
        for (int i = 0; i < 1000; i++) {
            Shape newShape = RandomShapeGenerator.getNewShapeAtRandom();
            for (Shape shape : shapeCount.keySet()) {
                if (newShape.getClass().getName().equals(shape.getClass().getName())) {
                    Integer count = shapeCount.get(shape);
                    assertFalse("new shape " + newShape + " same object as key " + shape,
                            shape.equals(newShape));
                    shapeCount.put(shape, ++count);
                }
            }
        }
        //for a visual check that it looks sensible
        System.out.println(shapeCount.toString());
        //just make sure we've not missed any
        assertFalse(shapeCount.containsValue(0));
    }
}
