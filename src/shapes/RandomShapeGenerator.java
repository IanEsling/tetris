package shapes;

import java.util.Random;

/**
 */
public class RandomShapeGenerator {

    enum Shapes {
        square{Shape getShape(){return new Square();}},
        l{Shape getShape(){return new LShape();}},
        xl {Shape getShape(){return new XLShape();}};

        abstract Shape getShape();
    }

    public static Shape getNewShapeAtRandom() {
        return Shapes.values()[randomShapeIndex()].getShape();
    }

    private static int randomShapeIndex() {
        return new Random().nextInt(Shapes.values().length);
    }
}
