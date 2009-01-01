package shapes;

/**
 */
public abstract class Shape {

    int[][] cells;

    Shape(){
        cells = new int[4][4];
        setShapeCells();
    }

    abstract void setShapeCells();

    public int[][] getCells(){
        return cells;
    }
}
