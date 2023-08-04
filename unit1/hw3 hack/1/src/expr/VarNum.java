package expr;

import itemlist.ItemListManager;

import java.math.BigInteger;

public class VarNum implements Factor {
    private final String varnum;
    private ItemListManager itemListManager;

    public VarNum(String varnum) {
        this.varnum = varnum;
        itemListManager = new ItemListManager();
        switch (varnum) {
            case "x":
                itemListManager.addItem(
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(0),
                        BigInteger.valueOf(0));
                break;
            case "y":
                itemListManager.addItem(
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(0),
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(0));
                break;
            case "z":
                itemListManager.addItem(
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(0),
                        BigInteger.valueOf(0),
                        BigInteger.valueOf(1));
                break;
            default:
                System.err.println("Error: varNum.java: varnum is not x, y or z");
                break;
        }
    }

    @Override
    public ItemListManager getItemListManager() {
        return itemListManager;
    }
}
