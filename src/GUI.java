import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUI extends JFrame {
    public GUI() {
        super();
        GridLayout gridLayout = new GridLayout();
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        setLayout(gridLayout);
        setVisible(true);
    }
    public void render(Token[][] board){
        this.removeAll();
        for (Token[] row : board) {
            for(Token token : row){
                JPanel jp = new JPanel();
                switch(token.getLabel()){
                    case Token.darkToken -> jp.setBackground(new Color(0xFF0000));
                    case Token.lightToken -> jp.setBackground(new Color(0xFFFF00));
                    default -> jp.setBackground(Color.WHITE);
                }
                this.add(jp);
            }
        }
    }
}
