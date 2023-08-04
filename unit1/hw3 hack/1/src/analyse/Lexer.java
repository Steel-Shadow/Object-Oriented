package analyse;

public class Lexer {
    private final String strIn;//输入字符串
    private int pos = 0;
    private String strOut; //暂存输出字符串
    private Integer signNum;//判断当前是何种类型的字符串

    public Lexer(String strIn) {
        this.strIn = strIn;
        this.next();
    }

    public String peek() { //获取串串
        return this.strOut;
    }

    public Integer getSign() { //获取类型
        return signNum;
    }

    public String next() {
        if (pos >= strIn.length()) {
            strOut = "";//超出长度后置空
            signNum = -1;
            return null;
        }
        char c = getChar(pos);
        //去除空白符
        while (c == ' ' || c == '\t') {
            ++pos;
            c = getChar(pos);
        }
        //如果空字符退出
        if (c == '\0') {
            strOut = "";
            signNum = -1;
            return null;
        }
        if (Character.isDigit(c)) { //不含正负号的数字
            strOut = getNum();
        } else if ("+-".indexOf(c) != -1) { //第一个字符是加减号
            strOut = String.valueOf(getChar(pos));
            ++pos;
            signNum = 1;
        } else if (c == '*') {    //第一个字符是乘号
            c = strIn.charAt(pos + 1);
            if (c == '*') {  //乘方
                pos += 2;
                strOut = "**";
                signNum = 3;
            } else {
                strOut = String.valueOf(getChar(pos)); //乘法
                ++pos;
                signNum = 2;
            }
        } else if (c == '(') {
            strOut = String.valueOf(getChar(pos)); //左括号
            ++pos;
            signNum = 4;
        } else if (c == ')') {
            strOut = String.valueOf(getChar(pos));//右括号
            ++pos;
            signNum = 5;
        } else {
            nextSecond(c);
        }
        return null;
    }

    private String nextSecond(char c) { //第二层判断
        if ("xyz".indexOf(c) != -1) {
            strOut = String.valueOf(getChar(pos));//变量
            ++pos;
            signNum = 6;
        } else if ("fgh".indexOf(c) != -1) {
            strOut = String.valueOf(getChar(pos));//函数
            ++pos;
            signNum = 7;
        } else if (c == ',') {
            strOut = String.valueOf(getChar(pos));//逗号
            ++pos;
            signNum = 8;
        } else if ("sc".indexOf(c) != -1) {
            strOut = getTrigonom();//三角函数
        } else if (c == '=') {
            strOut = String.valueOf(getChar(pos));
            ++pos;
            signNum = 10;
        } else if (c == 'd') {  //求导因子
            ++pos;
            char tmpc = c;
            tmpc = getChar(pos);
            strOut = "d" + tmpc;
            ++pos;
            signNum = 11;
        }
        return strOut;
    }

    private String getNum() {
        //用于从输入字符串中提取数字，利用 StringBuilder 逐个字符构造数字，并返回字符串形式的结果。
        StringBuilder sb = new StringBuilder();
        char c = getChar(pos);
        if ("+-".indexOf(c) != -1) { //正负号也包含其中
            sb.append(c);
            ++pos;
            c = getChar(pos);
        }

        while (pos < strIn.length() && Character.isDigit(c)) {
            sb.append(c);
            ++pos;
            c = getChar(pos);
        }
        signNum = 0;//数字标识
        return sb.toString();
    }

    private String getTrigonom() {
        StringBuilder sb = new StringBuilder();
        char c = getChar(pos);
        char c1 = getChar(pos + 1);
        char c2 = getChar(pos + 2);
        if (c == 's' && c1 == 'i' && c2 == 'n') {
            sb.append("sin");
            pos += 3;
        } else if (c == 'c' && c1 == 'o' && c2 == 's') {
            sb.append("cos");
            pos += 3;
        } else {
            signNum = -1;
            return "";
        }
        signNum = 9;
        return sb.toString();
    }

    //防止出现空指针异常
    private char getChar(int pos) {
        if (pos < strIn.length()) {
            return strIn.charAt(pos);
        }
        return '\0';
    }

}
