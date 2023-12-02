public class Token{
    private char label;
    public static final char emptyToken = '⨀';
    private final char darkToken = '●';
    private final char lightToken = '○';
    public Token(boolean isPlayer1){
        label = isPlayer1 ? lightToken : darkToken;
    }

    public Token(){
        label = emptyToken;
    }

    public char getLabel() {
        return label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

}
