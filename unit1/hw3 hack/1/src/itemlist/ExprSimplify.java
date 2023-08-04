package itemlist;

import java.math.BigInteger;
import java.util.ArrayList;

public class ExprSimplify {
    //ItemListManager itemListManager;
    private boolean errorMe = false;
    private PowObject sinPowObject = null;
    private PowObject cosPowObject = null;

    //构造方法
    public ExprSimplify() {
    }

    //get errorMe
    public boolean getErrorMe() {
        return this.errorMe;
    }

    //get sinPowObject
    public PowObject getSinPowObject() {
        return this.sinPowObject;
    }

    //get cosPowObject
    public PowObject getCosPowObject() {
        return this.cosPowObject;
    }

    //判断有无重复的项
    public boolean judgeEqual(ArrayList<ItemList> itemLists) {
        for (int i = 0; i < itemLists.size(); i++) { //必须确保没有每个itemlist里sin和cos都没有重复的项
            //分别判断sin和cos中是否有重复的项,有的话报错且return
            if (itemLists.get(i).getSinList().hasSameItem() == 1) {
                System.err.println("Error:有重复的项");
                return false;
            }
        }
        return true;
    }

    //判断是否只有一项的两个三角函数不同
    public boolean isDiffTrigonom(int i, int j, ArrayList<ItemList> itemLists) {
        if (!judgeEqual(itemLists)) {
            return false;
        }
        sinPowObject = null;
        cosPowObject = null;
        if (i == j) {
            return false;
        }
        //1.先比较底数和xyz的指数,相同的话为true
        if (itemLists.get(i).getCoefficient().
                compareTo(itemLists.get(j).getCoefficient()) == 0
                && itemLists.get(i).getxExp().compareTo(itemLists.get(j).getxExp()) == 0
                && itemLists.get(i).getyExp().compareTo(itemLists.get(j).getyExp()) == 0
                && itemLists.get(i).getzExp().
                compareTo(itemLists.get(j).getzExp()) == 0) {
            //2.如果siniSize和sinjSize相差为1,cosiSize和cosjSize相差为1,或者相反
            if (!getSizeDiff(i, j, itemLists)) {
                return false;
            }
            //3.如果sin和cos的不同的个数为2
            sinPowObject = judgeDifferentOfSinAndCos(itemLists.get(i),
                    itemLists.get(j), 0);
            if (errorMe) { //出现两个以上不同的项
                return false;
            }
            //且cos和sin不同各一项
            cosPowObject = judgeDifferentOfSinAndCos(itemLists.get(i),
                    itemLists.get(j), 1);
            if (errorMe) { //出现两个以上不同的项
                return false;
            }
            return sinPowObject != null && cosPowObject != null;
        }
        return false;
    }

    //判断能否利用sin和cos的平方和公式化简
    //    @SuppressWarnings("checkstyle:ParenPad")
    public boolean canQuadraticSum(PowObject sinPowObject, PowObject cosPowObject) {
        if (sinPowObject.getPow().equals(cosPowObject.getPow())) {
            if (sinPowObject.getPow().equals(BigInteger.valueOf(2))) {
                if (sinPowObject.getItemListManager().
                        compareTo(cosPowObject.getItemListManager()) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean getSizeDiff(int i, int j, ArrayList<ItemList> itemLists) {
        int siniSize = itemLists.get(i).getSinList().getSize();
        int sinjSize = itemLists.get(j).getSinList().getSize();
        int cosiSize = itemLists.get(i).getCosList().getSize();
        int cosjSize = itemLists.get(j).getCosList().getSize();
        if ((siniSize - sinjSize == 1 && cosjSize - cosiSize == 1) ||
                (siniSize - sinjSize == -1 && cosjSize - cosiSize == -1)) {
            return true;
        }
        return false;
    }

    private PowObject judgeDifferentOfSinAndCos(ItemList itemList1,ItemList itemList2,
                                                int sinorcos) {
        errorMe = false;
        //SinOrCos = 0表示sin,1表示cos
        int tmp = 0;//记录i和j对应的项的sin和cos的不同的个数,比较sin和cos的不同的个数
        PowObject powObject = null;
        ItemList itemListTmp1 = null;
        ItemList itemListTmp2 = null;
        if (sinorcos == 0) { //多的循环比较少的,这样返回的才是真实的不同数目
            if (itemList1.getSinList().getSize() > itemList2.getSinList().getSize()) {
                itemListTmp1 = itemList1;
                itemListTmp2 = itemList2;
            } else {
                itemListTmp1 = itemList2;
                itemListTmp2 = itemList1;
            }
            for (int k = 0; k < itemListTmp1.getSinList().getSize(); k++) {
                int flag = 0;
                for (int l = 0; l < itemListTmp2.getSinList().getSize(); l++) {
                    if (itemListTmp1.getSinList().getTrigonom(k).
                            compareTo(itemListTmp2.getSinList().
                                    getTrigonom(l)) == 0) { //相同
                        flag = 1;
                    }
                }
                if (flag == 0) { //不同
                    tmp++;
                    if (tmp > 1) {
                        break;
                    } else {
                        powObject = itemListTmp1.getSinList().getTrigonom(k);
                    }
                }
            }

            if (tmp > 1) {
                errorMe = true;
                return null;
            }
            errorMe = false;
            return powObject;
        } else {
            return judge2(itemList1, itemList2);
        }
    }

    private PowObject judge2(ItemList itemList1, ItemList itemList2) {
        PowObject powObject = null;
        int tmp = 0;
        ItemList itemListTmp1 = null;
        ItemList itemListTmp2 = null;
        if (itemList1.getCosList().getSize() > itemList2.getCosList().getSize()) {
            itemListTmp1 = itemList1;
            itemListTmp2 = itemList2;
        } else {
            itemListTmp1 = itemList2;
            itemListTmp2 = itemList1;
        }
        for (int k = 0; k < itemListTmp1.getCosList().getSize(); k++) {
            int flag = 0;
            for (int l = 0; l < itemListTmp2.getCosList().getSize(); l++) {
                if (itemListTmp1.getCosList().getTrigonom(k).
                        compareTo(itemListTmp2.getCosList().
                                getTrigonom(l)) == 0) {
                    flag = 1;
                }
            }
            if (flag == 0) {
                tmp++;
                if (tmp > 1) {
                    break;
                } else {
                    powObject = itemListTmp1.getCosList().getTrigonom(k);
                }
            }
        }
        if (tmp > 1) {
            errorMe = true;
            return null;
        }
        errorMe = false;
        return powObject;
    }
}
