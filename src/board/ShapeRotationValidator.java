package board;

/**
 */
class ShapeRotationValidator extends AbstractValidator {

    public ShapeRotationValidator(ShapeLayoutToBoardCellMapper mapper) {
        super(mapper);
    }

    boolean isValidRotation(Rotation rotation) {
        int[][] newMatrix = getRotatedShapeLayoutArray(rotation);

        return (!shapeWillRotateOffBoard(newMatrix) &&
                !rotatedShapeWillOccupyAPopulatedCell(newMatrix));
    }

    private boolean rotatedShapeWillOccupyAPopulatedCell(int[][] newMatrix) {
        for (int row = 0; row < newMatrix.length; row++) {
            for (int col = 0; col < newMatrix[0].length; col++) {
                if (boardCellAt(row, col).isPopulated() &&
                        newMatrix[row][col] == 1 &&
                        !(shapeCells().contains(boardCellAt(row, col)))) return true;
            }
        }
        return false;
    }

    private boolean shapeWillRotateOffBoard(int[][] newMatrix) {
        return shapeWillRotateOffLeftEdgeOfBoard(newMatrix) ||
                shapeWillRotateOffRightEdgeOfBoard(newMatrix);
    }

    private boolean shapeWillRotateOffRightEdgeOfBoard(int[][] newMatrix) {
        return (shapeWillMoveToRight(newMatrix) && shapeIsInColumn(totalBoardColumns() - 1));
    }

    private boolean shapeWillRotateOffLeftEdgeOfBoard(int[][] newMatrix) {
        return (shapeWillMoveToLeft(newMatrix) && shapeIsInColumn(0));
    }

    private boolean shapeWillMoveToRight(int[][] newMatrix) {
        return matrixContainsCellInColumn(newMatrix, 3) &&
                !matrixContainsCellInColumn(shapeLayoutArray(), 3);
    }

    private boolean shapeIsInColumn(int column) {
        for (Cell cell : shapeCells()) {
            if (cell.column == column) return true;
        }
        return false;
    }


    private boolean shapeWillMoveToLeft(int[][] newMatrix) {
        return matrixContainsCellInColumn(newMatrix, 0) &&
                !matrixContainsCellInColumn(shapeLayoutArray(), 0);
    }

    private boolean matrixContainsCellInColumn(int[][] newMatrix, int column) {
        for (int[] row : newMatrix) {
            if (row[column] == 1) return true;
        }
        return false;
    }

    protected int[][] getRotatedShapeLayoutArray(Rotation rotation) {
        return mapper.getRotatedShapeLayout(rotation);
    }
}
