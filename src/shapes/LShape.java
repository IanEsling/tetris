package shapes;

import java.awt.*;

public class LShape extends Shape {

    /*
            X
            X
            XX
     */
    @Override
    public void setShapeCells() {
        cells[0][1] = 1;
        cells[1][1] = 1;
        cells[2][1] = 1;
        cells[2][2] = 1;
    }

    @Override
    public Color getColour() {
        return Color.blue;
    }
}