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
        Integer number = new Random().nextInt(3);
        return Shapes.values()[number].getShape();
    }
}
