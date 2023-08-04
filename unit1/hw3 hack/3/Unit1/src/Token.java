import java.math.BigInteger;

public class Token {
    private String name;
    private BigInteger value;
    private char character;
    private boolean isOperator = false;

    public Token(String name) {
        this.name = name;
        isOperator = true;
    }

    public Token(String name, BigInteger value) {
        this.name = name;
        this.value = value;
    }

    public Token(String name, char character) {
        this.name = name;
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public BigInteger getValue() {
        return value;
    }

    public char getCharacter() {
        return character;
    }
}
