package board;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import shapes.LShape;
import shapes.Shape;
import shapes.Square;

/**
 */
public class TestCellGui {

    @Test
    public void changeColourWhenPopulated() {
        Cell cell = new Cell(5, 5);
        CellGui testee = new CellGui(cell);
        assertEquals(Board.DEFAULT_EMPTY_COLOUR, cell.getColour());
        assertEquals(Board.DEFAULT_EMPTY_COLOUR, testee.getColour());
        Shape newShape = new Square();
        cell.setPopulated(true, newShape);
        assertEquals(newShape.getColour(), cell.getColour());
        assertEquals(newShape.getColour(), testee.getColour());
        cell.setPopulated(false);
        assertEquals(Board.DEFAULT_EMPTY_COLOUR, cell.getColour());
        assertEquals(Board.DEFAULT_EMPTY_COLOUR, testee.getColour());
    }

    @Test
    public void coloursWhenShapeRotates() {
        BoardGui board = new BoardGui(30, 10);
        board.addNewShape(new LShape());
        board.getBoard().rotateShapeAntiClockwise();
        int[][] cells = board.getBoard().getMovingShape().getShape().getCells();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                if (cells[row][col] == 0){
                    assertFalse("cell populated when should be empty", board.getBoard().cellAt(Board.START_ROW + row, Board.START_COL + col).isPopulated());
                    assertEquals("unpopulated cell not empty colour", Board.DEFAULT_EMPTY_COLOUR, cellGuiAt(row + Board.START_ROW, col + Board.START_COL, board).getColour());
                }
                else{
                    assertTrue("cell empty when should be populated", board.getBoard().cellAt(Board.START_ROW + row, Board.START_COL + col).isPopulated());
                    assertEquals("populated cell not correct shape colour", 
                            board.getBoard().getMovingShape().getShape().getColour(),
                            cellGuiAt(row + Board.START_ROW, col + Board.START_COL, board).getColour());
                }
            }
        }
    }

    private CellGui cellGuiAt(int row, int col, BoardGui board){
        for (CellGui cell : board.getCellGuis()){
            if (cell.underlying().row == row && cell.underlying().column == col) return cell;
        }
        throw new RuntimeException();
    }
}
