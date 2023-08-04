package expr;

import itemlist.ItemList;
import itemlist.ItemListManager;
import itemlist.TrigonomList;

import java.math.BigInteger;

public class Term {
    private ItemListManager itemListManager;
    private int flag = 0;//标识符,是否需要乘以-1

    public Term() {
        this.itemListManager = new ItemListManager();
        this.itemListManager.addItem(new ItemList(
                BigInteger.valueOf(1),
                BigInteger.valueOf(0),
                BigInteger.valueOf(0),
                BigInteger.valueOf(0),
                new TrigonomList(0),
                new TrigonomList(1)));//先初始化一个1

    }

    //添加一个因子
    public void addFactor(Factor factor) { //添加因子
        this.itemListManager = this.itemListManager.multiply(factor.getItemListManager());//把因子乘在一起
    }

    public void setFlag(int flag) { //设置标识符
        this.flag = flag;
    } //设置标识符

    //整理表达式
    public void arrange() { //整理表达式
        if (this.flag == 1) {
            this.itemListManager.multiplyCoefficient(BigInteger.valueOf(-1));
            flag = 0;
        }
        this.itemListManager.mergeItemLists();
    }

    //实现getItemListManager
    public ItemListManager getItemListManager() {
        arrange();
        return this.itemListManager;
    }
}
