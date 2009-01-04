package shapes;

/**
 */
public class ClockwiseRotator extends AbstractRotator {
    public ClockwiseRotator(int[][] matrix) {
        super(matrix);
    }

    @Override
    public void rotate(int[][] newMatrix, int rowCount, int columnCount, int col) {
        newMatrix[matrix.length - 1 - columnCount][matrix.length - 1 - rowCount] = matrix[rowCount][col];
    }
}
