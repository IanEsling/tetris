package board;

import static board.Movement.*;
import static board.Rotation.AntiClockwise;
import static board.Rotation.Clockwise;
import shapes.RandomShapeGenerator;
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
    private final int rows, columns;
    private Cell[][] boardCells;
    public ShapeLayoutToBoardCellMapper mapper;
    public static final int START_ROW = 0;
    private static final int END_ROW = 3;
    public static final int START_COL = 3;
    private static final int END_COL = 6;
    public static final Color DEFAULT_EMPTY_COLOUR = Color.gray;
    private boolean gameOver = false;
    private List<TickListener> tickListeners;
    private Shape nextShape;
    private RandomShapeGenerator randomShapeGenerator;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        mapper = new ShapeLayoutToBoardCellMapper(this);
        tickListeners = new ArrayList<TickListener>();
        randomShapeGenerator = new RandomShapeGenerator();
        createBoardCells(rows, columns);
        setNextShape();
    }

    public void addTickListener(TickListener listener){
        tickListeners.add(listener);
    }

    public void moveShapeToRight() {
        moveShape(Right);
    }

    public void moveShapeToLeft() {
        moveShape(Left);
    }

    synchronized void moveShape(Movement movement) {
        mapper.moveShapeCells(movement);
    }

    public void addNewShape(Shape shape) {
        if (canAddNewShape()) mapper.setNewShape(shape);
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
        if (!movingShapeCanMoveDown()) {
            addNewShapeAtRandom();
            removeCompletedRows();
        }
        moveShape(Down);
        for (TickListener listener : tickListeners){
            listener.boardHasTicked();
        }
    }

    private void removeCompletedRows() {
        for (int row = 0; row < rows; row++) {
            boolean rowComplete = true;
            for (Cell cell : cellsInRow(row)) {
                if (!cell.isPopulated()) rowComplete = false;
            }
            if (rowComplete) {
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

    public List<Cell> movingShapeCells() {
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
        addNewShape(nextShape);
        setNextShape();
    }

    private void setNextShape() {
        nextShape = randomShapeGenerator.getNewShapeAtRandom();
    }

    public boolean movingShapeCanMoveDown() {
        return mapper.valid(Down);
    }

    public void rotateShapeClockwise() {
        rotate(Clockwise);
    }

    public void rotateShapeAntiClockwise() {
        rotate(AntiClockwise);
    }

    public void rotate(Rotation rotation) {
        mapper.rotateShape(rotation);
    }

    public Shape getNextShape() {
        return nextShape;
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

    private void createBoardCells(int rows, int columns) {
        boardCells = new Cell[rows][columns];
        eachCell(boardCells, new ArrayCellCallback() {
            @Override
            public void cell(int row, int col) {
                boardCells[row][col] = (new Cell(row, col));
            }
        });
    }

    public boolean gameOver() {
        return gameOver;
    }

    public int[][] movingShapeLayoutArray() {
        return mapper.shape.getLayoutArray();
    }

    public void setRandomShapeGenerator(RandomShapeGenerator rsg){
        this.randomShapeGenerator = rsg;
    }
}
