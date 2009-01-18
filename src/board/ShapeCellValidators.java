package board;

/**
 */
public class ShapeCellValidators {

    ShapeMovementValidator movementValidator;
    ShapeRotationValidator rotationValidator;

    ShapeCellValidators(ShapeLayoutToBoardCellMapper mapper){
        movementValidator = new ShapeMovementValidator(mapper);
        rotationValidator = new ShapeRotationValidator(mapper);
    }

    boolean isValid(Movement movement){
        return movementValidator.isValidMove(movement);
    }

    boolean isValid(Rotation rotation){
        return rotationValidator.isValidRotation(rotation);
    }
}
