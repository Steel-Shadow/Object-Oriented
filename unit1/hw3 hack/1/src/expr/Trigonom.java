package expr;

import itemlist.ItemListManager;
import itemlist.TrigonomList;

import java.math.BigInteger;

public class Trigonom implements Factor {

    private ItemListManager itemListManager = new ItemListManager();
    private int trigonomType;

    //构造函数
    public Trigonom(String trigonom, Factor factor, BigInteger pow) {
        if (trigonom.equals("sin")) {
            trigonomType = 0;
        } else if (trigonom.equals("cos")) {
            trigonomType = 1;
        }
        TrigonomList trigonomList = new TrigonomList(trigonomType);
        trigonomList.addPowObject(factor.getItemListManager(), pow);
        if (trigonomType == 0) { //sin
            itemListManager.addItem(
                    BigInteger.valueOf(1),
                    BigInteger.valueOf(0),
                    BigInteger.valueOf(0),
                    BigInteger.valueOf(0),
                    trigonomList,
                    new TrigonomList(1));
        } else if (trigonomType == 1) { //cos
            itemListManager.addItem(
                    BigInteger.valueOf(1),
                    BigInteger.valueOf(0),
                    BigInteger.valueOf(0),
                    BigInteger.valueOf(0),
                    new TrigonomList(0),
                    trigonomList);
        }
    }

    @Override
    public ItemListManager getItemListManager() {
        return itemListManager;
    }
}
