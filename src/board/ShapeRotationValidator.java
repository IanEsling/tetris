package board;

/**
 */
class ShapeRotationValidator {
    private ShapeLayoutToBoardCellMapper mapper;

    public ShapeRotationValidator(ShapeLayoutToBoardCellMapper mapper) {
        this.mapper = mapper;
    }

    boolean isValidRotation(int[][] newMatrix) {
        return (!shapeWillRotateOffBoard(newMatrix) &&
                !rotatedShapeWillOccupyAPopulatedCell(newMatrix));
    }

    private boolean rotatedShapeWillOccupyAPopulatedCell(int[][] newMatrix) {
        for (int row = 0; row < newMatrix.length; row++) {
            for (int col = 0; col < newMatrix[0].length; col++) {
                if (mapper.boardCellAt(row, col).isPopulated() &&
                        newMatrix[row][col] == 1 &&
                        !(mapper.shapeCellsAsList().contains(mapper.boardCellAt(row, col)))) return true;
            }
        }
        return false;
    }

    private boolean shapeWillRotateOffBoard(int[][] newMatrix) {
        return shapeWillRotateOffLeftEdgeOfBoard(newMatrix) ||
                shapeWillRotateOffRightEdgeOfBoard(newMatrix);
    }

    private boolean shapeWillRotateOffRightEdgeOfBoard(int[][] newMatrix) {
        return (shapeWillMoveToRight(newMatrix) && shapeIsInColumn(mapper.totalBoardColumns() - 1));
    }

    private boolean shapeWillRotateOffLeftEdgeOfBoard(int[][] newMatrix) {
        return (shapeWillMoveToLeft(newMatrix) && shapeIsInColumn(0));
    }


    private boolean shapeWillMoveToRight(int[][] newMatrix) {
        return matrixContainsCellInColumn(newMatrix, 3) &&
                !matrixContainsCellInColumn(mapper.shape.getLayoutArray(), 3);
    }

    private boolean shapeIsInColumn(int column) {
        for (Cell cell : mapper.shapeCellsAsList()) {
            if (cell.column == column) return true;
        }
        return false;
    }

    private boolean shapeWillMoveToLeft(int[][] newMatrix) {
        return matrixContainsCellInColumn(newMatrix, 0) &&
                !matrixContainsCellInColumn(mapper.shape.getLayoutArray(), 0);
    }

    private boolean matrixContainsCellInColumn(int[][] newMatrix, int column) {
        for (int[] row : newMatrix) {
            if (row[column] == 1) return true;
        }
        return false;
    }
}
