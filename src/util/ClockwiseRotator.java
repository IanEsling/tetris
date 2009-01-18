package util;

/**
 */
public class ClockwiseRotator extends AbstractRotator {

    @Override
    public void rotateMatrixPosition(int[][] currentMatrix, int[][] newMatrix, int rowCount, int columnCount, int col) {
        newMatrix[currentMatrix.length - 1 - columnCount][currentMatrix.length - 1 - rowCount] = currentMatrix[rowCount][col];
    }
}
