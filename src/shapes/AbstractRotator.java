package shapes;

/**
 */
public abstract class AbstractRotator implements Rotator {

    int[][] matrix;

    public AbstractRotator(int[][] matrix) {
        this.matrix = matrix;
    }

    @SuppressWarnings({"ManualArrayCopy", "UnusedDeclaration"})
    public void rotate() {
        int[][] newMatrix = new int[4][4];
        int rowCount = 0;
        for (int[] row : matrix) {
            int columnCount = 0;
            for (int col = matrix.length - 1; col >= 0; col--) {
                rotateMatrixPosition(newMatrix, rowCount, columnCount, col);
                columnCount++;
            }
            rowCount++;
        }
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] = newMatrix[row][col];
            }
        }
    }

    public abstract void rotateMatrixPosition(int[][] newMatrix, int rowCount, int columnCount, int col);
}
