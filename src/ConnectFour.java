import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ConnectFour {
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private Token[][] board = new Token[ROWS][COLUMNS];
    private int turn = 0;
    private GUI gui;
    public ConnectFour(){
        gui = new GUI(this);
    }
    public void startGame(){
        fillBoard();
        while(turn <= ROWS * COLUMNS){
            gameLoop();
            if(hasWon() != Token.emptyToken){
                //render();
                gui.render(board);
                gui.setVictory(hasWon() == Token.darkToken);
                System.out.printf("%s has won!\n", hasWon());
                break;
            }
        }
        if(hasWon() == Token.emptyToken){
            System.out.println("The game ended in a tie.");
        }
        System.out.println("The game will close in 5 seconds.");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
    private void gameLoop(){
        System.out.printf("%dth turn:\n", ++turn);
        //render();
        gui.render(board);
        if(turn % 2 == 1){
            getUserInput();
        }else{
            addToken(mustMove(), true);
            /*BotTrePuntoZero btpz = new BotTrePuntoZero();
            addToken(btpz.getBestMove(), true);*/
        }
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
        do {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }while(gui.getChosenColumn() == 0);
        addToken(gui.getChosenColumn(), false);
        /*Scanner sc = new Scanner(System.in);
        System.out.println("Where do you want to put your token? insert the number of the column from 1 to " + COLUMNS);
        addToken(sc.nextInt() - 1, turn % 2 == 0);*/
    }
    private void addToken(int column, boolean isPlayer1){
        for (int i = ROWS - 1; i >= 0; i--) {
            if(board[i][column].getLabel() == Token.emptyToken){ //     Se è vuoto
                board[i][column] = new Token(isPlayer1); // sui turni pari da un simbolo e sui dispari l'altro
                return;
            }
        }
        System.out.println("The chosen column is full!");
        turn--;
    }
    private void removeLastToken(int column){
        for (int i = 0; i < ROWS; i++) {
            if(board[i][column].getLabel() != Token.emptyToken){ //     Se è vuoto
                board[i][column] = new Token();
                return;
            }
        }
    }

    private char hasWon(){ //restituisce il simbolo del vincitore, '=' se è patta o il simbolo vuoto se non è finita
        char winner = '=';
        // 4 in verticale
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS - 3; j++) { // - 4 perchè ne servono 4 in fila
                if(board[j][i].getLabel() == board[j + 1][i].getLabel() &&
                        board[j][i].getLabel() == board[j + 2][i].getLabel() &&
                        board[j][i].getLabel() == board[j + 3][i].getLabel() &&
                        board[j][i].getLabel() != Token.emptyToken){ // se ce ne sono 4 di fila
                    return board[j][i].getLabel();
                }
            }
        }
        // 4 in orizzontale
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) { // - 4 perchè ne servono 4 in fila
                if(board[i][j].getLabel() == board[i][j + 1].getLabel() &&
                        board[i][j].getLabel() == board[i][j + 2].getLabel() &&
                        board[i][j].getLabel() == board[i][j + 3].getLabel() &&
                        board[i][j].getLabel() != Token.emptyToken){ // se ce ne sono 4 di fila
                    return board[i][j].getLabel();
                }
            }
        }
        // diagonali /
        for (int i = 3; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if(board[i][j].getLabel() == board[i - 1][j + 1].getLabel() &&
                        board[i][j].getLabel() == board[i - 2][j + 2].getLabel() &&
                        board[i][j].getLabel() == board[i - 3][j + 3].getLabel() &&
                        board[i][j].getLabel() != Token.emptyToken){ // se ce ne sono 4 di fila
                    return board[i][j].getLabel();
                }
            }
        }
        // diagonali \
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if(board[i][j].getLabel() == board[i + 1][j + 1].getLabel() &&
                        board[i][j].getLabel() == board[i + 2][j + 2].getLabel() &&
                        board[i][j].getLabel() == board[i + 3][j + 3].getLabel() &&
                        board[i][j].getLabel() != Token.emptyToken){ // se ce ne sono 4 di fila
                    return board[i][j].getLabel();
                }
            }
        }
        for (int i = 0; i < COLUMNS; i++) {
            if(board[0][i].getLabel() == Token.emptyToken){
                winner = Token.emptyToken; // se la partita non è finita
            }
        }
        return winner;
    }

    //VERSIONE 2

    public int mustMove(){ // se una mossa pul portare alla vittoria di una delle due parti DEVE essere fatta, o per vincere o per bloccare
                           // il robot non è comunque invincibile perchè se viene attaccato in due modi diversi può solo fare una mossa
        gui.setChosenColumn((byte) 0);
        for (int i = 0; i < COLUMNS; i++) {
            // La colonna è piena?
            if (board[0][i].getLabel() == Token.emptyToken) {
                addToken(i, true);
                if(hasWon() != Token.emptyToken){
                    removeLastToken(i);
                    System.out.println("Se faccio questa mossa vinco.");
                    return i;
                }
                removeLastToken(i);
                addToken(i, false);
                if(hasWon() != Token.emptyToken){
                    removeLastToken(i);
                    System.out.println("Se non faccio questa mossa perdo.");
                    return i;
                }
                removeLastToken(i);
            }
        }
        return new Random().nextInt(ROWS) + 1;
    }


    /* non abbastanza efficiente per il gioco che abbiamo noi, ci mette troppo a scegliere una mossa e non sono sicuro che scelga la migliore :(

    VERSIONE 1


    public class MiniMaxBot{

        public void bestMove() {
            double bestScore = Double.NEGATIVE_INFINITY;
            int move = 1;
            for (int i = 0; i < COLUMNS; i++) {
                // La colonna è piena?
                if (board[0][i].getLabel() == Token.emptyToken) {
                    addToken(i);
                    double score = minimax(0, false);
                    removeLastToken(i);
                    if (score > bestScore) {
                        bestScore = score;
                        move = i;
                    }
                }
            }
            addToken(move);
        }

        public double minimax(int depth, boolean isMaximizing){
            double bestScore;
            char winner = hasWon();
            switch(winner){
                case Token.darkToken /*giocatore*//* -> {
                    return -10;
                }
                case Token.lightToken /*robot*//* -> {
                    return 10;
                }
                case '=' -> {
                    return 0; //patta
                }
            }
            if(isMaximizing){
                bestScore = Double.NEGATIVE_INFINITY; // mettiamo il minimo punteggio possibile in modo da cercare il punteggio maggiore
                for (int i = 0; i < COLUMNS; i++) { // controlliamo ogni colonna
                    if(board[0][i].getLabel() == Token.emptyToken){ //se non è piena
                        addToken(i);
                        double score = minimax(depth + 1, false);
                        removeLastToken(i);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }else{
                bestScore = Double.POSITIVE_INFINITY; // mettiamo il massimo punteggio possibile in modo da cercare il punteggio minore
                for (int i = 0; i < COLUMNS; i++) { // controlliamo ogni colonna
                    if(board[0][i].getLabel() == Token.emptyToken){ //se non è piena
                        addToken(i);
                        double score = minimax(depth + 1, true);
                        removeLastToken(i);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
    */

    /*class BotTrePuntoZero{ // ispirato da Bellemo Nicolò
        private int[] scores = new int[COLUMNS];
        public BotTrePuntoZero(){

        }
        private void setScores(){
            for (int i = 0; i < COLUMNS; i++) {
                scores[i] = 0;
                int posX = i;
                int posY = 0;
                if(board[0][i].getLabel() == Token.emptyToken){//se la colonna non è piena
                    while(posY < 6 && board[0][posY + 1].getLabel() == Token.emptyToken){
                        posY++;
                    }
                    for (int j = 0; j < 7; j++) {
                        int deltaX = switch(j){
                            case 0, 1, 2 -> 1;
                            case 4, 5, 6 -> -1;
                            default -> 0;
                        };
                        int deltaY = switch(j){
                            case 0, 6 -> 1;
                            case 2, 3, 4 -> -1;
                            default -> 0;
                        };
                        char tokenType;
                        try{
                            tokenType = board[posY + deltaY][posX + deltaX].getLabel();
                        }catch(IndexOutOfBoundsException normale){
                            tokenType = Token.emptyToken;
                            scores[i]--;
                        }
                        for (int k = 1; k <= 4; k++) {
                            try {
                                if (tokenType != Token.emptyToken && board[posY + k * deltaY][posX + k * deltaX].getLabel()==tokenType) {
                                    scores[i]++;
                                }
                            }catch(IndexOutOfBoundsException normale){}
                        }
                    }
                }else{
                    System.out.println("...................................................................");
                }
            }
        }
        private int getBestMove(){
            setScores();
            int bestPlace = 0;
            int bestScore = 0;
            for (int i = 0; i < COLUMNS; i++) {
                if(scores[i] > bestScore){
                    bestScore = scores[i];
                    bestPlace = i;
                }
            }
            return bestPlace;
        }
    }*/
}
