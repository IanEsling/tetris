package shapes;

/**
 */
public abstract class AbstractRotator implements Rotator {

    int[][] matrix;

    public AbstractRotator(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] rotateMatrix() {
        int[][] newMatrix = new int[4][4];
        int rowCount = 0;
        for (int[] row : matrix) {
            int columnCount = 0;
            for (int col = matrix.length - 1; col >= 0; col--) {
                rotate(newMatrix, rowCount, columnCount, col);
                columnCount++;
            }
            rowCount++;
        }
        return newMatrix;
    }

    public abstract void rotate(int[][] newMatrix, int rowCount, int columnCount, int col);
}
