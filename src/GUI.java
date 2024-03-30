import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private ConnectFour cf;
    private byte chosenColumn = 0;
    public GUI(ConnectFour cf) {
        super();
        this.cf = cf;
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
                jp.setName("" + j);
                jp.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if(e.getButton() == MouseEvent.BUTTON1){
                            setChosenColumn(Byte.parseByte(jp.getName()));
                        }
                    }
                });
                this.getContentPane().add(jp);
            }
        }
        this.pack();
    }

    public void setVictory(boolean player){
        JFrame jd = new JFrame();
        jd.setTitle("Victory");
        JLabel label = new JLabel(String.format("You %s", player ? "won!" : "lost..."));
        label.setSize(200, 50);
        label.setFont(new Font("Helvetica", Font.BOLD, 50));
        label.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        jd.add(label);
        jd.pack();
        jd.setVisible(true);
    }

    public ConnectFour getCf() {
        return cf;
    }

    public void setCf(ConnectFour cf) {
        this.cf = cf;
    }

    public byte getChosenColumn() {
        return chosenColumn;
    }

    public void setChosenColumn(byte chosenColumn) {
        this.chosenColumn = chosenColumn;
    }
}
