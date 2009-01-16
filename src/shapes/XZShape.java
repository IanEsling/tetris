package shapes;

import java.awt.*;

public class XZShape extends Shape {

    @Override
    public void setShapeCells() {
        cells[1][1] = 1;
        cells[1][2] = 1;
        cells[2][1] = 1;
        cells[2][0] = 1;
    }

    @Override
    public Color getColour() {
        return Color.magenta;
    }
}