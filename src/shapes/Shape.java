package shapes;

/**
 */
public abstract class Shape {

    public int[][] cells;
    private Rotator clockwiseRotator, antiClockwiseRotator;

    public Shape() {
        newCells();
        clockwiseRotator = new ClockwiseRotator(cells);
        antiClockwiseRotator = new AntiClockwiseRotator(cells);
        setShapeCells();
    }

    public abstract void setShapeCells();

    public int[][] getCells() {
        return cells;
    }

    private void newCells() {
        cells = new int[4][4];
        for (int[] row : cells) {
            for (Integer col : row) {
                col = 0;
            }
        }
    }

    public void rotateClockwise() {
        clockwiseRotator.rotateMatrix();
    }

    public void rotateAntiClockwise() {
        antiClockwiseRotator.rotateMatrix();
    }
}
