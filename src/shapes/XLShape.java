package shapes;

import java.awt.*;

public class XLShape extends Shape {

    /*
             XX
             X
             X
     */
    @Override
    public void setShapeCells() {
        cells[0][1] = 1;
        cells[0][2] = 1;
        cells[1][1] = 1;
        cells[2][1] = 1;
    }

    @Override
    public Color getColour() {
        return Color.green;
    }
}