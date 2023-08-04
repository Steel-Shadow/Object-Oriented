public class Lexer {
    private String input;
    private int pointer = 0;
    private String now;

    public Lexer(String rawInput) {
        String input;
        input = rawInput.replace("\t", "");
        input = input.replace(" ", "");
        input = input.replace("+++", "+");
        input = input.replace("++-", "-");
        input = input.replace("+-+", "-");
        input = input.replace("-++", "-");
        input = input.replace("+--", "+");
        input = input.replace("-+-", "+");
        input = input.replace("--+", "+");
        input = input.replace("---", "-");
        input = input.replace("++", "+");
        input = input.replace("+-", "-");
        input = input.replace("--", "+");
        input = input.replace("-+", "-");
        input = input.replace("**", "^");
        input = input.replace("^+", "^");
        input = input.replace("*+", "*!");
        input = input.replace("*-", "*@");
        this.input = input;
        this.next();
    }

    private String getNumber() {
        StringBuilder sb = new StringBuilder();
        while (pointer < input.length() &&
                ((Character.isDigit(input.charAt(pointer))) ||
                        input.charAt(pointer) == '!' || input.charAt(pointer) == '@')) {
            sb.append(input.charAt(pointer));
            ++pointer;
        }

        return sb.toString();
    }

    private String getPowerFac() {
        StringBuilder sb = new StringBuilder();
        while (pointer < input.length() && (
                ("xyz".indexOf(input.charAt(pointer)) != -1) ||
                        input.charAt(pointer) == '^' ||
                        Character.isDigit(input.charAt(pointer)))
        ) {
            sb.append(input.charAt(pointer));
            ++pointer;
        }
        return sb.toString();
    }

    private String getDeriFac() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(input.charAt(pointer));
            ++pointer;
        }
        int cnt = 1;
        while (cnt > 0) {
            sb.append(input.charAt(pointer));
            if (input.charAt(pointer) == ')') {
                cnt--;
            } else if (input.charAt(pointer) == '(') {
                cnt++;
            }
            ++pointer;
        }
        return sb.toString();
    }

    public void next() {
        if (pointer == input.length()) {
            return;
        }
        char c = input.charAt(pointer);
        if (c == '!' || c == '@') {
            now = this.getNumber();
        } else if (Character.isDigit(c)) {
            now = this.getNumber();
        } else if ("xyz".indexOf(c) != -1) {
            now = this.getPowerFac();
        } else if ("+-*()^".indexOf(c) != -1) {
            pointer += 1;
            now = String.valueOf(c);
        } else if (c == 's') {
            if (input.charAt(pointer + 1) == 'i') {
                pointer += 3;
                now = "sin";
            } else {
                pointer += 1;
                now = String.valueOf(c);
            }
        } else if (c == 'c') {
            pointer += 3;
            now = "cos";
        } else if (c == 'd') {
            now = this.getDeriFac();
        }
    }

    public String getNow() {
        return this.now;
    }

}
