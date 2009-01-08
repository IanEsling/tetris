package board;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import shapes.Square;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class TestBoardGui {

    BoardGui testee;

    @Before
    public void createBoard() {
        testee = new BoardGui(30, 10);
    }

    @Test
    public void board() {
        assertEquals(300, testee.getCellGuis().size());
        for (CellGui cell : testee.getCellGuis()) {
            assertFalse(cell.underlying().isPopulated());
            assertEquals(cell.getSize().getHeight(), CellGui.CELL_SIZE, 0);
            assertEquals(cell.getSize().getWidth(), CellGui.CELL_SIZE, 0);
        }
    }

    @Test
    public void addNewShapeToBoard() {
        testee.getBoard().addNewShape(new Square());
        List<CellGui> populateCells = new ArrayList<CellGui>();
        List<CellGui> emptyCells = new ArrayList<CellGui>();
        for (CellGui cell : testee.getCellGuis()) {
            if (cell.underlying().isPopulated()) populateCells.add(cell);
            else
                emptyCells.add(cell);
        }
        assertEquals("4 cells not populated with square", 4, populateCells.size());
        assertEquals("rest of board not empty after adding square", 296, emptyCells.size());
        for (CellGui cell : populateCells) {
            assertEquals("cells with square in are wrong colour", new Square().getColour(), cell.getColour());
        }
        for (CellGui cell : emptyCells){
            assertEquals("empty cells are wrong colour", Board.DEFAULT_EMPTY_COLOUR, cell.getColour());
        }
    }
}
