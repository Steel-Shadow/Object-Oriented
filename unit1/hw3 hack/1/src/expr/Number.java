package expr;

import itemlist.ItemListManager;
import itemlist.TrigonomList;

import java.math.BigInteger;

public class Number implements Factor {
    private final BigInteger num;
    private ItemListManager itemListManager;

    public Number(BigInteger num) {
        this.num = num;
        this.itemListManager = new ItemListManager();
        this.itemListManager.addItem(
                num,
                BigInteger.valueOf(0),
                BigInteger.valueOf(0),
                BigInteger.valueOf(0),
                new TrigonomList(0),
                new TrigonomList(1));
    }

    @Override
    public ItemListManager getItemListManager() {
        return this.itemListManager;
    }

}
