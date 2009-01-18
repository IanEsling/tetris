package util;

/**
 */
public class AntiClockwiseRotator extends AbstractRotator {

    @Override
    public void rotateMatrixPosition(int[][] currentMatrix, int[][] newMatrix, int rowCount, int columnCount, int col) {
        newMatrix[columnCount][rowCount] = currentMatrix[rowCount][col];
    }
}
