package board;

import shapes.Shape;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class BoardGui extends JPanel {
    private List<CellGui> cellGuis;
    private final Board board;

    public BoardGui(int x, int y) {
        setLayout(new GridLayout(x, y));
        setSize(x * CellGui.CELL_SIZE + 50, y * CellGui.CELL_SIZE + 50);

        setBorder(new LineBorder(Color.black));
        board = new Board(x, y);
        createCellGuis();
    }

    private void createCellGuis() {
        cellGuis = new ArrayList<CellGui>();
        for (Cell cell : board.getBoardCells()) {
            CellGui cellGui = new CellGui(cell);
            add(cellGui);
            cellGuis.add(cellGui);
        }
    }

    public void tick(){
        board.tick();
        repaintBoard();
    }

    public void repaintBoard() {
        for (CellGui cell : getCellGuis()){
            cell.recolour();
        }
    }

    public List<CellGui> getCellGuis() {
        return cellGuis;
    }

    public Board getBoard(){
        return board;
    }

    void addNewShape(Shape shape){
        board.addNewShape(shape);
    }

    public void addNewShapeAtRandom() {
        board.addNewShapeAtRandom();
    }

    public boolean gameOver() {
        return board.gameOver();
    }
}
