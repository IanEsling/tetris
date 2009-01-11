package shapes;

import board.Board;

/**
 */
public class ClockwiseRotator extends AbstractRotator {

    public ClockwiseRotator(){}

    public ClockwiseRotator(Board board) {
        super(board);
    }

    @Override
    public void rotateMatrixPosition(int[][] currentMatrix, int[][] newMatrix, int rowCount, int columnCount, int col) {
        newMatrix[currentMatrix.length - 1 - columnCount][currentMatrix.length - 1 - rowCount] = currentMatrix[rowCount][col];
    }
}
