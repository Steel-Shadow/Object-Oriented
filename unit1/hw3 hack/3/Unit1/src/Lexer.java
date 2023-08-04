import java.math.BigInteger;
import java.util.ArrayList;

public class Lexer {
    private String input;

    private ArrayList<Token> tokenList = new ArrayList<>();

    private Token topToken() {
        return tokenList.get(tokenList.size() - 1);
    }

    private void reverseTopToken() {
        Token tmp = topToken();
        if (tmp.getName().equals("+")) {
            tokenList.remove(tokenList.size() - 1);
            tokenList.add(new Token("-"));
        }
        else if (tmp.getName().equals("-")) {
            tokenList.remove(tokenList.size() - 1);
            tokenList.add(new Token("+"));
        }
    }

    public String getInput() {
        return input;
    }

    public Lexer(String input) {
        this.input = input;
        int pos = 0;
        while (pos < input.length()) {
            if (input.charAt(pos) == '+') {
                if (tokenList.size() >= 1) {
                    if (!topToken().getName().equals("+") && !topToken().getName().equals("-")) {
                        tokenList.add(new Token("+"));
                    }
                }
                else { tokenList.add(new Token("+")); }
            } else if (input.charAt(pos) == '-') {
                if (tokenList.size() >= 1) {
                    if (!topToken().getName().equals("+") && !topToken().getName().equals("-")) {
                        tokenList.add(new Token("-"));
                    } else { reverseTopToken(); }
                }
                else { tokenList.add(new Token("-")); }
            } else if (input.charAt(pos) == '*' && pos + 1 < input.length() &&
                input.charAt(pos + 1) == '*') {
                tokenList.add(new Token("**"));
                pos++;
            } else if (input.charAt(pos) == '*') { tokenList.add(new Token("*")); }
            else if (input.charAt(pos) == '/') { tokenList.add(new Token("/")); }
            else if (Character.isDigit(input.charAt(pos))) {
                StringBuffer valueBuffer = new StringBuffer();
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                    valueBuffer.append(input.charAt(pos));
                    pos++;
                }
                pos--;
                String value = new String(valueBuffer);
                tokenList.add(new Token("number", new BigInteger(value)));
            } else if (input.charAt(pos) == 's') {
                tokenList.add(new Token("sin"));
                pos += 2;
            } else if (input.charAt(pos) == 'c') {
                tokenList.add(new Token("cos"));
                pos += 2;
            } else if (input.charAt(pos) == '=') {
                tokenList.add(new Token("="));
            } else if (input.charAt(pos) == 'd') {
                pos++;
                if (input.charAt(pos) == 'x') {
                    tokenList.add(new Token("dx"));
                } else if (input.charAt(pos) == 'y') {
                    tokenList.add(new Token("dy"));
                } else { tokenList.add(new Token("dz")); }
            } else if (Character.isAlphabetic(input.charAt(pos))) {
                if ('x' <= input.charAt(pos) && input.charAt(pos) <= 'z') {
                    tokenList.add(new Token("variable", input.charAt(pos)));
                } else { tokenList.add(new Token("function", input.charAt(pos))); }
            } else if (input.charAt(pos) == '(') {
                tokenList.add(new Token("("));
            } else if (input.charAt(pos) == ')') {
                tokenList.add(new Token(")"));
            } else if (input.charAt(pos) == ',') { tokenList.add(new Token(",")); }
            pos++;
        }
    }

    public Token getToken(int pos) {
        return tokenList.get(pos);
    }

    public int size() {
        return tokenList.size();
    }
}
