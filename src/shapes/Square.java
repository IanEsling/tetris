package shapes;

public class Square extends Shape {

    @Override
    void setShapeCells() {
        cells[0][1] = 1;
        cells[0][2] = 1;
        cells[1][1] = 1;
        cells[1][2] = 1;
    }
}
