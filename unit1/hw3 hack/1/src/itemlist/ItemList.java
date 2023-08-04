package itemlist;

import java.math.BigInteger;

public class ItemList {
    private BigInteger coefficient;
    private BigInteger xxExp;
    private BigInteger yyExp;
    private BigInteger zzExp;

    private TrigonomList sinList;
    private TrigonomList cosList;

    public ItemList(BigInteger coefficient, BigInteger xxExp,
                    BigInteger yyExp, BigInteger zzExp,
                    TrigonomList sinList, TrigonomList cosList) {
        this.coefficient = coefficient;
        this.xxExp = xxExp;
        this.yyExp = yyExp;
        this.zzExp = zzExp;
        this.sinList = sinList;
        this.cosList = cosList;
    }

    public ItemList() {
        this.coefficient = BigInteger.valueOf(0);
        this.xxExp = BigInteger.valueOf(0);
        this.yyExp = BigInteger.valueOf(0);
        this.zzExp = BigInteger.valueOf(0);
        this.sinList = new TrigonomList(0);
        this.cosList = new TrigonomList(1);
    }

    public ItemList(BigInteger coeff, BigInteger x, BigInteger y, BigInteger z) {
        this.coefficient = coeff;
        this.xxExp = x;
        this.yyExp = y;
        this.zzExp = z;
        this.sinList = new TrigonomList(0);
        this.cosList = new TrigonomList(1);
    }

    //读取每一位
    public BigInteger getCoefficient() {
        return this.coefficient;
    }

    public BigInteger getxExp() {
        return this.xxExp;
    }

    public BigInteger getyExp() {
        return yyExp;
    }

    public BigInteger getzExp() {
        return zzExp;
    }

    public TrigonomList getSinList() {
        return sinList;
    }

    public TrigonomList getCosList() {
        return cosList;
    }

    //读取sin和cos中的每一项
    //返回sin的TrigonomList
    public TrigonomList getSinTrigonomList() {
        return sinList;
    }

    //返回cos的TrigonomList
    public TrigonomList getCosTrigonomList() {
        return cosList;
    }

    //写入每一位(暂时只需要写入系数)
    public void setCoefficient(BigInteger coefficient) {
        this.coefficient = coefficient;
    }

    public void setxExp(BigInteger xxExp) {
        this.xxExp = xxExp;
    }

    public void setyExp(BigInteger yyExp) {
        this.yyExp = yyExp;
    }

    public void setzExp(BigInteger zzExp) {
        this.zzExp = zzExp;
    }

    //写入sin和cos中的每一项
    public void setSinTrigonomList(TrigonomList sinList) {
        this.sinList = sinList;
    }

    public void setCosTrigonomList(TrigonomList cosList) {
        this.cosList = cosList;
    }

    //化简三角函数
    public void simplifyTrig() {
        //1.自我化简,主要是合并同幂次和去除零
        coefficient = coefficient.multiply(BigInteger.valueOf(sinList.merge()));
        coefficient = coefficient.multiply(BigInteger.valueOf(cosList.merge()));

    }


    //toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int flag = 0;
        if (coefficient.signum() == 0) {
            return "0";
        } else {
            if (coefficient.compareTo(BigInteger.valueOf(1)) == 0) { //等于0说明是1
                flag = 1;
            } else if (coefficient.compareTo(BigInteger.valueOf(-1)) == 0) {
                sb.append("-");
                flag = 1;
            } else {
                sb.append(coefficient);
            }
        }

        if (xxExp.signum() != 0) {
            if (flag == 0) { sb.append("*"); }
            flag = 0;
            if (xxExp.compareTo(BigInteger.valueOf(1)) == 0) { sb.append("x"); } else {
                sb.append("x**");
                sb.append(xxExp);
            }
        }

        if (yyExp.signum() != 0) {
            if (flag == 0) { sb.append("*"); }
            flag = 0;
            if (yyExp.compareTo(BigInteger.valueOf(1)) == 0) {
                sb.append("y");
            } else {
                sb.append("y**");
                sb.append(yyExp);
            }
        }

        if (zzExp.signum() != 0) {
            if (flag == 0) { sb.append("*"); }
            flag = 0;
            if (zzExp.compareTo(BigInteger.valueOf(1)) == 0) { sb.append("z"); } else {
                sb.append("z**");
                sb.append(zzExp);
            }
        }
        //sin和cos的Tostring
        if (sinList.getSize() != 0 && (!sinList.toString().equals(""))) {
            if (flag == 0) {
                sb.append("*");
            }
            flag = 0;
            sb.append(sinList.toString());
        }
        if (cosList.getSize() != 0 && (!cosList.toString().equals(""))) {
            if (flag == 0) {
                sb.append("*");
            }
            flag = 0;
            sb.append(cosList.toString());
        }

        if (flag == 1) {
            sb.append("1");
        }
        return sb.toString();
    }

    public ItemList copy() {
        ItemList copy = new ItemList();
        copy.setCoefficient(this.getCoefficient());
        copy.setxExp(this.getxExp());
        copy.setyExp(this.getyExp());
        copy.setzExp(this.getzExp());
        copy.setSinTrigonomList(this.getSinTrigonomList().copy());
        copy.setCosTrigonomList(this.getCosTrigonomList().copy());
        return copy;
    }
}
