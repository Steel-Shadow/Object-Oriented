import java.util.HashSet;

public class Lexer {
    private static final HashSet<Character> VAR_SET = new HashSet<Character>() {{
            add('x');
            add('y');
            add('z');
        }};

    private static final HashSet<String> TRIGFUNC_SET = new HashSet<String>() {{
            add("sin");
            add("cos");
        }};

    private static final HashSet<Character> FUNC_SET = new HashSet<Character>() {{
            add('f');
            add('g');
            add('h');
        }};

    private static final HashSet<String> DERIVATIVE_SET = new HashSet<String>() {{
            add("dx");
            add("dy");
            add("dz");
        }};

    private final String input;
    private int index;
    private String curToken;

    public Lexer(String input) {
        this.input = input;
        index = 0;
        next();
    }

    private boolean getNumber() {
        char c = input.charAt(index);
        if (Character.isDigit(c)) {
            StringBuilder sb = new StringBuilder();
            sb.append(c);
            index++;
            while (index < input.length() && Character.isDigit(input.charAt(index))) {
                sb.append(input.charAt(index));
                index++;
            }
            curToken = sb.toString();
            return true;
        }
        return false;
    }

    private boolean getVar() {
        char c = input.charAt(index);
        if (VAR_SET.contains(c)) {
            curToken = String.valueOf(c);
            index++;
            return true;
        }
        return false;
    }

    private boolean getOperator() {
        char c = input.charAt(index);
        switch (c) {
            case '+':
                curToken = "+";
                index++;
                return true;
            case '-':
                curToken = "-";
                index++;
                return  true;
            case '(':
                curToken = "(";
                index++;
                return true;
            case ')':
                curToken = ")";
                index++;
                return true;
            case '*':
                index++;
                if (index == input.length() || input.charAt(index) != '*') {
                    curToken = "*";
                } else {
                    curToken = "**";
                    index++;
                }
                return true;
            case ',':
                curToken = ",";
                index++;
                return true;
            default:
                return false;
        }
    }

    private boolean getTrigfunc() {
        if (index + 3 > input.length()) {
            return false;
        }
        String str = input.substring(index, index + 3);
        if (TRIGFUNC_SET.contains(str)) {
            curToken = str;
            index += 3;
            return true;
        }
        return false;
    }

    private boolean getFunction() {
        char c = input.charAt(index);
        if (FUNC_SET.contains(c) && input.charAt(index + 1) != '\'') {
            curToken = String.valueOf(c);
            index++;
            return true;
        }
        return false;
    }

    private boolean getDerivative() {
        if (index + 2 > input.length()) {
            return false;
        }
        String str = input.substring(index, index + 2);
        if (DERIVATIVE_SET.contains(str)) {
            curToken = str;
            index += 2;
            return true;
        }
        return false;
    }

    public boolean next() {
        while (index < input.length()
                && (input.charAt(index) == ' '
                || input.charAt(index) == '\t')) {
            index++;
        }
        if (index < input.length()) {
            if (getNumber()) {
                return true;
            }
            if (getVar()) {
                return true;
            }
            if (getOperator()) {
                return true;
            }
            if (getTrigfunc()) {
                return true;
            }
            if (getFunction()) {
                return true;
            }
            if (input.charAt(index) == '=') {
                curToken = "=";
                index++;
                return true;
            }
            if (getDerivative()) {
                return true;
            }
            curToken = null;
        }
        return false;
    }

    public String getToken() {
        return curToken;
    }

    public boolean hasNext() {
        return index <= input.length();
    }
}
