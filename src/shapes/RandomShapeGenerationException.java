package shapes;

import java.util.Random;

/**
 */
public class RandomShapeGenerationException extends Throwable {
    public RandomShapeGenerationException(Integer number) {
        super("error trying to generate new random shape using "+number);
    }
}
