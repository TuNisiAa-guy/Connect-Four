public class ConnectFour {
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private Token[][] board;
    public ConnectFour(){

    }
    private void render(){
        for (int i = 0; i < ROWS; i++) {
            System.out.print("|");
            for (int j = 0; j < COLUMNS; j++) {
                System.out.printf("%s|", board[i][j].getLabel());
            }
            System.out.println();
        }
    }
}
