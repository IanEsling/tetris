package shapes;

import java.awt.*;

public class TShape extends Shape {

    @Override
    public void setShapeCells() {
        cells[2][1] = 1;
        cells[1][2] = 1;
        cells[2][2] = 1;
        cells[2][3] = 1;
    }

    @Override
    public Color getColour() {
        return Color.yellow;
    }
}