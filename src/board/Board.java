package board;

import static board.Movement.*;
import static board.Rotation.AntiClockwise;
import static board.Rotation.Clockwise;
import static shapes.RandomShapeGenerator.getNewShapeAtRandom;
import shapes.Shape;
import util.ArrayCellCallback;
import static util.Util.eachCell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
public class Board {
    private final int rows;
    private final int columns;
    private Cell[][] boardCells;
    public ShapeCellBoardMapper mapper;
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

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        rotators = new RotatorFactory(this);
        movementValidator = new MovingShapeMovementValidator(this);
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
                if (getCell(row, col).isPopulated()) return false;
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
                Cell cellAbove = getCell(row - 1, cell.column);
                if (movingShapeCells().contains(cellAbove)) {
                    cell.setPopulated(false);
                    cell.setColour(Board.DEFAULT_EMPTY_COLOUR);
                } else {
                    cell.setPopulated(cellAbove.isPopulated());
                    cell.setColour(cellAbove.getColour());
                }
            }
        }
    }

    private List<Cell> movingShapeCells() {
        return mapper.shapeCellsAsList();
    }

    private List<Cell> cellsInRow(int row) {
        List<Cell> cellsInRow = new ArrayList<Cell>();
        for (Cell cell : boardCells[row]) {
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

    public class ShapeCellBoardMapper {

        Shape shape;
        Cell[][] shapeCells;
        int zeroIndexRow, zeroIndexColumn;//cell at (0,0) of the shape matrix

        ShapeCellBoardMapper(MovingShape movingShape) {
            this.shape = movingShape.getShape();
            shapeCells = boardCellsForNewShape(Board.START_ROW, Board.START_COL);
        }

        private Cell[][] boardCellsForNewShape(final int startRow, final int startCol) {
            final Cell[][] newShapeCells = new Cell[4][4];
            zeroIndexRow = startRow;
            zeroIndexColumn = startCol;

            eachCell(shape.getCells(), new ArrayCellCallback() {
                @Override
                public void cell(int row, int col) {
                    newShapeCells[row][col] =
                            shape.getCells()[row][col] == 0 ?
                                    null :
                                    getCell(startRow + row, startCol + col).setPopulated(shape);
                }
            });
            return newShapeCells;
        }

        private void moveShapeCells(final Movement movement) {
            setAllCells(shapeCells, false);
            zeroIndexColumn += movement.getColumnChange();
            zeroIndexRow += movement.getRowChange();

            eachCell(shapeCells, new ArrayCellCallback() {
                @Override
                public void cell(int row, int col) {
                    shapeCells[row][col] = cellInNewPosition(movement, shapeCells[row][col]);
                }
            });
        }

        private Cell cellInNewPosition(Movement movement, Cell cell) {
            if (cell != null) {
                return getCell(cell.row + movement.getRowChange(),
                        cell.column + movement.getColumnChange()).setPopulated(shape);
            }
            return cell;
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

        public int startingBoardColumn() {
            return zeroIndexColumn;
        }

        public int startingBoardRow() {
            return zeroIndexRow;
        }

        private Cell[][] mapNewShapeToBoardCells() {
            return boardCellsForNewShape(startingBoardRow(), startingBoardColumn());
        }

        private void setNewShapeCells(Cell[][] newShapeCells) {
            setAllCells(shapeCells, false);
            setAllCells(newShapeCells, true);
            shapeCells = newShapeCells;
        }


        private void setAllCells(final Cell[][] cells, final boolean populated) {
            eachCell(cells, new ArrayCellCallback() {
                @Override
                public void cell(int row, int col) {
                    if (cells[row][col] != null) {
                        cells[row][col].setPopulated(populated, shape);
                    }
                }
            });
        }
    }

    public class MovingShape {

        final Shape shape;

        public MovingShape(Shape shape) {
            this.shape = shape;
            mapper = new ShapeCellBoardMapper(this);
        }

        void moveToRight() {
            move(Right);
        }

        void moveToLeft() {
            move(Left);
        }

        synchronized void move(Movement movement) {
            if (canMove(movement)) mapper.moveShapeCells(movement);
        }

        boolean canMove(Movement movement) {
            return movementValidator.canMove(movement);
        }

        public void rotate(Rotation rotation) {
            mapper.setNewShapeCells(rotateCells(rotation));
        }

        private Cell[][] rotateCells(Rotation rotation) {
            rotators.get(rotation).rotate();

            return mapper.mapNewShapeToBoardCells();
        }

        public Shape getShape() {
            return shape;
        }
    }

    public MovingShape getMovingShape() {
        return movingShape;
    }

    public List<Cell> getBoardCells() {
        List<Cell> cellList = new ArrayList<Cell>();
        for (Cell[] row : boardCells) {
            cellList.addAll(Arrays.asList(row));
        }

        return cellList;
    }

    public Cell getCell(int row, int column) {
        return boardCells[row][column];
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    Board getBoard() {
        return this;
    }

    private void createBoardCells(int rows, int columns) {
        boardCells = new Cell[rows][columns];
        eachCell(boardCells, new ArrayCellCallback() {
            @Override
            public void cell(int row, int col) {
                boardCells[row][col] = (new Cell(row, col));
            }
        });
    }
}
