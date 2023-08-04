package analyse;

import expr.Expr;
import expr.Factor;
import expr.Term;
import expr.Myfunc;
import expr.Number;
import expr.Power;
import expr.VarNum;
import expr.Trigonom;
import expr.Differ;

import itemlist.ItemListManager;

import java.math.BigInteger;
import java.util.HashMap;

public class Parser {
    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    //递归下降所需要代码部分
    public Expr parseExpr() {
        Expr expr = new Expr();// 新建一个表达式对象
        //1.
        //先看看是不是一个负数,负数的话就把flag设为1.然后如果是正或负数都next一下
        int flag = 1;
        if (lexer.peek().equals("-")) {
            //expr.setFlag(1);
            flag = -1;
            lexer.next();
        } else if (lexer.peek().equals("+")) {
            lexer.next();
        }
        //2.
        //添加第一个项
        expr.addTerm(parseTerm(), flag);
        //expr.arrange();
        //3.
        //如果下一个token是加号或减号，那么继续添加后续的项
        while (lexer.peek().equals("+") || lexer.peek().equals("-")) {
            if (lexer.peek().equals("-")) { //如果是减号，那么把后面的项的flag设为-1
                lexer.next();
                expr.addTerm(parseTerm(), -1);
            } else {
                lexer.next();
                expr.addTerm(parseTerm(), 1);
            }
        }
        return expr;
    }

    public Term parseTerm() {
        Term term = new Term();// 新建一个表达式对象
        //1.
        //先看看是不是一个负数,负数的话就把flag设为1.然后如果是正或负数都next一下
        if (lexer.peek().equals("-")) {
            term.setFlag(1);
            lexer.next();
        } else if (lexer.peek().equals("+")) {
            lexer.next();
        }
        //2.
        //添加第一个因子
        term.addFactor(parseFactor());
        //3.
        //如果下一个token是乘号，添加后续的因子
        while (lexer.peek().equals("*")) {
            lexer.next();
            term.addFactor(parseFactor());
        }
        return term;
    }

    public Factor parseFactor() {
        //根据具体情况返回Factor
        //先判断是不是数字
        if (lexer.getSign() == 0 || lexer.getSign() == 1) {
            return numPraser();
        } else if (lexer.getSign() == 6) { //再判断是不是变量
            return varParser();
        } else if (lexer.getSign() == 4) { //再判断是不是左括号//表达式因子
            return exprfactorParser();
        } else if (lexer.getSign() == 9) { //再判断是不是sin/cos
            return trigonomParser();
        } else if (lexer.getSign() == 7) { //再判断是不是函数
            return functionParser();
        } else if (lexer.getSign() == 11) { //导数
            return derivativeParser();
        } else if (lexer.getSign() == 5) {
            return null;//内容为空
        } else {
            System.err.println("F**King err from parseFactor");
            return null;
        }
    }

    private Factor functionParser() {
        //先取出函数名
        String funcName = lexer.peek();
        lexer.next();
        //去除左括号
        if (!lexer.peek().equals("(")) {
            System.err.println("F**King not a '('");
        }
        lexer.next();
        //建立一个map
        HashMap<Integer, ItemListManager> funcMap = new HashMap<>();
        int flag = 0;
        //取出括号里的因子并添加到map中
        Factor factor = parseFactor();
        Myfunc myfunc = null;
        if (factor != null) {
            funcMap.put(flag, factor.getItemListManager());
            flag += 1;
            //循环判断下面是不是逗号，如果是逗号就继续取出因子
            while (lexer.peek().equals(",")) {
                lexer.next();
                factor = parseFactor();
                funcMap.put(flag, factor.getItemListManager());
                flag += 1;
            }
            //建立对象和替换形参
            myfunc = new Myfunc(funcName, funcMap);
            //myfunc.setItemListManager(); // getItemListManager()时已经替换了形参
        } else {
            myfunc = new Myfunc(funcName, funcMap); // 无参函数
        }
        //去除右括号
        if (!lexer.peek().equals(")")) {
            System.err.println("F**King not a ')'");
        }
        lexer.next();
        //判断后面是不是乘方 //  乘方其实可以抽象出来,不过这里就不抽象了(懒人)
        if (lexer.getSign() == 3) { //是乘方
            lexer.next();//取出乘方后的指数
            int flag1 = 1;
            if (lexer.peek().equals("-")) {
                flag1 = -1;
                lexer.next();
            } else if (lexer.peek().equals("+")) {
                lexer.next();
            }
            int num = Integer.parseInt(lexer.peek());//如果是乘方，那么就把后面的数字作为指数
            num = num * flag1;
            Factor power = new Power(myfunc, num);
            lexer.next();
            return power;
        } else { //不是乘方
            return myfunc;
        }
    }

    private Factor trigonomParser() {
        String trigonom = lexer.peek();
        lexer.next();
        //去除左括号
        if (!lexer.peek().equals("(")) {
            System.err.println("F**King not a '('");
        }
        lexer.next();
        Factor factor = parseFactor();//取出括号里的因子
        //去除右括号
        if (!lexer.peek().equals(")")) {
            System.err.println("F**King not a ')'");
        }
        lexer.next();
        //判断后面是不是乘方
        if (lexer.getSign() == 3) { //是乘方
            lexer.next();//取出乘方后的指数
            int flag = 1;
            if (lexer.peek().equals("-")) {
                flag = -1;
                lexer.next();
            } else if (lexer.peek().equals("+")) {
                lexer.next();
            }
            //如果是乘方，那么就把后面的数字作为指数
            int num = Integer.parseInt(lexer.peek());
            if (flag == -1) {
                num = -num;
            }
            lexer.next();
            return new Trigonom(trigonom, factor, BigInteger.valueOf(num));
        }

        return new Trigonom(trigonom, factor, BigInteger.valueOf(1));
    }

    private Factor exprfactorParser() {
        lexer.next();
        Factor expr = parseExpr();
        if (!lexer.peek().equals(")")) { // 确保下一个 token 是右括号，然后忽略它
            System.err.println("F**King not a ')'");
        }
        lexer.next();
        if (lexer.getSign() == 3) { //判断后面是不是乘方//是乘方
            lexer.next();//取出乘方后的指数
            int flag = 1;
            if (lexer.peek().equals("-")) {
                flag = -1;
                lexer.next();
            } else if (lexer.peek().equals("+")) {
                lexer.next();
            }
            //如果是乘方，那么就把后面的数字作为指数
            int num = Integer.parseInt(lexer.peek());
            num = num * flag;
            Factor power = new Power(expr, num);
            lexer.next();
            return power;
        } else { //不是乘方
            return expr;
        }
    }

    private Factor numPraser() {
        if (lexer.getSign() == 0) {
            BigInteger num = new BigInteger(lexer.peek());//如果是数字，那么直接返回一个数字因子
            lexer.next();
            return new Number(num);
        } else if (lexer.getSign() == 1) {
            int flag = 0;
            if (lexer.peek().equals("-")) {
                flag = -1;
            } else if (lexer.peek().equals("+")) {
                flag = 1;
            }
            lexer.next();
            BigInteger num = new BigInteger(lexer.peek());//如果是数字，那么直接返回一个数字因子
            num = num.multiply(BigInteger.valueOf(flag));
            lexer.next();
            return new Number(num);
        } else {
            System.err.println("F**King err from numPraser");
            return null;
        }
    }

    private Factor varParser() {
        if (lexer.getSign() == 6) {
            String varStr = lexer.peek();
            Factor varNum = new VarNum(varStr);//新建一个变量因子
            lexer.next();
            //判断后面是不是乘方
            if (lexer.getSign() == 3) { //是乘方
                lexer.next();//取出乘方后的指数
                int flag = 1;
                if (lexer.peek().equals("-")) {
                    flag = -1;
                    lexer.next();
                } else if (lexer.peek().equals("+")) {
                    lexer.next();
                }
                int num = Integer.parseInt(lexer.peek());//如果是乘方，那么就把后面的数字作为指数
                num = num * flag;
                Factor power = new Power(varNum, num);
                lexer.next();
                return power;
            } else { //不是乘方
                return varNum;
            }
        } else {
            System.err.println("F**King err from varParser");
            return null;
        }
    }

    public Factor derivativeParser() {
        String derStr = lexer.peek();
        lexer.next(); // 取出der
        Factor factor = parseFactor();//取出括号里的因子
        ItemListManager itemListManager = factor.getItemListManager();//取出因子的itemListManager
        Factor differ = new Differ(itemListManager,derStr);//新建一个求导因子
        return differ;
    }

}
