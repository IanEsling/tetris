package board;

import shapes.Rotator;
import shapes.ClockwiseRotator;
import shapes.AntiClockwiseRotator;

/**
 */
class RotatorFactory {
    final Rotator clockwiseRotator;
    final Rotator antiClockwiseRotator;

    RotatorFactory(Board board) {
        clockwiseRotator = new ClockwiseRotator(board);
        antiClockwiseRotator = new AntiClockwiseRotator(board);
    }

    Rotator get(Rotation rotation) {
        return rotation == Rotation.AntiClockwise ? antiClockwiseRotator : clockwiseRotator;
    }
}
