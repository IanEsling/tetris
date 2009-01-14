package board;

import java.util.List;

/**
 */
class MovingShapeMovementValidator implements MovementValidator {
    Board board;

    MovingShapeMovementValidator(Board board) {
        this.board = board;
    }

    @Override
    public boolean canMove(Movement movement) {
        for (Cell cell : shapeCells()) {
            if (newCellIsAlreadyPopulated(movement, cell))
                return false;

            if (!movement.allowMove(cell, board)) return false;
        }
        return true;
    }

    List<Cell> shapeCells() {
        return board.getMovingShape().shapeCellsAsList();
    }

    private boolean newCellIsAlreadyPopulated(Movement movement, Cell cell) {
        return board.cellAt(newRow(movement, cell), newCol(movement, cell)).isPopulated() &&
                (!shapeCells().contains(new Cell(newRow(movement, cell), newCol(movement, cell))));
    }

    private int newCol(Movement movement, Cell cell) {
        return cell.column + movement.getColumnChange() > board.getColumns() - 1 ? board.getColumns() - 1 :
                (cell.column + movement.getColumnChange() < 0 ? 0 : cell.column + movement.getColumnChange());
    }

    private int newRow(Movement movement, Cell cell) {
        return cell.row + movement.getRowChange() > board.getRows() - 1 ? board.getRows() - 1 : cell.row + movement.getRowChange();
    }
}
