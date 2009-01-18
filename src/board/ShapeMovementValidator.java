package board;

/**
 */
class ShapeMovementValidator extends AbstractValidator implements MovementValidator {

    ShapeMovementValidator(ShapeLayoutToBoardCellMapper mapper) {
        super(mapper);
    }

    @Override
    public boolean isValidMove(Movement movement) {
        for (Cell cell : shapeCells()) {
            if (newCellIsAlreadyPopulated(movement, cell))
                return false;

            if (!movement.allowMove(cell, board())) return false;
        }
        return true;
    }

    private boolean newCellIsAlreadyPopulated(Movement movement, Cell cell) {
        return getBoardCell(movement, cell).isPopulated() &&
                (!shapeCells().contains(new Cell(newRow(movement, cell), newCol(movement, cell))));
    }

    private Cell getBoardCell(Movement movement, Cell cell) {
        return mapper.board.getCell(newRow(movement, cell), newCol(movement, cell));
    }

    private int newCol(Movement movement, Cell cell) {
        return cell.column + movement.getColumnChange() > totalBoardColumns() - 1 ? totalBoardColumns() - 1 :
                (cell.column + movement.getColumnChange() < 0 ? 0 : cell.column + movement.getColumnChange());
    }

    private int newRow(Movement movement, Cell cell) {
        return cell.row + movement.getRowChange() > totalBoardRows() - 1 ? totalBoardRows() - 1 : cell.row + movement.getRowChange();
    }
}
