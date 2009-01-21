import board.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import shapes.Shape;

/**
 */
public class GameRunner {

    private final BoardGui board;
    private JFrame app;

    private GameRunner() throws InterruptedException {
        board = new BoardGui(30, 10);
        app = new JFrame();
        newApp();
        app.add(board);
        app.add(new NextShapeGui(board));
        start();
    }

    void start() throws InterruptedException {
        board.addNewShapeAtRandom();
        board.repaintBoard();
        while (!board.gameOver()) {
            board.tick();
            Thread.sleep(250);
        }
    }

    private void newApp() {
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLayout(new FlowLayout());
        app.setSize(500, 600);
        app.setLocation(200, 200);
        app.setVisible(true);
        app.setTitle("Tetris");
        app.addKeyListener(new ShapeMover());
    }

    private class ShapeMover implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) board.getBoard().moveShapeToLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) board.getBoard().moveShapeToRight();
            if (e.getKeyCode() == KeyEvent.VK_UP) board.getBoard().rotateShapeAntiClockwise();
            if (e.getKeyCode() == KeyEvent.VK_DOWN) board.getBoard().rotateShapeClockwise();
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                while (board.getBoard().movingShapeCanMoveDown()) {
                    board.tick();
                }
            }

            board.repaintBoard();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException, InterruptedException {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        new GameRunner();
    }

    private class NextShapeGui extends JPanel implements TickListener {
        Board board;
        List<CellGui> cells;

        public NextShapeGui(BoardGui board) {
            this.board = board.getBoard();
            this.board.addTickListener(this);
            setLayout(new GridLayout(4, 4));

            setBorder(new LineBorder(Color.black));
            cells = new ArrayList<CellGui>();

            for (int row = 0;row<4;row++){
                for (int col = 0;col<4;col++){
                    CellGui cell = new CellGui(new Cell(row, col));
                    add(cell);
                    cells.add(cell);
                }
            }
        }


        @Override
        public void boardHasTicked() {
            Shape nextShape = board.getNextShape();
            for (int row = 0;row<nextShape.getLayoutArray().length;row++){
                for (int col = 0;col<nextShape.getLayoutArray()[0].length;col++){
                    cellGuiAt(row, col).underlying().setPopulated(
                            nextShape.getLayoutArray()[row][col] == 1, nextShape);
                    cellGuiAt(row, col).recolour();
                }
            }
        }

        private CellGui cellGuiAt(int row, int col){
            for (CellGui cell : cells){
                if (cell.underlying().row == row && cell.underlying().column == col) return cell;
            }
            throw new RuntimeException("unable to find cell at "+row+", "+col+" in next shape gui");
        }
    }

}
