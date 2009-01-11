package shapes;

import board.Board;

/**
 */
public class AntiClockwiseBoardShapeRotator extends AbstractBoardShapeRotator {

    public AntiClockwiseBoardShapeRotator(){}
    
    public AntiClockwiseBoardShapeRotator(Board board) {
        super(board);
    }

    @Override
    public void rotateMatrixPosition(int[][] currentMatrix, int[][] newMatrix, int rowCount, int columnCount, int col) {
        newMatrix[columnCount][rowCount] = currentMatrix[rowCount][col];
    }
}
