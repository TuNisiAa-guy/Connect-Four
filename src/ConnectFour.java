public class ConnectFour {
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private Token[][] board = new Token[ROWS][COLUMNS];
    private int turn = 0;
    public ConnectFour(){
        fillBoard();
        render();
    }
    public void startGame(){
        for (int i = 0; i < ROWS * COLUMNS; i++) {
            gameLoop();
        }
        System.out.println("The game ended in a tie");
    }
    private void gameLoop(){
        System.out.printf("%dth turn:\n", ++turn);

    }
    private void render(){
        for (int i = 0; i < ROWS; i++) {
            System.out.print("| ");
            for (int j = 0; j < COLUMNS; j++) {
                System.out.printf("%s | ", board[i][j].getLabel());
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }
    private void fillBoard(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = new Token();
            }
        }
    }
    private void addToken(int column){
        for (int i = 0; i < ROWS; i++) {
            if(board[i][column].getLabel() == Token.emptyToken){ //     Se è vuoto
                board[i][column] = new Token(turn % 2 == 0); // sui turni pari da un simbolo e sui dispari l'altro
            }
        }
        System.out.println("La colonna che hai scelto è piena!");
        turn--;
    }
}
