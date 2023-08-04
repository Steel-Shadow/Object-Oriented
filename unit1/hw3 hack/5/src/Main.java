import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputHandel inputHandel = new InputHandel(Integer.parseInt(scanner.nextLine()));
        for (int i = 0; i < inputHandel.getNumOfSelfDefFunc(); i++) {
            inputHandel.getRawDefine().add(scanner.nextLine());
        }
        inputHandel.handleSelfDef();
        String input = inputHandel.handleInput(scanner.nextLine(), 'i');
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        Expr expr = parser.parseExpr();
        Polynomial polynomial = expr.toPoly(1);
        System.out.println(polynomial.toString());
    }
}
