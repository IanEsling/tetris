package shapes;

import board.Board;
import board.Cell;

import java.util.List;

import static util.Util.eachCell;
import util.ArrayCellCallback;

/**
 */
public abstract class AbstractRotator implements Rotator {

    private Board board;

    //default constructor for tests
    AbstractRotator() {
    }

    AbstractRotator(Board board) {
        this.board = board;
    }

    public void rotate() {
        int[][] currentMatrix = board.movingShapeLayoutArray();
        List<Cell> shapeCells = board.movingShapeCells();
        int[][] newMatrix = getRotatedMatrix(currentMatrix);
        
        if (!shapeWillRotateOffBoard(currentMatrix, shapeCells, newMatrix) &&
                !rotatedShapeWillOccupyAPopulatedCell(newMatrix)) {
            rotateShape(currentMatrix, newMatrix);
        }
    }
    
    private void rotateShape(final int[][] currentMatrix, final int[][] newMatrix) {

        eachCell(currentMatrix, new ArrayCellCallback(){
            @Override
            public void cell(int row, int col) {
                currentMatrix[row][col] = newMatrix[row][col];
            }
        });
    }

    private boolean shapeWillRotateOffBoard(int[][] currentMatrix, List<Cell> shapeCells, int[][] newMatrix) {
        return shapeWillRotateOffLeftEdgeOfBoard(currentMatrix, shapeCells, newMatrix) ||
                shapeWillRotateOffRightEdgeOfBoard(currentMatrix, shapeCells, newMatrix);
    }

    private boolean shapeWillRotateOffRightEdgeOfBoard(int[][] currentMatrix, List<Cell> shapeCells, int[][] newMatrix) {
        return (shapeWillMoveToRight(newMatrix, currentMatrix) && shapeIsInColumn(shapeCells, board.getColumns() - 1));
    }

    private boolean shapeWillRotateOffLeftEdgeOfBoard(int[][] currentMatrix, List<Cell> shapeCells, int[][] newMatrix) {
        return (shapeWillMoveToLeft(newMatrix, currentMatrix) && shapeIsInColumn(shapeCells, 0));
    }

    private boolean rotatedShapeWillOccupyAPopulatedCell(int[][] newMatrix) {
        for (int row = 0; row < newMatrix.length; row++) {
            for (int col = 0; col < newMatrix[0].length; col++) {
                if (boardCellAt(row, col).isPopulated() &&
                        newMatrix[row][col] == 1 &&
                        !(board.mapper.shapeCellsAsList().contains(boardCellAt(row, col)))) return true;
            }
        }
        return false;
    }

    private Cell boardCellAt(int row, int col) {
        return board.getCell(boardRow(row), boardCol(col));
    }

    private int boardCol(int col) {
        return (board.mapper.startingBoardColumn() + col) >= board.getColumns() ?
                board.getColumns() - 1 :
                (board.mapper.startingBoardColumn() + col) < 0 ?
                        0 :
                        board.mapper.startingBoardColumn() + col;
    }

    private int boardRow(int row) {
        return (board.mapper.startingBoardRow() + row) >= board.getRows() ?
                board.getRows() - 1 :
                (board.mapper.startingBoardRow() + row) < 0 ?
                        0 :
                        board.mapper.startingBoardRow() + row;
    }

    private boolean shapeWillMoveToRight(int[][] newMatrix, int[][] currentMatrix) {
        return matrixContainsCellInColumn(newMatrix, 3) &&
                !matrixContainsCellInColumn(currentMatrix, 3);
    }

    private boolean shapeIsInColumn(List<Cell> shapeCells, int column) {
        for (Cell cell : shapeCells) {
            if (cell.column == column) return true;
        }
        return false;
    }

    private boolean shapeWillMoveToLeft(int[][] newMatrix, int[][] currentMatrix) {
        return matrixContainsCellInColumn(newMatrix, 0) &&
                !matrixContainsCellInColumn(currentMatrix, 0);
    }

    private boolean matrixContainsCellInColumn(int[][] newMatrix, int column) {
        for (int[] row : newMatrix) {
            if (row[column] == 1) return true;
        }
        return false;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public int[][] getRotatedMatrix(int[][] currentMatrix) {
        int[][] newMatrix = new int[4][4];
        int rowCount = 0;
        for (int[] row : currentMatrix) {
            int columnCount = 0;
            for (int col = currentMatrix.length - 1; col >= 0; col--) {
                rotateMatrixPosition(currentMatrix, newMatrix, rowCount, columnCount, col);
                columnCount++;
            }
            rowCount++;
        }
        return newMatrix;
    }

    protected abstract void rotateMatrixPosition(int[][] currentMatrix, int[][] newMatrix, int rowCount, int columnCount, int col);
}
