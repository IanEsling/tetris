package shapes;

import java.awt.*;

public class Bar extends Shape {

    @Override
    public void setShapeCells() {
        cells[1][1] = 1;
        cells[1][2] = 1;
        cells[1][3] = 1;
        cells[1][0] = 1;
    }

    @Override
    public Color getColour() {
        return Color.orange;
    }
}