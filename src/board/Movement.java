package board;

/**
 */
public enum Movement {
    Left {
        @Override
        int getRowChange() {
            return 0;
        }
        @Override
        int getColumnChange() {
            return -1;
        }
        @Override
        boolean allowMove(Cell cell, Board board) {
            return cell.column != 0;
        }},
    Right {
        @Override
        int getRowChange() {
            return 0;
        }
        @Override
        int getColumnChange() {
            return 1;
        }
        @Override
        boolean allowMove(Cell cell, Board board) {
            return cell.column != board.getColumns() - 1;
        }},
    Down {
        @Override
        int getRowChange() {
            return 1;
        }
        @Override
        int getColumnChange() {
            return 0;
        }
        @Override
        boolean allowMove(Cell cell, Board board) {
            return cell.row != board.getRows() - 1;
        }};

    abstract int getRowChange();

    abstract int getColumnChange();

    abstract boolean allowMove(Cell cell, Board board);
}
