import analyse.Lexer;

public class FuncParser {
    private final Lexer lexer;
    private String funcName = "";
    private String funcBody = "";

    public FuncParser(Lexer lexer) {
        this.lexer = lexer;
        parseFunc();
    }

    private void parseFunc() {
        if (lexer.getSign() == 7) {
            funcName += lexer.peek();
            lexer.next();
            if (lexer.getSign() == 4) {
                funcName += lexer.peek();
                lexer.next();
            } else {
                throw new RuntimeException("Expected '('");
            }
        } else {
            throw new RuntimeException("Expected 'def'");
        }
        while (lexer.getSign() != 5) {
            //如果getsign不是6,则不是变量名,抛出异常
            if (lexer.getSign() != 6) {
                throw new RuntimeException("Expected variable name");
            } else {
                funcName += lexer.peek();
                lexer.next();
            }
            //如果getsign是8,则是逗号,则继续
            if (lexer.getSign() == 8) {
                funcName += lexer.peek();
                lexer.next();
            } else if (lexer.getSign() == 5) {
                funcName += lexer.peek();
            } else {
                throw new RuntimeException("Expected ',' or ')'");
            }
        }
        lexer.next();
        //读入等号然后忽略
        if (lexer.getSign() == 10) {
            lexer.next();
        } else {
            throw new RuntimeException("Expected '='");
        }
        //读入函数体
        while (lexer.getSign() != -1) {
            funcBody += lexer.peek();
            lexer.next();
        }
    }

    public String getFuncName() {
        return funcName;
    }

    public String getFuncBody() {
        return funcBody;
    }

}
