package itemlist;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class TrigonomList implements Iterable<PowObject>, Comparable<TrigonomList> {
    private ArrayList<PowObject> powObjects;

    //sin为0,cos为1
    private int trigonomType;

    //构造方法
    public TrigonomList(int trigonomType) {
        powObjects = new ArrayList<PowObject>();
        this.trigonomType = trigonomType;
    }

    //增
    public void addPowObject(PowObject powObject) {
        //将powObject复制后再添加
        powObjects.add(powObject.copy());
        //powObjects.add(powObject);
    }

    public void addPowObject(ItemListManager itemListManager, BigInteger pow) {
        powObjects.add(new PowObject(itemListManager, pow, trigonomType));
    }

    //删
    public void removePowObject(PowObject powObject) { //只删除第一个
        //powObjects.remove(powObject);
        for (int i = 0; i < powObjects.size(); i++) {
            if (powObjects.get(i).compareTo(powObject) == 0) {
                powObjects.remove(i);
                break;
            }
        }
    }

    //获取size
    public int getSize() {
        return powObjects.size();
    }

    //读
    //TODO
    //相乘
    public TrigonomList multiply(TrigonomList trigonomList) { //TODO
        TrigonomList trigonomList1 = new TrigonomList(trigonomType);
        for (PowObject powObject1 : this) {
            trigonomList1.addPowObject(powObject1);
        }
        for (PowObject powObject2 : trigonomList) {
            trigonomList1.addPowObject(powObject2);
        }
        //trigonomList1.merge();
        return trigonomList1.copy();
    }

    //排序
    public void sort() {
        powObjects.sort(new Comparator<PowObject>() {
            @Override
            public int compare(PowObject o1, PowObject o2) {
                return o1.compareTo(o2);
            }
        });
    } //弄清大小关系//TODO

    //整理powObjects,合并相同的PowObject,并且把指数相加
    public int merge() {
        //递归化简powObject的itemListManager
        for (PowObject powObject : powObjects) {
            powObject.getItemListManager().memrgeFinal();
        }
        this.sort();
        //合并相同的PowObject,并且把指数相加
        for (int i = 0; i < powObjects.size(); i++) { //如果有两个相同的PowObject,则把他们的指数相加
            for (int j = 0; j < powObjects.size(); j++) {
                if (i == j) {
                    continue;
                }
                ItemListManager itemListManager1 = powObjects.get(i).getItemListManager().copy();
                ItemListManager itemListManager2 = powObjects.get(j).getItemListManager().copy();

                if (itemListManager1.
                        compareTo(itemListManager2) == 0) {
                    BigInteger pow1 = powObjects.get(i).getPow();
                    BigInteger pow2 = powObjects.get(j).getPow();
                    powObjects.get(i).setPow(pow1.add(pow2));
                    powObjects.remove(j);
                    i--;
                    break;
                }
            }
        }
        if (hasSameItem() == 1) {
            System.err.print("Has Same Item!!!from merge()" + powObjects);
        }
        //删除指数为0的PowObject
        for (int i = 0; i < powObjects.size(); i++) {
            if (powObjects.get(i).getPow().equals(BigInteger.valueOf(0))) {
                powObjects.remove(i);
                i--;//如果后续要继续完成代码,则要记得如果i为-1要continue
            }
        }
        //去除负数
        int tmp = 1;
        tmp = tmp * simplifyNegate();


        //开始化简
        return tmp * simplifySelf();
    }

    private int simplifyNegate() { //化简负数
        //把itemListManager中的负底数变为正底数
        int tmp = 1;
        for (int i = 0; i < powObjects.size(); i++) {
            BigInteger coeff = powObjects.get(i).getItemListManager().
                    getItemList(0).getCoefficient();
            //如果系数为负数
            if (coeff.compareTo(BigInteger.valueOf(0)) < 0) {
                //并且是cos或者是sin的偶数次幂,则直接把系数变为正数
                if (trigonomType == 1 ||
                        (trigonomType == 0 && powObjects.get(i).getPow().
                                mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0)))) {
                    powObjects.get(i).getItemListManager().multiplyCoefficient(
                            BigInteger.valueOf(-1));
                    tmp = tmp * 1;
                }
                //否则,把底数变为负数
                else {
                    powObjects.get(i).getItemListManager().multiplyCoefficient(
                            BigInteger.valueOf(-1));
                    tmp = tmp * -1;
                }
            }
        }
        return tmp;
    }

    private int simplifySelf() { //自我化简,去0
        this.sort();
        //1.先化简cos(0)和sin(0)
        for (int i = 0; i < powObjects.size(); i++) {
            if (trigonomType == 1 &&    //cos
                    powObjects.get(i).getItemListManager().removeZeroItem() == 0) {
                powObjects.remove(i);
                i--;
            }
            if (trigonomType == 0 &&    //sin
                    powObjects.get(i).getItemListManager().removeZeroItem() == 0) {
                //该项为0
                return 0;
            }
        }
        //
        return 1;
    }

    //toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        int flag = 0;
        for (PowObject powObject : powObjects) {
            if (flag == 0) { flag = 1; }
            else if (!powObject.toString().equals("")) {
                sb.append("*");
            }
            sb.append(powObject.toString());
        }
        return sb.toString();
    }

    //迭代器
    @Override
    public Iterator<PowObject> iterator() {
        return powObjects.iterator();
    }

    @Override
    public int compareTo(TrigonomList o2) {
        this.sort();
        o2.sort();
        //先比较size
        if (this.getSize() > o2.getSize()) {
            return 1;
        } else if (this.getSize() < o2.getSize()) {
            return -1;
        } else {
            //再比较每个PowObject
            for (int i = 0; i < this.getSize(); i++) {
                if (this.powObjects.get(i).compareTo(o2.powObjects.get(i)) > 0) {
                    return 1;
                } else if (this.powObjects.get(i).compareTo(o2.powObjects.get(i)) < 0) {
                    return -1;
                }
            }
            return 0;
        }
    }

    public TrigonomList copy() {
        TrigonomList trigonomList = new TrigonomList(trigonomType);
        for (PowObject powObject : this) {
            trigonomList.addPowObject(powObject);
        }
        return trigonomList;
    }

    //copy
    public void copy(TrigonomList trigonomList) {
        for (PowObject powObject : trigonomList) {
            this.addPowObject(powObject);
        }
    }

    public PowObject getTrigonom(int i) {
        return powObjects.get(i);
    }

    public int hasSameItem() { //判断是否有相同的项
        for (int i = 0; i < powObjects.size(); i++) {
            for (int j = i + 1; j < powObjects.size(); j++) {
                if (powObjects.get(i).getItemListManager().
                        compareTo(powObjects.get(j).getItemListManager()) == 0 &&
                        powObjects.get(i).getPow().equals(powObjects.get(j).getPow())) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
