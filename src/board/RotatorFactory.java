package board;

import shapes.BoardShapeRotator;
import shapes.ClockwiseBoardShapeRotator;
import shapes.AntiClockwiseBoardShapeRotator;

/**
 */
class RotatorFactory {
    final BoardShapeRotator clockwiseBoardShapeRotator;
    final BoardShapeRotator antiClockwiseBoardShapeRotator;

    RotatorFactory(Board board) {
        clockwiseBoardShapeRotator = new ClockwiseBoardShapeRotator(board);
        antiClockwiseBoardShapeRotator = new AntiClockwiseBoardShapeRotator(board);
    }

    BoardShapeRotator get(Rotation rotation) {
        return rotation == Rotation.AntiClockwise ? antiClockwiseBoardShapeRotator : clockwiseBoardShapeRotator;
    }
}
