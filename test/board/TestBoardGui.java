package board;

import static junit.framework.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

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
        for (CellGui cell : testee.getCellGuis()) {
            assertFalse(cell.underlying().isPopulated());
        }
    }
}
