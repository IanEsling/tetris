package shapes;

import board.Cell;

import java.util.List;

import static util.Util.eachCell;
import util.ArrayCellCallback;

/**
 */
public abstract class AbstractRotator implements Rotator {

    public int[][] rotate(int[][] rotateMe) {
        return getRotatedMatrix(rotateMe);
        
//        if (!shapeWillRotateOffBoard(currentMatrix, shapeCells, newMatrix) &&
//                !rotatedShapeWillOccupyAPopulatedCell(newMatrix)) {
//            rotateShape(currentMatrix, newMatrix);
//        }
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
