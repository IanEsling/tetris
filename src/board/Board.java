package board;

import shapes.Shape;

/**
 */
public class Board {
    Integer[][] cells;
    public static int START_ROW = 0;
    public static int END_ROW = 3;
    public static int START_COL = 4;
    public static int END_COL = 7;

    public Board(int x, int y) {
        cells = new Integer[x][y];
        for (int row = 0; row < x; row++) {
            for (int col = 0; col < y; col++) {
                cells[row][col] = 0;
            }
        }
    }

    void addNewShape(Shape shape) {
        Integer[][] shapeCells = shape.getCells();
        int shapeRow=0;
        for (int row = START_ROW;row<=END_ROW;row++)
        {
            int shapeCol = 0;
            for (int col = START_COL;col<=END_COL;col++)
            {
                cells[row][col] = shapeCells[shapeRow][shapeCol];
                shapeCol++;
            }
            shapeRow++;
        }
    }

    Integer[][] getCells() {
        return cells;
    }
}
