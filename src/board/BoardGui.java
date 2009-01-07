package board;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;

/**
 */
class BoardGui extends JPanel {
    List<CellGui> cellGuis;
    private Board board;

    BoardGui(int x, int y) {
        setSize(800, 700);
        setLayout(new GridLayout(x, y));
        board = new Board(x, y);
        createCellGuis();
    }

    public List<CellGui> getCellGuis() {
        return cellGuis;
    }

    private void createCellGuis() {
        cellGuis = new ArrayList<CellGui>();
        for (Cell cell : board.getCells()) {
            CellGui cellGui = new CellGui(cell);
            add(cellGui);
            cellGuis.add(cellGui);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        BoardGui game = new BoardGui(30,10);
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        JFrame app = new JFrame();
        app.setLayout(new FlowLayout());
        app.add(game);
        app.setSize(800,700);
        app.setVisible(true);
        app.setTitle("Tetris");
    }
}
