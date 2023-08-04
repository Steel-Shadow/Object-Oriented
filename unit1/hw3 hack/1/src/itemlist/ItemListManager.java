package itemlist;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ItemListManager implements Iterable<ItemList>, Comparable<ItemListManager> {
    private ArrayList<ItemList> itemLists;
    private boolean errorMe = false;
    private ExprSimplify exprSimplify = new ExprSimplify();

    public ItemListManager() {
        itemLists = new ArrayList<ItemList>();
    }

    //获取size
    public int getManagerSize() {
        mergeItemLists();
        return itemLists.size();
    }

    //获取ItemList
    public ItemList getItemList(int index) {
        mergeItemLists();
        return itemLists.get(index);
    }

    //增
    public void addItem(ItemList itemList) {
        itemLists.add(itemList.copy());
    }

    public void addItem(
        BigInteger coefficient, BigInteger xxExp, BigInteger yyExp, BigInteger zzExp,
        TrigonomList sinList, TrigonomList cosList) {
        itemLists.add(new ItemList(coefficient, xxExp, yyExp, zzExp, sinList, cosList));
    }

    public void addItem(BigInteger coefficient, BigInteger xxExp,
                        BigInteger yyExp, BigInteger zzExp) {
        itemLists.add(new ItemList(coefficient, xxExp, yyExp, zzExp,
                new TrigonomList(0),
                new TrigonomList(1)));
    }

    //计算方法部分:两个多项式相加
    public ItemListManager add(ItemListManager itemListManager) {
        ItemListManager itemListManager1 = new ItemListManager();
        for (ItemList itemList : this) {
            itemListManager1.addItem(itemList.copy());
        }
        for (ItemList itemList : itemListManager) {
            itemListManager1.addItem(itemList.copy());
        }
        itemListManager1.sortItemLists();
        itemListManager1.mergeItemLists();
        return itemListManager1;
    }

    //两个多项式相减
    public ItemListManager sub(ItemListManager itemListManager) {
        ItemListManager itemListManager1 = new ItemListManager();
        for (ItemList itemList : this) {
            itemListManager1.addItem(itemList.copy());
        }
        itemListManager.multiplyCoefficient(BigInteger.valueOf(-1));
        for (ItemList itemList : itemListManager) {
            itemListManager1.addItem(itemList.copy());
        }
        itemListManager1.sortItemLists();
        itemListManager1.mergeItemLists();
        return itemListManager1;
    }

    //两个多项式相乘
    public ItemListManager multiply(ItemListManager itemListManager) {
        ItemListManager itemListManager1 = new ItemListManager();
        for (ItemList itemList1 : this.copy()) {
            for (ItemList itemList2 : itemListManager.copy()) {
                itemListManager1.addItem(
                        itemList1.getCoefficient().multiply(itemList2.getCoefficient()),
                        //
                        itemList1.getxExp().add(itemList2.getxExp()),
                        itemList1.getyExp().add(itemList2.getyExp()),
                        itemList1.getzExp().add(itemList2.getzExp()),
                        itemList1.getSinList().multiply(itemList2.getSinList()),
                        itemList1.getCosList().multiply(itemList2.getCosList()));
                itemListManager1.mergeItemLists();
            }
        }
        itemListManager1.sortItemLists();
        itemListManager1.mergeItemLists();
        return itemListManager1;
    }

    //多项式的乘方
    public ItemListManager pow(int n) {
        ItemListManager itemListManager1 = new ItemListManager();
        if (n > 0) {
            itemListManager1 = itemListManager1.add(this.copy());
        } else {
            ItemListManager itemListManager2 = new ItemListManager();//否则返回1;
            itemListManager2.addItem(
                    new ItemList(
                            new BigInteger("1"),
                            new BigInteger("0"),
                            new BigInteger("0"),
                            new BigInteger("0"),
                            new TrigonomList(0),
                            new TrigonomList(1)));
            return itemListManager2.copy();
        }

        //循环n-1次
        for (int i = 1; i < n; i++) {
            itemListManager1 = itemListManager1.multiply(this.copy());
        }
        itemListManager1.mergeItemLists();
        return itemListManager1.copy();
    }

    //删
    public void removeItem(ItemList itemList) {
        itemLists.remove(itemList);
    }

    //tostring
    public String toString() {
        mergeItemLists();
        String str = "";
        int flag = 0;
        for (ItemList itemList : itemLists) {
            if (flag == 0) { //如果是第一个元素，就不加符号
                str += itemList.toString();
                flag = 1;
            } else {
                //如果itemList的系数为正数，就在前面加一个加号
                if (itemList.getCoefficient().signum() > 0) {
                    str += "+" + itemList.toString();
                } else {
                    str += itemList.toString();
                }
            }
        }
        return str;
    }

    //排序,先按照x的指数从小到大,再按照y的指数从小到大,再按照z的指数从小到大,最后按照系数从小到大
    public void sortItemLists() {
        removeZeroItem();
        Collections.sort(itemLists, new Comparator<ItemList>() {
            public int compare(ItemList itemList1, ItemList itemList2) {
                if (itemList1.getxExp().compareTo(itemList2.getxExp()) == 0) {
                    if (itemList1.getyExp().compareTo(itemList2.getyExp()) == 0) {
                        if (itemList1.getzExp().compareTo(itemList2.getzExp()) == 0) {
                            if (itemList1.getCoefficient().
                                    compareTo(itemList2.getCoefficient()) == 0) { //hk2新增
                                if (itemList1.getSinList().
                                        compareTo(itemList2.getSinList()) == 0) {
                                    return itemList1.
                                            getCosList().compareTo(itemList2.getCosList());
                                }
                                return itemList1.getSinList().compareTo(itemList2.getSinList());
                            }
                            return itemList1.getCoefficient().compareTo(itemList2.getCoefficient());
                        }
                        return itemList1.getzExp().compareTo(itemList2.getzExp());
                    }
                    return itemList1.getyExp().compareTo(itemList2.getyExp());
                }
                return itemList1.getxExp().compareTo(itemList2.getxExp());
            }
        });
    }

    //去除系数为0的项
    public int removeZeroItem() {
        for (int i = 0; i < itemLists.size(); i++) {
            if (itemLists.get(i).getCoefficient().signum() == 0) {
                itemLists.remove(i);
                i--;
            }
        }
        //如果没有项了,就添加一个系数为0的项
        if (itemLists.size() == 0) {
            itemLists.add(new ItemList(
                    new BigInteger("0"),
                    new BigInteger("0"),
                    new BigInteger("0"),
                    new BigInteger("0"),
                    new TrigonomList(0),
                    new TrigonomList(1)));
            return 0;//返回0表示没有项了
        }
        return 1;
    }

    //先排序,然后合并指数相同的项为系数相加,然后删除系数为0的项
    public void mergeItemLists() {  //合并指数相同的项
        sortItemLists();
        for (int i = 0; i < itemLists.size() - 1; i++) {
            if (itemLists.get(i).getCoefficient().signum() == 0) {
                itemLists.remove(i);
                i--;
                continue;
            } else {
                if (itemLists.get(i).getxExp().compareTo(itemLists.get(i + 1).getxExp()) == 0
                        && itemLists.get(i).getyExp().compareTo(itemLists.get(i + 1).getyExp()) == 0
                        && itemLists.get(i).getzExp().compareTo(itemLists.get(i + 1).getzExp()) == 0
                        && itemLists.get(i).getSinList().
                        compareTo(itemLists.get(i + 1).getSinList()) == 0
                        && itemLists.get(i).getCosList().compareTo(itemLists.get(i + 1).
                        getCosList()) == 0) {
                    itemLists.get(i).setCoefficient(
                            itemLists.get(i).getCoefficient().
                                    add(itemLists.get(i + 1).getCoefficient()));
                    itemLists.remove(i + 1);
                    i--;
                    continue;
                }
            }
            //修复了如果i为-1的时候会出现数组越界的bug
            //ItemList itemList =  itemLists.get(i);
            //itemList.simplifyTrig();
        }
        //遍历itemList,然后itemList.simplifyTrig();
        //for (ItemList itemList : itemLists) {
        //      itemList.simplifyTrig();
        //  }
        //项间化简, 如果为空,则新建一个0项
        if (itemLists.size() == 0) {
            ItemList itemList = new ItemList();
            itemLists.add(itemList);
        }
        //循环10次
        //for (int i = 0; i < 10; i++) {
        //   simplifyItemListsSquare();
        //}
    }

    public void memrgeFinal() {
        mergeItemLists();
        for (int i = 0; i < itemLists.size(); i++) {
            itemLists.get(i).simplifyTrig();
        }

        for (int i = 0; i < 2; i++) {
            mergeItemLists();
            simplifyItemListsSquare();
        }
        mergeItemLists();
    }

    //项间化简
    public void simplifyItemListsSquare() { //化简平方和
        for (int i = 0; i < itemLists.size(); i++) {
            for (int j = 0; j < itemLists.size(); j++) { //i = j的时候不用比较
                if (exprSimplify.isDiffTrigonom(i,j,itemLists)) {
                    PowObject sinPowObject = exprSimplify.getSinPowObject();
                    PowObject cosPowObject = exprSimplify.getCosPowObject();
                    if (exprSimplify.canQuadraticSum(sinPowObject,cosPowObject)) {
                        itemLists.remove(j);
                        itemLists.get(i).getSinList().removePowObject(sinPowObject);
                        itemLists.get(i).getCosList().removePowObject(cosPowObject);
                        i--;
                        break;
                    }
                }
            }
        }
    }

    //将每一项的系数乘以一个数
    public void multiplyCoefficient(BigInteger coefficient) {
        for (ItemList itemList : itemLists) {
            itemList.setCoefficient(itemList.getCoefficient().multiply(coefficient));
        }
        mergeItemLists();
    }

    @Override
    public Iterator<ItemList> iterator() {
        return itemLists.iterator();
    }

    @Override
    public int compareTo(ItemListManager o2) {
        this.mergeItemLists();
        o2.mergeItemLists();
        this.sortItemLists();
        o2.sortItemLists();
        if (this.itemLists.size() > o2.itemLists.size()) { //如果项数不同,就比较项数,项数多的大
            return 1;
        } else if (this.itemLists.size() < o2.itemLists.size()) {
            return -1;
        } else {
            //for循环比较每一项的x的指数
            for (int i = 0; i < this.itemLists.size(); i++) {
                if (this.itemLists.get(i).getxExp().compareTo(o2.itemLists.get(i).getxExp()) > 0) {
                    //先比较x的指数
                    return 1;
                } else if (this.itemLists.get(i).getxExp().
                        compareTo(o2.itemLists.get(i).getxExp()) < 0) {
                    return -1;
                }
                if (this.itemLists.get(i).getyExp().compareTo(o2.itemLists.get(i).getyExp()) > 0) {
                    //再比较y的指数
                    return 1;
                } else if (this.itemLists.get(i).getyExp().
                        compareTo(o2.itemLists.get(i).getyExp()) < 0) {
                    return -1;
                }
                if (this.itemLists.get(i).getzExp().
                        compareTo(o2.itemLists.get(i).getzExp()) > 0) { //再比较z的指数
                    return 1;
                } else if (this.itemLists.get(i).getzExp().
                        compareTo(o2.itemLists.get(i).getzExp()) < 0) {
                    return -1;
                }
                if (this.itemLists.get(i).getCoefficient().
                        compareTo(o2.itemLists.get(i).getCoefficient()) > 0) { //再比较系数
                    return 1;
                } else if (this.itemLists.get(i).getCoefficient().
                        compareTo(o2.itemLists.get(i).getCoefficient()) < 0) {
                    return -1;
                }
                //比较sin,cos
                if (this.itemLists.get(i).getSinList().
                        compareTo(o2.itemLists.get(i).getSinList()) > 0) { //先比较sin
                    return 1;
                } else if (this.itemLists.get(i).getSinList().
                        compareTo(o2.itemLists.get(i).getSinList()) < 0) {
                    return -1;
                }
                if (this.itemLists.get(i).getCosList().
                        compareTo(o2.itemLists.get(i).getCosList()) > 0) { //再比较cos
                    return 1;
                } else if (this.itemLists.get(i).getCosList().
                        compareTo(o2.itemLists.get(i).getCosList()) < 0) {
                    return -1;
                }
            }
            //debug
            // System.out.println("compareTo:");
            return 0;
        }
    }

    public ItemListManager copy() {
        ItemListManager itemListManager = new ItemListManager();
        for (ItemList itemList : this.itemLists) {
            itemListManager.itemLists.add(itemList.copy());
        }
        return itemListManager;
    }

    public ArrayList<ItemList> getItemLists() {
        return itemLists;
    }
}