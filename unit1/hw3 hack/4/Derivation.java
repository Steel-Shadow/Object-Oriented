public class Derivation implements Factor {
    private final String content;

    private final int flag;

    public Derivation(String content, String op) {
        this.flag = op.equals("x") ? 1 : op.equals("y") ? 2 : op.equals("z") ? 3 : 4;
        this.content = content;
    }

    public Poly toPoly() {
        Lexer lexer3 = new Lexer(content);
        Parser parser3 = new Parser(lexer3);
        return parser3.parseExpr().toPoly().derive(flag);
    }

}
