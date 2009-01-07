package board;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 */
class CellGui extends JPanel {

    Cell cell;

    CellGui(Cell cell) {
        this.cell = cell;
        setBackground(Color.CYAN);
        setBorder(new LineBorder(Color.black));
    }

    public Cell underlying() {
        return cell;
    }
}
