package shapes;

/**
 */
public abstract class Shape {

    public int[][] cells;
    private Rotator clockwise, antiClockwise;

    public Shape() {
        cells = new int[4][4];
        clockwise = new ClockwiseRotator(cells);
        antiClockwise = new AntiClockwiseRotator(cells);
        setShapeCells();
    }

    public abstract void setShapeCells();

    public int[][] getCells() {
        return cells;
    }

    public void rotateClockwise() {
        clockwise.rotate();
    }

    public void rotateAntiClockwise() {
        antiClockwise.rotate();
    }
}
