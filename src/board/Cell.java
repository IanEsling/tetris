package board;

/**
 */
public class Cell {
    public int row;
    public int column;
    boolean populated;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    Cell setPopulated(boolean populated) {
        this.populated = populated;
        return this;
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
}
