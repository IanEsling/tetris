package shapes;

import java.util.Random;

/**
 */
public class ShapeFactory {
    public static Shape getNewShapeAtRandom(){
        return new Square();
    }
}
