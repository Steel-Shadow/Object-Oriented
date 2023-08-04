package expr;

import itemlist.ItemListManager;

public class Power implements Factor {

    private ItemListManager itemListManager;

    public Power(Factor factor, int power) { //未实现
        this.itemListManager = new ItemListManager();
        this.itemListManager = factor.getItemListManager().pow(power);
    }

    @Override
    public ItemListManager getItemListManager() {
        return itemListManager;
    }
}
