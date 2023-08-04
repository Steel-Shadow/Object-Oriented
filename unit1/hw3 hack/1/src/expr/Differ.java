package expr;

import itemlist.ItemList;
import itemlist.ItemListManager;
import itemlist.PowObject;
import itemlist.TrigonomList;

import java.math.BigInteger;
//import java.util.ArrayList;

public class Differ implements Factor { //求导类
    private ItemListManager itemListManager; //itemListManager对象
    private String derStr;
    private int derNum = 0; //x:0, y:1, z:2//求哪个变量的导数

    //初始化
    public Differ(ItemListManager itemListManager, String derStr) {
        this.itemListManager = itemListManager;
        this.derStr = derStr;
        //根据derStr判断derNum
        if (derStr.equals("dx")) {
            derNum = 0;
        } else if (derStr.equals("dy")) {
            derNum = 1;
        } else if (derStr.equals("dz")) {
            derNum = 2;
        }
        ItemListManager itemListManagertmp = diff(itemListManager, derNum);
        this.itemListManager = itemListManagertmp.copy();
    }

    //添加表达式
    public void addExpr(ItemListManager itemListManager, String derStr) {
        this.itemListManager = itemListManager;
        this.derStr = derStr;
    }

    //求导
    public ItemListManager diff(ItemListManager itemListManager, int derNum) {
        //对itemListManager中的每个项进行求导
        itemListManager.mergeItemLists();
        ItemListManager itemListManager1 = new ItemListManager();//存放求导后的项
        for (ItemList item : itemListManager) {
            //常规项求导
            itemListManager1 = itemListManager1.add(diffItemNomal(item,derNum));
            //三角函数项求导
            itemListManager1 = itemListManager1.add(diffItemTrigonom(item,derNum));
        }
        itemListManager1.mergeItemLists();
        return itemListManager1;
    }

    private ItemListManager diffItemTrigonom(ItemList item, int derNum) {
        ItemListManager itemListManager1 = new ItemListManager();
        //对三角函数求导
        //存储参数
        BigInteger coeffPre = item.getCoefficient();
        BigInteger xpre = item.getxExp();
        BigInteger ypre = item.getyExp();
        BigInteger zpre = item.getzExp();
        TrigonomList sinListPre = item.getSinList();
        TrigonomList cosListPre = item.getCosList();
        BigInteger coeff = coeffPre;
        //三角函数中的每一项分别求导
        for (PowObject triItem : sinListPre) {
            TrigonomList sinList = new TrigonomList(0);
            TrigonomList cosList = new TrigonomList(1);
            sinList.copy(sinListPre);
            cosList.copy(cosListPre);
            sinList.removePowObject(triItem);
            BigInteger pow = triItem.getPow();
            //1.
            //如果三角函数中的项的指数为0，导数为0
            if (triItem.getPow().equals(BigInteger.valueOf(0))) {
                continue;
            } //指数为0
            //系数乘以指数
            BigInteger coeffTmp = coeff.multiply(pow);
            //指数减一
            BigInteger powTmp = pow.subtract(BigInteger.valueOf(1));
            //先将指数减一后的项加入itemListManager2
            ItemListManager itemListManagerTmp = triItem.getItemListManager();//暂存三角函数内部的表达式
            sinList.addPowObject(itemListManagerTmp, powTmp);

            //2.
            //sin变cos
            cosList.addPowObject(itemListManagerTmp, BigInteger.ONE);
            //3.
            //ItemManger
            ItemListManager itemListManager2 = new ItemListManager();//存放三角函数的导数
            itemListManager2.addItem(coeffTmp, xpre, ypre, zpre, sinList, cosList);
            //4.三角里面的项求导 然后乘起来
            ItemListManager itemListManagerTmp2 = diff(itemListManagerTmp, derNum);
            itemListManager2 = itemListManager2.multiply(itemListManagerTmp2);
            //此时的itemListManager2为针对这一项三角函数项的导数
            //将此项加入itemListManager1
            itemListManager1 = itemListManager1.add(itemListManager2);
        }
        for (PowObject triItem : cosListPre) {
            TrigonomList sinList = new TrigonomList(0);
            TrigonomList cosList = new TrigonomList(1);
            sinList.copy(sinListPre);
            cosList.copy(cosListPre);
            cosList.removePowObject(triItem);
            BigInteger pow = triItem.getPow();
            //1.
            //如果三角函数中的项的指数为0，导数为0
            if (triItem.getPow().equals(BigInteger.valueOf(0))) {
                continue;
            } //指数为0
            //系数乘以指数
            BigInteger coeffTmp = coeff.multiply(pow);
            //指数减一
            BigInteger powTmp = pow.subtract(BigInteger.valueOf(1));
            //先将指数减一后的项加入itemListManager2
            ItemListManager itemListManagerTmp = triItem.getItemListManager();//暂存三角函数内部的表达式
            cosList.addPowObject(itemListManagerTmp, powTmp);
            //2.
            //cos变sin
            coeffTmp = coeffTmp.negate();//cos求导sin有-1
            sinList.addPowObject(itemListManagerTmp, BigInteger.ONE);
            //3.
            //ItemManger
            ItemListManager itemListManager2 = new ItemListManager();//存放三角函数的导数
            itemListManager2.addItem(coeffTmp, xpre, ypre, zpre, sinList, cosList);
            //4.三角里面的项求导 然后乘起来
            ItemListManager itemListManagerTmp2 = diff(itemListManagerTmp, derNum);
            itemListManager2 = itemListManager2.multiply(itemListManagerTmp2);
            //此时的itemListManager2为针对这一项三角函数项的导数
            //将此项加入itemListManager1
            itemListManager1 = itemListManager1.add(itemListManager2);
        }
        return itemListManager1;
    }

    private ItemListManager diffItemNomal(ItemList item, int derNum) {
        ItemListManager itemListManager1 = new ItemListManager();
        //对x,y,z求导
        //存储参数
        BigInteger coeffPre = item.getCoefficient();
        BigInteger xpre = item.getxExp();
        BigInteger ypre = item.getyExp();
        BigInteger zpre = item.getzExp();
        TrigonomList sinListPre = item.getSinList();
        TrigonomList cosListPre = item.getCosList();
        BigInteger coeff = coeffPre;
        BigInteger x = xpre;
        BigInteger y = ypre;
        BigInteger z = zpre;
        if (derNum == 0) { //求导x
            //如果x的指数为0，导数为0
            if (xpre.equals(BigInteger.valueOf(0))) {
                return itemListManager1;
            } else {
                //系数乘以x的指数
                coeff = coeffPre.multiply(xpre);
                //x的指数减一
                x = xpre.subtract(BigInteger.valueOf(1));
            }
        } else if (derNum == 1) { //求导y
            //如果y的指数为0，导数为0
            if (ypre.equals(BigInteger.valueOf(0))) {
                return itemListManager1;
            } else {
                //系数乘以y的指数
                coeff = coeffPre.multiply(ypre);
                //y的指数减一
                y = ypre.subtract(BigInteger.valueOf(1));
            }
        } else if (derNum == 2) { //求导z
            //如果z的指数为0，导数为0
            if (zpre.equals(BigInteger.valueOf(0))) {
                return itemListManager1;
            } else {
                //系数乘以z的指数
                coeff = coeffPre.multiply(zpre);
                //z的指数减一
                z = zpre.subtract(BigInteger.valueOf(1));
            }
        } else {
            return itemListManager1;
        }
        ItemList item1 = new ItemList(coeff, x, y, z, sinListPre, cosListPre);
        itemListManager1.addItem(item1);
        return itemListManager1;
    }

    @Override
    public ItemListManager getItemListManager() {
        return itemListManager;
    }
}
