package shapes;

import java.util.Random;

/**
 */
public class RandomShapeGenerator {

    enum Shapes {
        square{Shape getShape(){return new Square();}},
        l{Shape getShape(){return new LShape();}},
        xl {Shape getShape(){return new XLShape();}},
        z{Shape getShape() {return new ZShape();}},
        xz{Shape getShape() {return new XZShape();}},
        bar{Shape getShape() {return new Bar();}};

        abstract Shape getShape();
    }

    public Shape getNewShapeAtRandom() {
        return Shapes.values()[randomShapeIndex()].getShape();
    }

    private static int randomShapeIndex() {
        return new Random().nextInt(Shapes.values().length);
    }
}
