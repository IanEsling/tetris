package board;

import shapes.RandomShapeGenerator;
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
    protected MovingShape movingShape;

    enum Rotation {
        Clockwise,
        AntiClockwise
    }

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
        movingShape = new MovingShape(shape);
        for (int row = START_ROW; row <= END_ROW; row++) {
            int shapeCol = 0;
            for (int col = START_COL; col <= END_COL; col++) {
                if (shapeCells[shapeRow][shapeCol] == 1) {
                    movingShape.shapeIsInThisCell(cellAt(row, col));
                }
                shapeCol++;
            }
            shapeRow++;
        }
    }

    public void tick() {
        if (movingShape != null) {
            movingShape.move(1, 0);
            if (movingShapeCannotMoveDownAnymore()) addNewShapeAtRandom();
        }
    }

    protected void addNewShapeAtRandom() {
        addNewShape(RandomShapeGenerator.getNewShapeAtRandom());
    }

    private boolean movingShapeCannotMoveDownAnymore() {
        for (Cell cell : movingShape.listOfCells()) {
            if (cell.row == rows - 1) return true;
            if ((cellAt(cell.row + 1, cell.column).isPopulated())//cell below has something in it
                    &&
                    //and it's not because it's one of its own cells
                    (!movingShape.listOfCells().contains(new Cell(cell.row + 1, cell.column)))
                    )
                return true;
        }
        return false;
    }

    public void rotateShapeClockwise() {
        movingShape.rotate(Rotation.Clockwise);
    }

    public void rotateShapeAntiClockwise() {
        movingShape.rotate(Rotation.AntiClockwise);
    }

    class MovingShape {

        Cell[][] shapeCells;
        Shape shape;

        MovingShape(Shape shape) {
            this.shape = shape;
            shapeCells = new Cell[4][4];
        }

        public Shape getShape() {
            return shape;
        }

        void shapeIsInThisCell(Cell cell) {
            shapeCells[cell.row][cell.column - START_COL] = cell;
            cell.setPopulated(true);
        }

        void moveToRight() {
            move(0, 1);
        }

        void moveToLeft() {
            move(0, -1);
        }

        boolean canMove(int columns) {
            for (Cell cell : listOfCells()) {
                //cell is on right edge and attempt to move right
                if (cell.column == getColumns() - 1 && columns > 0)
                    return false;

                //cell is on left edge and attempt to move left
                if (cell.column == 0 && columns < 0)
                    return false;
            }
            return true;
        }

        void move(int rows, int columns) {

            if (!canMove(columns)) columns = 0;//trying to move left or right when on the edge

            setCurrentCellsToUnpopulated();

            setCellsForNewPosition(rows, columns);
        }

        private void setCellsForNewPosition(int rows, int columns) {
            for (int row = 0; row < shapeCells.length; row++) {
                for (int column = 0; column < shapeCells[0].length; column++) {
                    if (shapeCells[row][column] != null) {
                        Cell oldCell = shapeCells[row][column];
                        Cell newCell = cellAt(oldCell.row + rows, oldCell.column + columns);
                        newCell.setPopulated(true);
                        shapeCells[row][column] = newCell;
                    }
                }
            }
        }

        private void setCurrentCellsToUnpopulated() {
            for (Cell cell : listOfCells()) {
                cell.setPopulated(false);
            }
        }

        public List<Cell> listOfCells() {
            List<Cell> cellList = new ArrayList<Cell>();
            for (Cell[] cells : shapeCells) {
                for (Cell cell : cells) {
                    if (cell != null)
                        cellList.add(cell);
                }
            }
            return cellList;
        }

        public void rotate(Rotation rotation) {
            Cell[][] newShapeCells = new Cell[4][4];
            rotateCells(rotation, newShapeCells);
            setAllCells(shapeCells, false);
            setAllCells(newShapeCells, true);
            shapeCells = newShapeCells;
        }

        private void rotateCells(Rotation rotation, Cell[][] newShapeCells) {
            int rowCount = 0;
            for (Cell[] row : shapeCells) {
                int columnCount = 0;
                for (int column = shapeCells[0].length - 1; column >= 0; column--) {
                    if (shapeCells[rowCount][column] != null) {
                        rotate(newShapeCells, rowCount, columnCount, column, rotation);
                    }
                    columnCount++;
                }
                rowCount++;
            }
        }

        private void rotate(Cell[][] newShapeCells, int rowCount, int columnCount, int column, Rotation rotation) {
            Cell oldCell = shapeCells[rowCount][column];

            if (rotation == Rotation.Clockwise) {
                Cell newCell = cellAt(oldCell.row + column - rowCount, oldCell.column + columnCount - rowCount);
                newShapeCells[shapeCells.length - 1 - columnCount][shapeCells.length - 1 - rowCount] = newCell;
            }

            if (rotation == Rotation.AntiClockwise) {
                Cell newCell = cellAt(oldCell.row - (rowCount - columnCount), oldCell.column - (column - rowCount));
                newShapeCells[columnCount][rowCount] = newCell;
            }
        }
    }

    private void setAllCells(Cell[][] cells, boolean populated) {
        for (Cell[] cell : cells) {
            for (int col = 0; col < cells[0].length; col++) {
                if (cell[col] != null)
                    cell[col].setPopulated(populated);
            }
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

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}
