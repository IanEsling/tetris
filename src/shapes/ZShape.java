package shapes;

import java.awt.*;

public class ZShape extends Shape {

    @Override
    public void setShapeCells() {
        cells[1][1] = 1;
        cells[1][2] = 1;
        cells[2][2] = 1;
        cells[2][3] = 1;
    }

    @Override
    public Color getColour() {
        return Color.CYAN;
    }
}