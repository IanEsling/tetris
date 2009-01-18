package board;

import java.util.List;

/**
 */
class ShapeMovementValidator implements MovementValidator {
    ShapeLayoutToBoardCellMapper mapper;

    ShapeMovementValidator(ShapeLayoutToBoardCellMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean canMove(Movement movement) {
        for (Cell cell : shapeCells()) {
            if (newCellIsAlreadyPopulated(movement, cell))
                return false;

            if (!movement.allowMove(cell, mapper.board)) return false;
        }
        return true;
    }

    List<Cell> shapeCells() {
        return mapper.shapeCellsAsList();
    }

    private boolean newCellIsAlreadyPopulated(Movement movement, Cell cell) {
        return mapper.board.getCell(newRow(movement, cell), newCol(movement, cell)).isPopulated() &&
                (!shapeCells().contains(new Cell(newRow(movement, cell), newCol(movement, cell))));
    }

    private int newCol(Movement movement, Cell cell) {
        return cell.column + movement.getColumnChange() > mapper.totalBoardColumns() - 1 ? mapper.totalBoardColumns() - 1 :
                (cell.column + movement.getColumnChange() < 0 ? 0 : cell.column + movement.getColumnChange());
    }

    private int newRow(Movement movement, Cell cell) {
        return cell.row + movement.getRowChange() > mapper.totalBoardRows() - 1 ? mapper.totalBoardRows() - 1 : cell.row + movement.getRowChange();
    }
}
