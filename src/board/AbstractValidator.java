package board;

import java.util.List;

/**
 */
public class AbstractValidator {
    ShapeLayoutToBoardCellMapper mapper;

    public AbstractValidator(ShapeLayoutToBoardCellMapper mapper) {
        this.mapper = mapper;
    }

    List<Cell> shapeCells() {
        return mapper.shapeCellsAsList();
    }

    protected int[][] shapeLayoutArray() {
        return mapper.shape.getLayoutArray();
    }

    protected Cell boardCellAt(int row, int col) {
        return mapper.boardCellAt(row, col);
    }

    protected int totalBoardRows() {
        return mapper.totalBoardRows();
    }

    protected int totalBoardColumns() {
        return mapper.totalBoardColumns();
    }

    protected Board board() {
        return mapper.board;
    }
}
