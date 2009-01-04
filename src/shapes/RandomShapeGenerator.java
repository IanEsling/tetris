package shapes;

import java.util.Random;

/**
 */
public class RandomShapeGenerator {

    public static Shape getNewShapeAtRandom() throws RandomShapeGenerationException {
        Integer number = new Random().nextInt(3);
        switch(number){
            case 0:return new Square();
            case 1:return new LShape();
            case 2:return new XLShape();
        }
        throw new RandomShapeGenerationException(number);
    }
}
