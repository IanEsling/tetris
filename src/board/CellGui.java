package board;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 */
public class CellGui extends JPanel {

    private final Cell cell;
    public final static int CELL_SIZE = 100;

    public CellGui(Cell cell) {
        this.cell = cell;
        setBackground(Board.DEFAULT_EMPTY_COLOUR);
        setBorder(new LineBorder(Color.black));
        setSize(CELL_SIZE,CELL_SIZE);
        setVisible(true);
    }

    public Cell underlying() {
        return cell;
    }

    public Color getColour(){
        return underlying().getColour();
    }

    public void recolour(){
        setBackground(underlying().getColour());
    }
}
