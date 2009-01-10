package board;

import static board.Board.Rotation.AntiClockwise;
import static board.Board.Rotation.Clockwise;
import static shapes.RandomShapeGenerator.getNewShapeAtRandom;
import shapes.Shape;

import java.awt.*;
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
    public static Color DEFAULT_EMPTY_COLOUR = Color.gray;
    protected MovingShape movingShape;
    boolean gameOver = false;

    public boolean gameOver() {
        return gameOver;
    }

    enum Rotation {
        Clockwise {void rotate(Shape shape) {
            shape.rotateClockwise();
        }},
        AntiClockwise {void rotate(Shape shape) {
            shape.rotateAntiClockwise();
        }};

        abstract void rotate(Shape shape);
    }

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        setCells(rows, columns);
    }

    public void moveShapeToRight() {
        movingShape.moveToRight();
    }

    public void moveShapeToLeft() {
        movingShape.moveToLeft();
    }

    public void addNewShape(Shape shape) {
        if (canAddNewShape()) movingShape = new MovingShape(shape);
        else
            gameOver = true;
    }

    private boolean canAddNewShape() {
        for (int row = START_ROW; row <= END_ROW; row++) {
            for (int col = START_COL; col <= END_COL; col++) {
                if (cellAt(row, col).isPopulated()) return false;
            }
        }
        return true;
    }

    public void tick() {
        if (movingShape != null) {
            movingShape.move(1, 0);//move down one row
            if (movingShapeCannotMoveDownAnymore()) addNewShapeAtRandom();
        }
    }

    public void addNewShapeAtRandom() {
        addNewShape(getNewShapeAtRandom());
    }

    private boolean movingShapeCannotMoveDownAnymore() {
        for (Cell cell : movingShape.listOfCells()) {
            if (cellIsOnBottomRow(cell) || somethingBelowShape(cell))
                return true;
        }
        return false;
    }

    public void rotateShapeClockwise() {
        movingShape.rotate(Clockwise);
    }

    public void rotateShapeAntiClockwise() {
        movingShape.rotate(AntiClockwise);
    }

    class MovingShape {

        Cell[][] shapeCells;
        Shape shape;

        MovingShape(Shape shape) {
            this.shape = shape;
            shapeCells = boardCellsForNewShape(Board.START_ROW, Board.START_COL);
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

                //trying to move sideways into a populated cell
                if (columns != 0 && cellAt(cell.row, cell.column + columns).isPopulated() &&
                        (!movingShape.listOfCells().contains(new Cell(cell.row, cell.column + columns))))
                    return false;
            }
            return true;
        }

        void move(int rows, int columns) {
            if (!canMove(columns)) columns = 0;//trying to move left or right when on the edge

            if (rows != 0 || columns != 0)
                setCellsForNewPosition(rows, columns);
        }

        private void setCellsForNewPosition(int rows, int columns) {
            setCurrentCellsToUnpopulated();

            for (int row = 0; row < shapeCells.length; row++) {
                for (int column = 0; column < shapeCells[0].length; column++) {
                    shapeCells[row][column] = setNewCell(rows, columns, shapeCells[row][column]);
                }
            }
        }

        private Cell setNewCell(int rows, int columns, Cell cell) {
            if (cell != null) {
                return cellAt(cell.row + rows, cell.column + columns).setPopulated(true, getShape());
            }
            return cell;
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
                    if (cell != null) cellList.add(cell);
                }
            }
            return cellList;
        }

        public void rotate(Rotation rotation) {
            setNewShapeCells(rotateCells(rotation));
        }

        private void setNewShapeCells(Cell[][] newShapeCells) {
            setAllCells(shapeCells, false);
            setAllCells(newShapeCells, true);
            shapeCells = newShapeCells;
        }

        private Cell[][] rotateCells(Rotation rotation) {
            rotation.rotate(getShape());

            return mapNewShapeToBoardCells();
        }

        private Cell[][] mapNewShapeToBoardCells() {
            return boardCellsForNewShape(startingBoardRow(shapeCells), startingBoardColumn(shapeCells));
        }

        private Cell[][] boardCellsForNewShape(int startRow, int startCol) {
            Cell[][] newShapeCells = new Cell[4][4];
            for (int row = 0; row < getShape().getCells().length; row++) {
                for (int col = 0; col < getShape().getCells()[0].length; col++) {
                    newShapeCells[row][col] =
                            getShape().getCells()[row][col] == 0 ?
                                    null :
                                    cellAt(startRow + row, startCol + col).setPopulated(true, getShape());
                }
            }
            return newShapeCells;
        }

        //board column at (0,0) of this matrix
        private int startingBoardColumn(Cell[][] matrix) {
            for (Cell[] row : matrix) {
                for (int col = 0; col < matrix[0].length; col++) {
                    if (row[col] != null) {
                        return row[col].column - col;
                    }
                }
            }
            //fail fast, fail big
            throw new RuntimeException();
        }

        //board row at (0,0) of this matrix
        private int startingBoardRow(Cell[][] matrix) {
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[0].length; col++) {
                    if (matrix[row][col] != null) {
                        return matrix[row][col].row - row;
                    }
                }
            }
            //fail fast, fail big
            throw new RuntimeException();
        }

        public Shape getShape() {
            return shape;
        }

    }

    private void setAllCells(Cell[][] cells, boolean populated) {
        for (Cell[] cell : cells) {
            for (int col = 0; col < cells[0].length; col++) {
                if (cell[col] != null) {
                    cell[col].setPopulated(populated, getMovingShape().getShape());
                }
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

    private void setCells(int rows, int columns) {
        cells = new ArrayList<Cell>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells.add(new Cell(row, col));
            }
        }
    }

    private boolean cellIsOnBottomRow(Cell cell) {
        return cell.row == rows - 1;
    }

    private boolean somethingBelowShape(Cell cell) {
        return (cellAt(cell.row + 1, cell.column).isPopulated())//cell below has something in it
                &&
                //and it's not because it's one of its own cells
                (!movingShape.listOfCells().contains(new Cell(cell.row + 1, cell.column)));
    }
}
