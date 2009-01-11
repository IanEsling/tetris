package shapes;

import java.awt.*;

/**
 */
public abstract class Shape {

    public int[][] cells;

    public Shape() {
        cells = new int[4][4];
        setShapeCells();
    }

    public abstract void setShapeCells();

    public abstract Color getColour();

    public int[][] getCells() {
        return cells;
    }
}
