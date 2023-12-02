import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ConnectFour {
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private Token[][] board = new Token[ROWS][COLUMNS];
    private int turn = 0;
    public ConnectFour(){
    }
    public void startGame(){
        fillBoard();
        for (int i = 0; i < ROWS * COLUMNS; i++) {
            gameLoop();
            if(isWin()){
                System.out.println("Qualcuno ha vinto");
            }
        }
        System.out.println("The game ended in a tie.\nThe game will close in 5 seconds.");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(69);
    }
    private void gameLoop(){
        System.out.printf("%dth turn:\n", ++turn);
        render();
        getUserInput();
    }
    private void render(){
        for (int i = 0; i < ROWS; i++) {
            System.out.print("| ");
            for (int j = 0; j < COLUMNS; j++) {
                System.out.printf("%s | ", board[i][j].getLabel());
            }
            System.out.println();
        }
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
    }
    private void fillBoard(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = new Token();
            }
        }
    }
    private void getUserInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Where do you want to put your token? insert the number of the column from 1 to " + COLUMNS);
        addToken(sc.nextInt() - 1);
    }
    private void addToken(int column){
        for (int i = ROWS - 1; i >= 0; i--) {
            if(board[i][column].getLabel() == Token.emptyToken){ //     Se è vuoto
                board[i][column] = new Token(turn % 2 == 0); // sui turni pari da un simbolo e sui dispari l'altro
                return;
            }
        }
        System.out.println("The chosen column is full!");
        turn--;
    }

    private boolean isWin(){
        // 4 in verticale
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS - 4; j++) { // - 4 perchè ne servono 4 in fila
                if(board[j][i].getLabel() == board[j + 1][i].getLabel() &&
                   board[j][i].getLabel() == board[j + 2][i].getLabel() &&
                   board[j][i].getLabel() == board[j + 3][i].getLabel() &&
                   board[j][i].getLabel() != Token.emptyToken){ // se ce ne sono 4 di fila
                    return true;
                }
            }
        }
        // 4 in orizzontale
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 4; j++) { // - 4 perchè ne servono 4 in fila
                if(board[i][j].getLabel() == board[i][j + 1].getLabel() &&
                   board[i][j].getLabel() == board[i][j + 2].getLabel() &&
                   board[i][j].getLabel() == board[i][j + 3].getLabel() &&
                   board[i][j].getLabel() != Token.emptyToken){ // se ce ne sono 4 di fila
                    return true;
                }
            }
        }
        return false;
    }
}
