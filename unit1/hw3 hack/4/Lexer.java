import java.math.BigInteger;

public class Lexer {
    private final String input;

    private int pos = 0;

    private String curToken;

    public Lexer(String input) {
        this.input = input.replaceAll("\\t","")
                .replaceAll(" ","").replaceAll("[*]{2}","^")
                .replaceAll("sin","s").replaceAll("cos","c");
        this.next();
    }

    private String getNumber() {    //指向下一位
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            ++pos;
        }
        return sb.toString();
    }

    public void next() {
        if (pos == input.length()) {
            return;
        }
        char c = input.charAt(pos);
        if (Character.isDigit(c)) {
            curToken = getNumber();
        }
        else if ("(),^*+-xyzfghcsd".indexOf(c) != -1) {
            pos += 1;
            curToken = String.valueOf(c);
        }
    }

    public String peek() {
        return this.curToken;
    }

    public Boolean ensureSign() {
        if (curToken.equals("-")) {
            this.next();
            return false;
        }
        else if (curToken.equals("+")) {
            this.next();
            return true;
        }
        return true;
    }

    public BigInteger ensureExp() {
        Boolean sign;
        if (curToken.equals("^")) {
            this.next();
            sign = this.ensureSign();
            BigInteger exp = new BigInteger(curToken);
            this.next();
            return this.turn(sign, exp);
        }
        else {
            return this.turn(true, BigInteger.ONE);
        }
    }

    public BigInteger turn(Boolean sign, BigInteger num) {
        return (sign) ? num : num.negate();
    }
}

