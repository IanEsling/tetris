package shapes;

import java.awt.*;

/**
 */
public abstract class Shape {

    final int[][] cells;

    Shape() {
        cells = new int[4][4];
        setShapeCells();
    }

    protected abstract void setShapeCells();

    public abstract Color getColour();

    public int[][] getLayoutArray() {
        return cells;
    }
}
