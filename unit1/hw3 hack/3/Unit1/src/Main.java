import java.util.Scanner;

public class Main {

    public int findFirstLeftBracket(Lexer lexer) {
        for (int i = 0; i < 10; i++) {
            if (lexer.getToken(i).getName().equals("(")) {
                return i;
            }
        }
        return 0;
    }

    public int findFirstRightBrachket(Lexer lexer) {
        for (int i = 0; i < 10; i++) {
            if (lexer.getToken(i).getName().equals(")")) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int fnum = input.nextInt();
        input.nextLine();


        for (int i = 1; i <= fnum; i++) {
            Lexer lexer = new Lexer(input.nextLine());
            Parser parser = new Parser(lexer);
            char name = lexer.getToken(0).getCharacter();
            int l = new Main().findFirstLeftBracket(lexer);
            int r = new Main().findFirstRightBrachket(lexer);

            Op op = parser.parseOp(r + 2, lexer.size() - 1,false);

            Poly result = op.calculate();
            Lexer polyLexer = new Lexer(result.toString());
            Parser polyParser = new Parser(polyLexer);
            Op root = polyParser.parseOp(0, polyLexer.size() - 1, true);

            FunDefine funDefine = new FunDefine(root);
            for (int j = l + 1, k = 1; j <= r - 1; j += 2, k++) {
                char var = lexer.getToken(j).getCharacter();
                funDefine.setRank(var, k);
            }
            FunEnv.addFunc(name, funDefine);
        }

        Lexer lexer = new Lexer(input.nextLine());
        Parser parser = new Parser(lexer);
        Op root = parser.parseOp(0, lexer.size() - 1, false);
        Poly ans = root.calculate();

        System.out.println(ans);
    }
}