package board;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.awt.Color;

import shapes.Square;
import shapes.Shape;

/**
 */
public class TestCellGui {

    @Test
    public void changeColourWhenPopulated(){
        Cell cell = new Cell(5,5);
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
}
