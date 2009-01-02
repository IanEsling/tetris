package board;

import shapes.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Board {
    int rows, columns;
    List<Cell> cells;
    public static int START_ROW = 0;
    public static int END_ROW = 3;
    public static int START_COL = 3;
    public static int END_COL = 6;
    MovingShape movingShape;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new ArrayList<Cell>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells.add(new Cell(row, col));
            }
        }
    }

    public void moveShapeToRight() {
        movingShape.moveToRight();
    }

    public void moveShapeToLeft() {
        movingShape.moveToLeft();
    }

    public void addNewShape(Shape shape) {
        int[][] shapeCells = shape.getCells();
        int shapeRow = 0;
        movingShape = new MovingShape();
        for (int row = START_ROW; row <= END_ROW; row++) {
            int shapeCol = 0;
            for (int col = START_COL; col <= END_COL; col++) {
                if (shapeCells[shapeRow][shapeCol] == 1) {
                    movingShape.isPresentInCell(cellAt(shapeRow, shapeCol + START_COL));
                }
                shapeCol++;
            }
            shapeRow++;
        }
    }

    MovingShape getMovingShape() {
        return movingShape;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getCell(int row, int column) {
        return cellAt(row, column);
    }

    private Cell cellAt(int row, int column) {
        return cells.get(cells.indexOf(new Cell(row, column)));
    }

    public void tick() {
        if (movingShape != null) {
            movingShape.move(1, 0);
            if (movingShapeCannotMoveDownAnymore()) movingShape = null;
        }
    }

    private boolean movingShapeCannotMoveDownAnymore() {
        for (Cell cell : movingShape.shapeCells) {
            if (cell.row == rows - 1) return true;
            if ((cellAt(cell.row + 1, cell.column).isPopulated())//cell below has something in it
                    &&
                    //it's not because it's one of it's own cells
                    (!movingShape.shapeCells.contains(new Cell(cell.row + 1, cell.column)))
                    )
                return true;
        }

        return false;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    class MovingShape {
        List<Cell> shapeCells;

        MovingShape() {
            shapeCells = new ArrayList<Cell>();
        }

        void isPresentInCell(Cell cell) {
            shapeCells.add(cell);
            cell.setPopulated(true);
        }

        void moveToRight() {
            move(0, 1);
        }

        void moveToLeft() {
            move(0, -1);
        }

        boolean canMove(int columns) {
            for (Cell cell : shapeCells) {
                if (cell.column == getColumns() - 1 && columns > 0)//cell is on right edge and attempt to move right
                    return false;
                if (cell.column == 0 && columns < 0)//cell is on left edge and attempt to move left
                    return false;
            }
            return true;
        }

        void move(int rows, int columns) {

            if (!canMove(columns)) columns = 0;//trying to move left or right when on the edge

            setCurrentCellsToUnpopulated();

            shapeCells = getBoardCellsForNewPosition(getNewPositions(rows, columns));
        }

        private List<Cell> getBoardCellsForNewPosition(List<Cell> newCells) {
            List<Cell> newShapeCells = new ArrayList<Cell>();
            for (Cell cell : newCells) {
                Cell newCell = cells.get(cells.indexOf(cell));
                newCell.setPopulated(true);
                newShapeCells.add(newCell);
            }
            return newShapeCells;
        }

        private void setCurrentCellsToUnpopulated() {
            for (Cell cell : shapeCells) {
                cell.setPopulated(false);
            }
        }

        private List<Cell> getNewPositions(int rows, int columns) {
            List<Cell> newCells = new ArrayList<Cell>();
            for (Cell cell : shapeCells) {
                newCells.add(new Cell(cell.row + rows, cell.column + columns));
            }
            return newCells;
        }
    }
}
