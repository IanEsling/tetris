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
            movingShape.move(1, 0);
            if (movingShapeCannotMoveDownAnymore()) movingShape = null;
        }
    }

    private boolean movingShapeCannotMoveDownAnymore() {
        List<Cell> lowestCells = movingShape.cellsInLowestRow();
        for (Cell cell : lowestCells) {
            if (cell.row == rows - 1) return true;
            if (cells.get(cells.indexOf(new Cell(cell.row + 1, cell.column))).isPopulated()) return true;
        }

        return false;
    }

    class MovingShape {
        List<Cell> shapeCells, boardCells;
        Board board;

        MovingShape(Board board) {
            shapeCells = new ArrayList<Cell>();
            boardCells = board.getCells();
            this.board = board;

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

        boolean canMove(int columns){
            for (Cell cell : shapeCells){
                if (cell.column==board.columns-1 && columns > 0)//cell is on right edge and attempt to move right
                    return false;
                if (cell.column == 0 && columns < 0)//cell is on left edge and attempt to move left
                    return false;
            }
            return true;
        }

        void move(int rows, int columns) {
            List<Cell> newShapeCells = new ArrayList<Cell>();
            List<Cell> newCells = new ArrayList<Cell>();

            if (!canMove(columns)) columns = 0;//trying to move left or right when on the edge

            getCellsForNewPosition(rows, columns, newCells);

            setCurrentCellsToUnpopulated();

            getBoardCellsForNewPosition(newShapeCells, newCells);

            shapeCells = newShapeCells;
        }

        private void getBoardCellsForNewPosition(List<Cell> newShapeCells, List<Cell> newCells) {
            for (Cell cell : newCells) {
                Cell newCell = boardCells.get(boardCells.indexOf(cell));
                newCell.setPopulated(true);
                newShapeCells.add(newCell);
            }
        }

        private void setCurrentCellsToUnpopulated() {
            for (Cell cell : shapeCells) {
                cell.setPopulated(false);
            }
        }

        private void getCellsForNewPosition(int rows, int columns, List<Cell> newCells) {
            for (Cell cell : shapeCells) {
                if (cell.row == board.rows - 1) rows = 0;//cell is at bottom
                newCells.add(new Cell(cell.row + rows, cell.column + columns));
            }
        }

        List<Cell> cellsInLowestRow() {
            return cellsInRow(getLowestRowNumberShapeHasCellIn());
        }

        private List<Cell> cellsInRow(int row) {
            List<Cell> lowestCells = new ArrayList<Cell>();
            for (Cell cell : shapeCells) {
                if (cell.row == row) lowestCells.add(cell);
            }
            return lowestCells;
        }

        private int getLowestRowNumberShapeHasCellIn() {
            int row = 0;
            for (Cell cell : shapeCells) {
                if (cell.row > row) row = cell.row;
            }
            return row;
        }
    }
}
