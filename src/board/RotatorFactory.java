package board;

import util.Rotator;
import util.ClockwiseRotator;
import util.AntiClockwiseRotator;

/**
 */
class RotatorFactory {
    final Rotator clockwiseRotator;
    final Rotator antiClockwiseRotator;

    RotatorFactory() {
        clockwiseRotator = new ClockwiseRotator();
        antiClockwiseRotator = new AntiClockwiseRotator();
    }

    int[][] rotate(Rotation rotation, int[][] rotateMe){
        return get(rotation).rotate(rotateMe); 
    }

    Rotator get(Rotation rotation) {
        return rotation == Rotation.AntiClockwise ? antiClockwiseRotator : clockwiseRotator;
    }
}
