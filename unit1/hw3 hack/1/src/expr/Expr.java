package expr;

import itemlist.ItemListManager;

import java.math.BigInteger;

public class Expr implements Factor {  //private final HashSet<Term> terms;
    private ItemListManager itemListManager;
    private int flag = 0;//标识符,是否需要乘以-1

    public Expr() {
        //this.terms = new HashSet<>();
        this.itemListManager = new ItemListManager();
    }

    public void addTerm(Term term, int flag) { //添加项
        //this.terms.add(term);
        if (flag != -1) {
            this.itemListManager = this.itemListManager.add(term.getItemListManager());
        } else {
            this.itemListManager = this.itemListManager.sub(term.getItemListManager());
        }

    }

    @Override
    public ItemListManager getItemListManager() {
        this.itemListManager.mergeItemLists();
        return this.itemListManager;
    }

    public void setFlag(int flag) { //设置标识符
        this.flag = flag;
    }

    //整理表达式
    public void arrange() { //整理表达式
        if (this.flag == 1) {
            this.itemListManager.multiplyCoefficient(BigInteger.valueOf(-1));
            flag = 0;
        }
        //循环3次
        for (int i = 0; i < 3; i++) {
            //this.itemListManager.mergeItemLists();
            this.itemListManager.memrgeFinal();
        }
    }

    public String toString() {
        return this.itemListManager.toString();
    }

}
