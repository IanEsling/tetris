package shapes;

/**
 */
public abstract class Shape {

    Integer[][] cells;

    Shape(){
        cells = new Integer[4][4];
        for (int x = 0;x<4;x++){
            for (int y = 0;y<4;y++){
                cells[x][y]=0;
            }
        }
        setShapeCells();
    }

    abstract void setShapeCells();

    public Integer[][] getCells(){
        return cells;
    }
}
