package board;

import shapes.Shape;

import java.awt.*;

/**
 */
public class Cell {
    public final int row;
    public final int column;
    private boolean populated;
    private Color colour;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        colour = Board.DEFAULT_EMPTY_COLOUR;
    }

    public Cell setPopulated(boolean populated, Shape shape) {
        this.populated = populated;
        colour = ((populated && shape!=null) ? shape.getColour() : Board.DEFAULT_EMPTY_COLOUR);
        return this;
    }

    Cell setPopulated(Shape shape){
        return setPopulated(true, shape);
    }

    Cell setPopulated(boolean populated) {
        return setPopulated(populated, null);
    }

    Color getColour() {
        return colour;
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

    public void setColour(Color colour) {
        this.colour = colour;
    }
}
