package shapes;

public class XLShape extends Shape {

    /*
             XX
             X
             X
     */
    @Override
    void setShapeCells() {
        cells[0][1] = 1;
        cells[0][2] = 1;
        cells[1][1] = 1;
        cells[2][1] = 1;
    }
}