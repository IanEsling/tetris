package shapes;

/**
 */
public abstract class Shape {

    public int[][] cells;

    public Shape(){
        cells = new int[4][4];
        setShapeCells();
    }

    public abstract void setShapeCells();

    public int[][] getCells(){
        return cells;
    }
}
