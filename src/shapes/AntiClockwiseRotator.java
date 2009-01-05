package shapes;

/**
 */
public class AntiClockwiseRotator extends AbstractRotator {
    public AntiClockwiseRotator(int[][] matrix) {
        super(matrix);
    }

    @Override
    public void rotateMatrixPosition(int[][] newMatrix, int rowCount, int columnCount, int col) {
        newMatrix[columnCount][rowCount] = matrix[rowCount][col];
    }
}
