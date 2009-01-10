import board.BoardGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 */
public class GameRunner {

    BoardGui board;

    GameRunner() throws InterruptedException {
        board = new BoardGui(30, 10);
        newApp(board);
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

    private void newApp(BoardGui game) {
        JFrame app = new JFrame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLayout(new FlowLayout());
        app.add(game);
        app.setSize(500, 600);
        app.setLocation(200, 200);
        app.setVisible(true);
        app.setTitle("Tetris");
        app.addKeyListener(new ShapeMover());
    }

    class ShapeMover implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) board.getBoard().moveShapeToLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) board.getBoard().moveShapeToRight();

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
}
