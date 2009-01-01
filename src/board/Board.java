package board;

import shapes.Shape;

/**
 */
public class Board {
    int[][] cells;
    public static int START_ROW = 0;
    public static int END_ROW = 3;
    public static int START_COL = 3;
    public static int END_COL = 6;

    public Board(int rows, int columns) {
        cells = new int[rows][columns];
    }

    void addNewShape(Shape shape) {
        int[][] shapeCells = shape.getCells();
        int shapeRow = 0;
        for (int row = START_ROW; row <= END_ROW; row++) {
            int shapeCol = 0;
            for (int col = START_COL; col <= END_COL; col++) {
                cells[row][col] = shapeCells[shapeRow][shapeCol];
                shapeCol++;
            }
            shapeRow++;
        }
    }

    int[][] getCells() {
        return cells;
    }

    void tick() {
        for (int row = cells.length - 1; row >= 0; row--) {
            for (int col = cells[0].length - 1; col >= 0; col--) {
                if (row == 0) cells[row][col] = 0;//top row, always will empty on tick
                else if (row == cells.length - 1 && cells[row][col] == 1)
                    cells[row][col] = cells[row][col];//bottom row populated, stay populated
                else if (cells[row][col]==0){//cell is empty
                    cells[row][col] = cells[row - 1][col];//move contents above into it
                    cells[row-1][col]=0;//empty cell above
                }
            }
        }
    }
}
