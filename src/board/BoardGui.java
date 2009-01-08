package board;

import shapes.Square;
import shapes.Shape;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 */
class BoardGui extends JPanel {
    List<CellGui> cellGuis;
    private Board board;

    BoardGui(int x, int y) {
        setLayout(new GridLayout(x, y));
        setSize(x * CellGui.CELL_SIZE + 50, y * CellGui.CELL_SIZE + 50);

        setBorder(new LineBorder(Color.black));
        board = new Board(x, y);
        createCellGuis();
    }

    public List<CellGui> getCellGuis() {
        return cellGuis;
    }

    public Board getBoard(){
        return board;
    }

    private void createCellGuis() {
        cellGuis = new ArrayList<CellGui>();
        for (Cell cell : board.getCells()) {
            CellGui cellGui = new CellGui(cell);
            add(cellGui);
            cellGuis.add(cellGui);
        }
    }

    void start() throws InterruptedException {
        addNewShape(new Square());
        while (!board.gameOver()){
            tick();
            Thread.sleep(250);
        }
    }

    private void tick(){
        board.tick();
        for (CellGui cell : getCellGuis()){
            cell.recolour();
        }
    }

    void addNewShape(Shape shape){
        board.addNewShape(shape);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException, InterruptedException {
        BoardGui game = new BoardGui(30, 10);
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        JFrame app = new JFrame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLayout(new FlowLayout());
        app.add(game);
        app.setSize(500, 600);
        app.setLocation(200,200);
        app.setVisible(true);
        app.setTitle("Tetris");
        game.start();
    }
}
