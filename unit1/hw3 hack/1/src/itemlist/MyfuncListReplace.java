package itemlist;

import java.math.BigInteger;
import java.util.HashMap;

public class MyfuncListReplace {
    //    static ItemListManager itemListManager11 = new ItemListManager();
    public ItemListManager replace(String funcName, HashMap<Integer, ItemListManager> funcMap) {
        //返回一个替换形参后的ItemListManager


        MyfuncList.MyItem myItem = null;
        for (MyfuncList.MyItem item : MyfuncList.getItemList()) {
            if (item.getFuncName().equals(funcName)) {
                myItem = item;//找到了funcName对应的MyItem
                break;
            }
        }
        if (myItem == null) {
            System.err.println("Error: funcName is not in MyfuncList");
            return null;
        }
        HashMap<String, Integer> formPara = myItem.getFormPara();//获取形参
        ItemListManager itemListManager = new ItemListManager();
        itemListManager = itemListManager.add(myItem.getItemListManager());//获取函数表达式ItemListManager
        //判断形参是否为空
        if (formPara.isEmpty()) {
            return itemListManager;
        }
        //形参不为空,遍历itemListManager中的各个ItemList(项)
        //新建一个ArrayList,用于存放替换后的各个itemListManager
        //ArrayList<ItemListManager> itemListManagerListArray = new ArrayList<>();
        ItemListManager itemListManager11 = new ItemListManager();
        //ItemListManager itemListManager2 ;
        for (int i = 0; i < itemListManager.getManagerSize(); i++) {
            ItemList itemList = itemListManager.getItemList(i);
            ItemListManager itemListManager2 = doReplace(itemList, funcMap, formPara).copy();
            itemListManager11 = itemListManager11.add(itemListManager2.copy());//替换形参
        }

        return itemListManager11;
    }

    public ItemListManager replace(HashMap<Integer, ItemListManager> funcMap,
                                   HashMap<String, Integer> formPara,
                                   ItemListManager itemListManager) { //返回一个替换形参后的ItemListManager
        ItemListManager itemListManager1 = new ItemListManager();
        //遍历itemListManager中的各个ItemList
        for (ItemList itemList : itemListManager) { //遍历itemListManager中的各个ItemList
            ItemListManager itemListManager2 = doReplace(itemList, funcMap, formPara);
            itemListManager1 = itemListManager1.add(itemListManager2);
        }
        return itemListManager1;
    }

    private ItemListManager doReplace(ItemList itemList, HashMap<Integer, ItemListManager> funcMap,
                                             HashMap<String, Integer> formPara) { //替换形参
        ItemListManager itemListManager = new ItemListManager();
        //1.itemListManager的系数等于itemList的系数
        ItemList itemList1 = new ItemList();
        itemList1.setCoefficient(itemList.getCoefficient());
        itemListManager.addItem(itemList1);
        //2.处理形参x
        itemListManager = itemListManager.multiply(doVarReplace("x", itemList, funcMap, formPara));
        //3.处理形参y
        itemListManager = itemListManager.multiply(doVarReplace("y", itemList, funcMap, formPara));
        //4.处理形参z
        itemListManager = itemListManager.multiply(doVarReplace("z", itemList, funcMap, formPara));
        //5.处理sin
        itemListManager = itemListManager.
                multiply(doTriReplace("sin", itemList, funcMap, formPara));
        //6.处理cos
        itemListManager = itemListManager.
                multiply(doTriReplace("cos", itemList, funcMap, formPara));
        return itemListManager;
    }

    private ItemListManager doVarReplace(String var, ItemList itemList,
                                         HashMap<Integer, ItemListManager> funcMap,
                                         HashMap<String, Integer> formPara) {
        ItemListManager itemListManager = null;
        if (formPara.containsKey(var)) {
            int index = formPara.get(var);
            ItemListManager itemListManager1 = new ItemListManager();
            itemListManager1 = itemListManager1.add(funcMap.get(index));//这里少了一个赋值语句
            if (var.equals("x")) {
                itemListManager = itemListManager1.pow(itemList.getxExp().intValue());
            } else if (var.equals("y")) {
                itemListManager = itemListManager1.pow(itemList.getyExp().intValue());
            } else if (var.equals("z")) {
                itemListManager = itemListManager1.pow(itemList.getzExp().intValue());
            }
        } else { //如果没有形参
            int tmp = 0;
            if (var.equals("x")) {
                tmp = itemList.getxExp().intValue();
            } else if (var.equals("y")) {
                tmp = itemList.getyExp().intValue();
            } else if (var.equals("z")) {
                tmp = itemList.getzExp().intValue();
            }
            //新建一个系数为1的ItemListManager
            ItemListManager itemListManager1 = new ItemListManager();//
            ItemList itemList2 = new ItemList();
            itemList2.setCoefficient(BigInteger.ONE);
            if (tmp != 0) {
                if (var.equals("x")) {
                    itemList2.setxExp(itemList.getxExp());
                } else if (var.equals("y")) {
                    itemList2.setyExp(itemList.getyExp());
                } else if (var.equals("z")) {
                    itemList2.setzExp(itemList.getzExp());
                }
            }
            itemListManager1.addItem(itemList2);
            itemListManager = itemListManager1;

        }
        return itemListManager;
    }

    private ItemListManager doTriReplace(String tri, ItemList itemList,
                                         HashMap<Integer, ItemListManager> funcMap,
                                         HashMap<String, Integer> formPara) {
        //新建一个系数为1的ItemListManager

        ItemList itemList1 = new ItemList();
        itemList1.setCoefficient(BigInteger.ONE);
        //获取sin或cos的PowObjectList
        TrigonomList trigonomList = null;
        int flag;
        if (tri.equals("sin")) {
            trigonomList = itemList.getSinTrigonomList();
            flag = 0;
        } else {
            trigonomList = itemList.getCosTrigonomList();
            flag = 1;
        }
        //遍历PowObjectList
        TrigonomList trigonomList1 = new TrigonomList(flag);
        for (PowObject powObject : trigonomList) {
            //取出PowObject的每个参数
            ItemListManager itemListManager1 = powObject.getItemListManager();
            BigInteger pow = powObject.getPow();
            //调用replace方法
            ItemListManager itemListManager2 = replace(funcMap, formPara, itemListManager1);
            //将结果加入到trigonomList1中
            trigonomList1.addPowObject(itemListManager2, pow);
        }
        int tmp = trigonomList1.merge();
        //将trigonomList1加入到itemListManager中
        if (tri.equals("sin")) {
            itemList1.setSinTrigonomList(trigonomList1);
        } else {
            itemList1.setCosTrigonomList(trigonomList1);//修改
        }
        BigInteger tmpCoeff = itemList1.getCoefficient().multiply(BigInteger.valueOf(tmp));
        itemList1.setCoefficient(tmpCoeff);
        ItemListManager itemListManager = new ItemListManager();
        itemListManager.addItem(itemList1);

        return itemListManager;
    }
}
