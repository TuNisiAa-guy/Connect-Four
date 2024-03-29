import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI() {
        super();
        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(7);
        gridLayout.setRows(6);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        getContentPane().setLayout(gridLayout);
        setLayout(gridLayout);
        getContentPane().setBackground(Color.BLUE);
        setSize(new Dimension(800, 600));
        setVisible(true);
    }
    public void render(Token[][] board){
        this.getContentPane().removeAll();
        for (int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++){
                JPanel jp = new JPanel();
                jp.setLayout(new FlowLayout());
                jp.setBounds(i * 50, j * 50, 50, 50);
                jp.setPreferredSize(new Dimension(50, 50));
                switch(board[i][j].getLabel()){
                    case Token.darkToken -> jp.setBackground(new Color(0xFF0000));
                    case Token.lightToken -> jp.setBackground(new Color(0xFFFF00));
                    default -> jp.setBackground(Color.WHITE);
                }
                this.getContentPane().add(jp);
            }
        }
        this.pack();
    }
}
