package board;

import static board.Board.Movement.*;
import static board.Board.Rotation.AntiClockwise;
import static board.Board.Rotation.Clockwise;
import shapes.AntiClockwiseBoardShapeRotator;
import shapes.BoardShapeRotator;
import shapes.ClockwiseBoardShapeRotator;
import static shapes.RandomShapeGenerator.getNewShapeAtRandom;
import shapes.Shape;
import util.ArrayCellCallback;
import static util.Util.eachCell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class Board {
    private final int rows;
    private final int columns;
    private List<Cell> cells;
    public static final int START_ROW = 0;
    private static final int END_ROW = 3;
    public static final int START_COL = 3;
    private static final int END_COL = 6;
    public static final Color DEFAULT_EMPTY_COLOUR = Color.gray;
    protected MovingShape movingShape;
    private boolean gameOver = false;
    private final RotatorFactory rotators;
    private MovementValidator movementValidator;

    public boolean gameOver() {
        return gameOver;
    }

    static class RotatorFactory {
        final BoardShapeRotator clockwiseBoardShapeRotator;
        final BoardShapeRotator antiClockwiseBoardShapeRotator;

        RotatorFactory(Board board) {
            clockwiseBoardShapeRotator = new ClockwiseBoardShapeRotator(board);
            antiClockwiseBoardShapeRotator = new AntiClockwiseBoardShapeRotator(board);
        }

        BoardShapeRotator get(Rotation rotation) {
            return rotation == Rotation.AntiClockwise ? antiClockwiseBoardShapeRotator : clockwiseBoardShapeRotator;
        }
    }

    enum Rotation {
        Clockwise,
        AntiClockwise
    }

    public enum Movement {
        Left {
            @Override
            int getRowChange() {
                return 0;
            }
            @Override
            int getColumnChange() {
                return -1;
            }
            @Override
            boolean allowMove(Cell cell, int boardColumns, int boardRows) {
                return cell.column != 0;
            }},
        Right {
            @Override
            int getRowChange() {
                return 0;
            }
            @Override
            int getColumnChange() {
                return 1;
            }
            @Override
            boolean allowMove(Cell cell, int boardColumns, int boardRows) {
                return cell.column != boardColumns - 1;
            }},
        Down {
            @Override
            int getRowChange() {
                return 1;
            }
            @Override
            int getColumnChange() {
                return 0;
            }
            @Override
            boolean allowMove(Cell cell, int boardColumns, int boardRows) {
                return cell.row != boardRows - 1;
            }};

        abstract int getRowChange();

        abstract int getColumnChange();

        abstract boolean allowMove(Cell cell, int boardColumns, int boardRows);
    }

    interface MovementValidator {
        boolean canMove(Movement movement);
    }

    class MovementValidatorImpl implements MovementValidator {
        MovingShape movingShape;

        MovementValidatorImpl(MovingShape movingShape) {
            this.movingShape = movingShape;
        }

        List<Cell> shapeCells() {
            return movingShape.shapeCellsAsList();
        }

        @Override
        public boolean canMove(Movement movement) {
            for (Cell cell : shapeCells()) {
                if (newCellIsAlreadyPopulated(movement, cell))
                    return false;

                if (!movement.allowMove(cell, getColumns(), getRows())) return false;
            }
            return true;
        }

        private boolean newCellIsAlreadyPopulated(Movement movement, Cell cell) {
            return cellAt(newRow(movement, cell), newCol(movement, cell)).isPopulated() &&
                    (!shapeCells().contains(new Cell(newRow(movement, cell), newCol(movement, cell))));
        }

        private int newCol(Movement movement, Cell cell) {
            return cell.column + movement.getColumnChange() > getColumns() - 1 ? getColumns() - 1 :
                    (cell.column + movement.getColumnChange() < 0 ? 0 : cell.column + movement.getColumnChange());
        }

        private int newRow(Movement movement, Cell cell) {
            return cell.row + movement.getRowChange() > getRows() - 1 ? getRows() - 1 : cell.row + movement.getRowChange();
        }
    }

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        rotators = new RotatorFactory(this);
        createBoardCells(rows, columns);
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
            //don't check after moving, so player can still move sideways before next tick
            if (!movingShapeCanMoveDown()) {
                addNewShapeAtRandom();
                removeCompletedRows();
            }
            movingShape.move(Down);
        }
    }

    private void removeCompletedRows() {
        for (int row = 0; row < rows; row++) {
            boolean complete = true;
            for (Cell cell : cellsInRow(row)) {
                if (!cell.isPopulated()) complete = false;
            }
            if (complete) {
                removeRow(row);
            }
        }
    }

    private void removeRow(int removeMe) {
        for (int row = removeMe; row > 0; row--) {
            for (Cell cell : cellsInRow(row)) {
                Cell cellAbove = cellAt(row - 1, cell.column);
                if (getMovingShape().shapeCellsAsList().contains(cellAbove)) {
                    cell.setPopulated(false);
                    cell.setColour(Board.DEFAULT_EMPTY_COLOUR);
                } else {
                    cell.setPopulated(cellAbove.isPopulated());
                    cell.setColour(cellAbove.getColour());
                }
            }
        }
    }

    private List<Cell> cellsInRow(int row) {
        List<Cell> cellsInRow = new ArrayList<Cell>();
        for (Cell cell : cells) {
            if (cell.row == row) cellsInRow.add(cell);
        }
        return cellsInRow;
    }

    public void addNewShapeAtRandom() {
        addNewShape(getNewShapeAtRandom());
    }

    public boolean movingShapeCanMoveDown() {
        return movingShape.canMove(Down);
    }

    public void rotateShapeClockwise() {
        movingShape.rotate(Clockwise);
    }

    public void rotateShapeAntiClockwise() {
        movingShape.rotate(AntiClockwise);
    }

    public class MovingShape {

        Cell[][] shapeCells;
        final Shape shape;

        public MovingShape(Shape shape) {
            this.shape = shape;
            shapeCells = boardCellsForNewShape(Board.START_ROW, Board.START_COL);
            movementValidator = new MovementValidatorImpl(this);
        }

        void moveToRight() {
            move(Right);
        }

        void moveToLeft() {
            move(Left);
        }

        boolean canMove(Movement movement) {
            return movementValidator.canMove(movement);
        }

        synchronized void move(Movement movement) {
            if (canMove(movement)) setCellsForNewPosition(movement);

        }

        private void setCellsForNewPosition(final Movement movement) {
            setCurrentCellsToUnpopulated();

            eachCell(shapeCells, new ArrayCellCallback() {
                @Override
                public void cell(int row, int col) {
                    shapeCells[row][col] = setNewCell(movement, shapeCells[row][col]);
                }
            });
        }

        private Cell setNewCell(Movement movement, Cell cell) {
            if (cell != null) {
                return cellAt(cell.row + movement.getRowChange(), cell.column + movement.getColumnChange()).setPopulated(true, getShape());
            }
            return cell;
        }

        private void setCurrentCellsToUnpopulated() {

            for (Cell cell : shapeCellsAsList()) {
                cell.setPopulated(false);
            }
        }

        public List<Cell> shapeCellsAsList() {
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
            rotators.get(rotation).rotate();

            return mapNewShapeToBoardCells();
        }

        private Cell[][] mapNewShapeToBoardCells() {
            return boardCellsForNewShape(startingBoardRow(), startingBoardColumn());
        }

        private Cell[][] boardCellsForNewShape(final int startRow, final int startCol) {
            final Cell[][] newShapeCells = new Cell[4][4];

            eachCell(getShape().getCells(), new ArrayCellCallback() {
                @Override
                public void cell(int row, int col) {
                    newShapeCells[row][col] =
                            getShape().getCells()[row][col] == 0 ?
                                    null :
                                    cellAt(startRow + row, startCol + col).setPopulated(true, getShape());
                }
            });
            return newShapeCells;
        }

        //board column at (0,0) of this matrix
        public int startingBoardColumn() {
            for (Cell[] row : shapeCells) {
                for (int col = 0; col < shapeCells[0].length; col++) {
                    if (row[col] != null) {
                        return row[col].column - col;
                    }
                }
            }
            //fail fast, fail big
            throw new RuntimeException();
        }

        //board row at (0,0) of this matrix
        public int startingBoardRow() {
            for (int row = 0; row < shapeCells.length; row++) {
                for (int col = 0; col < shapeCells[0].length; col++) {
                    if (shapeCells[row][col] != null) {
                        return shapeCells[row][col].row - row;
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

    private void setAllCells(final Cell[][] cells, final boolean populated) {
        eachCell(cells, new ArrayCellCallback() {
            @Override
            public void cell(int row, int col) {
                if (cells[row][col] != null) {
                    cells[row][col].setPopulated(populated, getMovingShape().getShape());
                }
            }
        });
    }

    public MovingShape getMovingShape() {
        return movingShape;
    }

    public List<Cell> getBoardCells() {
        return cells;
    }

    public Cell getCell(int row, int column) {
        return cellAt(row, column);
    }

    Cell cellAt(int row, int column) {
        if (cells.indexOf(new Cell(row, column)) < 0) {
            throw new RuntimeException("cell not found at " + row + "," + column);
        }
        return cells.get(cells.indexOf(new Cell(row, column)));
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    private void createBoardCells(int rows, int columns) {
        cells = new ArrayList<Cell>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells.add(new Cell(row, col));
            }
        }
    }
}
