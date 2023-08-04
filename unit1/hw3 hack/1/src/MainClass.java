import analyse.Lexer;
import analyse.Parser;
import expr.Expr;
import itemlist.MyfuncList;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        //获取输入
        Scanner scan = new Scanner(System.in);
        //读入自定义函数
        int n = scan.nextInt();//读入函数个数
        scan.nextLine();

        //存储函数名和函数体
        for (int i = 0; i < n; i++) {
            Map<String, String> funcMap = new HashMap<>();
            String strIn = scan.nextLine();
            FuncParser parser = new FuncParser(new Lexer(strIn));
            funcMap.put(parser.getFuncName(), parser.getFuncBody());
            MyfuncList.addItem(funcMap);
            //System.out.println(parser.getFuncName());//测试用
            //System.out.println(parser.getFuncBody());//测试用
        }
        //将funcMap中的函数添加到itemList中


        String strIn = scan.nextLine();
        Parser parser = new Parser(new Lexer(strIn));
        Expr expr = parser.parseExpr();
        expr.arrange();
        System.out.println(expr.toString());//输出expr
        //        String strIn;
        //        while (scan.hasNextLine()) { //循环读取输入
        //            //如果输入为空，就跳过
        //            strIn = scan.nextLine();
        //            if (strIn.equals("")) {
        //                continue;
        //            }
        //            //建立Lexer和Parser
        //            analyse.Parser parser = new analyse.Parser(new analyse.Lexer(strIn));
        //
        //            //获得表达式
        //            Expr expr = parser.parseExpr();
        //            System.out.println(expr.toString());//输出expr
        //        }
        //String strIn = scan.nextLine();

        //            Expr expr = parser.parseExpr();
        //            System.out.println(expr.toString());//输出expr

    }
}