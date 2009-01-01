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

    public void addNewShape(Shape shape) {
        int[][] shapeCells = shape.getCells();
        int shapeRow = 0;
        movingShape = new MovingShape(this);
        for (int row = START_ROW; row <= END_ROW; row++) {
            int shapeCol = 0;
            for (int col = START_COL; col <= END_COL; col++) {
                if (shapeCells[shapeRow][shapeCol] == 1) {
                    movingShape.isPresentInCell(cells.get(cells.indexOf(new Cell(shapeRow, shapeCol + START_COL))));
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
        return cells.get(cells.indexOf(new Cell(row, column)));
    }

    public void tick() {
        if (movingShape != null) {
            movingShape.moveDownOneRow();
            if (movingShapeCannotMoveAnymore()) movingShape = null;
        }

    }

    private boolean movingShapeCannotMoveAnymore() {
        List<Cell> lowestCells = movingShape.cellsInLowestRow();
        for (Cell cell : lowestCells) {
            if (cell.row == rows - 1) return true;
            if (cells.get(cells.indexOf(new Cell(cell.row + 1, cell.column))).isPopulated()) return true;
        }

        return false;
    }

    class MovingShape {
        List<Cell> shapeCells, boardCells;

        MovingShape(Board board) {
            shapeCells = new ArrayList<Cell>();
            boardCells = board.getCells();

        }

        void isPresentInCell(Cell cell) {
            shapeCells.add(cell);
            cell.setPopulated(true);
        }

        void moveDownOneRow() {
            List<Cell> newShapeCells = new ArrayList<Cell>();
            List<Cell> newCells = new ArrayList<Cell>();
            for (Cell cell : shapeCells) {
                newCells.add(new Cell(cell.row + 1, cell.column));
            }

            for (Cell cell : shapeCells) {
                cell.setPopulated(false);
            }

            for (Cell cell : newCells) {
                Cell newCell = boardCells.get(boardCells.indexOf(cell));
                newCell.setPopulated(true);
                newShapeCells.add(newCell);
            }

            shapeCells = newShapeCells;
        }

        List<Cell> cellsInLowestRow() {
            int row = 0;
            for (Cell cell : shapeCells) {
                if (cell.row > row) row = cell.row;
            }
            List<Cell> lowestCells = new ArrayList<Cell>();
            for (Cell cell : shapeCells) {
                if (cell.row == row) lowestCells.add(cell);
            }
            return lowestCells;
        }
    }

    public class Cell {
        public int row;
        public int column;
        boolean populated;

        Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        void setPopulated(boolean populated) {
            this.populated = populated;
        }

        public boolean isPopulated() {
            return populated;
        }

        @SuppressWarnings({"RedundantIfStatement"})
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cell cell = (Cell) o;

            if (column != cell.column) return false;
            if (row != cell.row) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + column;
            return result;
        }

        @Override
        public String toString() {
            return "[" + row + "," + column + ": " + (isPopulated() ? "populated" : "empty") + "]";
        }
    }
}
