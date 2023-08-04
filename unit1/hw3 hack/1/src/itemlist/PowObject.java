package itemlist;

import java.math.BigInteger;

public class PowObject implements Comparable<PowObject> {
    private ItemListManager itemListManager;
    private BigInteger pow;
    private int trigonomType;//0:sin,1:cos

    //构造方法
    public PowObject(ItemListManager itemListManager, BigInteger pow,int trigonomType) {
        this.itemListManager = itemListManager;
        this.pow = pow;
        this.trigonomType = trigonomType;
    }

    //读写方法
    //读取底数表达式
    public ItemListManager getItemListManager() {
        return this.itemListManager;
    }

    //写入底数表达式
    public void setItemListManager(ItemListManager itemListManager) {
        this.itemListManager = itemListManager;
    }

    //读取指数
    public BigInteger getPow() {
        return this.pow;
    }

    //写入指数
    public void setPow(BigInteger pow) {
        this.pow = pow;
    }

    //ToString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (trigonomType == 0) {
            sb.append("sin");
        } else if (trigonomType == 1) {
            sb.append("cos");
        }
        //如果底数表达式是一个单项式,则不需要加括号
        if (isExpr()) {
            sb.append("((");
            sb.append(itemListManager.toString().equals("") ? "1" : itemListManager.toString());
            sb.append("))");
        } else {
            sb.append("(");
            sb.append(itemListManager.toString().equals("") ? "1" : itemListManager.toString());
            sb.append(")");
        }
        if (pow.equals(BigInteger.valueOf(0))) { //TODO:测试这里会不会出问题
            return "";
        }
        if (!pow.equals(BigInteger.valueOf(1))) {
            sb.append("**");
            sb.append(pow.toString());
        }

        return sb.toString();
    }

    @Override
    public int compareTo(PowObject o2) {
        //先比较指数,再比较表达式
        int result = this.pow.compareTo(o2.getPow());//先比较指数,指数大的大
        if (result == 0) {
            result = this.itemListManager.compareTo(o2.getItemListManager());
        }
        return result;
    }

    public PowObject copy() {
        return new PowObject(this.itemListManager.copy(), this.pow, this.trigonomType);
    }

    private boolean isExpr() {
        //判断是不是表达式因子
        //1.判断是不是单项式
        if (itemListManager.getManagerSize() > 1) {
            return true;
        }
        //2.判断单项式是不是常量因子
        ItemList itemList = itemListManager.getItemList(0);
        int flag = 0;
        if (!itemList.getCoefficient().equals(BigInteger.valueOf(0)) &&
                !itemList.getCoefficient().equals(BigInteger.valueOf(1))) {
            flag++;
        }
        if (!itemList.getxExp().equals(BigInteger.valueOf(0))) {
            flag++;
        }
        if (!itemList.getyExp().equals(BigInteger.valueOf(0))) {
            flag++;
        }
        if (!itemList.getzExp().equals(BigInteger.valueOf(0))) {
            flag++;
        }
        flag = flag + itemList.getSinList().getSize() + itemList.getCosList().getSize();
        if (flag > 1) {
            return true;
        } else {
            return false;
        }
    }
}
