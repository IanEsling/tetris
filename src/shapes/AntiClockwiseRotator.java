package shapes;

import board.Board;

/**
 */
public class AntiClockwiseRotator extends AbstractRotator {

    public AntiClockwiseRotator(){}
    
    public AntiClockwiseRotator(Board board) {
        super(board);
    }

    @Override
    public void rotateMatrixPosition(int[][] currentMatrix, int[][] newMatrix, int rowCount, int columnCount, int col) {
        newMatrix[columnCount][rowCount] = currentMatrix[rowCount][col];
    }
}
