package itemlist;

import analyse.Parser;
import analyse.Lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyfuncList {

    private static ArrayList<MyItem> itemList = new ArrayList<>();

    public static ArrayList<MyItem> getItemList() {
        return itemList;
    }

    public static void addItem(String funcName,
                               HashMap<String, Integer> formPara, ItemListManager itemListManager) {
        MyItem newItem = new MyItem(funcName, formPara, itemListManager);
        itemList.add(newItem);
    }

    public static void addItem(Map<String, String> funcMap) { //将funcMap中的函数添加到itemList中
        //遍历map
        for (Map.Entry<String, String> entry : funcMap.entrySet()) {
            String funcNames  = entry.getKey();
            String exprStr = entry.getValue();
            HashMap<String, Integer> formPara = new HashMap<>();
            String funcName = "";
            //将exprStr转换为ItemListManager
            Parser parser = new Parser(new Lexer(exprStr));
            ItemListManager itemListManager = parser.parseExpr().getItemListManager();
            //将funcNames转换为funcname和formPara
            Lexer lexer = new Lexer(funcNames);
            if (lexer.getSign() == 7) { //变量名
                funcName = lexer.peek();
                lexer.next(); //左括号
                lexer.next(); //xyz变量
                int flag = 0;
                while (lexer.getSign() == 6) { //变量
                    formPara.put(lexer.peek(), flag);
                    flag++;
                    lexer.next();
                    if (lexer.getSign() == 8) { //逗号
                        lexer.next();
                    } else {
                        break;
                    }
                }
            } else {
                System.err.println("Error: funcName is not a string");
            }
            MyItem newItem = new MyItem(funcName, formPara, itemListManager);
            itemList.add(newItem);
        }
    }

    public static void removeItem(int index) {
        itemList.remove(index);
    }

    public static void updateItem(int index, String funcName,
                                  HashMap<String, Integer> formPara,
                                  ItemListManager itemListManager) {
        //更新第index个元素
        MyItem updatedItem = new MyItem(funcName, formPara, itemListManager);
        itemList.set(index, updatedItem);
    }

    public static MyItem getItem(int index) {
        return itemList.get(index);
    }

    public static ArrayList<MyItem> getAllItems() {
        return itemList;
    }

    // Other necessary methods can be added here

    static class MyItem {
        private String funcName;
        private HashMap<String, Integer> formPara;
        private ItemListManager itemListManager;

        public MyItem(String funcName,
                      HashMap<String, Integer> formPara, ItemListManager itemListManager) {
            this.funcName = funcName;
            this.formPara = formPara;
            this.itemListManager = itemListManager;
        }

        //读
        public String getFuncName() {
            return funcName;
        }

        public HashMap<String, Integer> getFormPara() {
            return formPara;
        }

        public ItemListManager getItemListManager() {
            return itemListManager;
        }

        //写
        public void addFormPara(String key, Integer value) {
            formPara.put(key, value);
        }
    }

    public static ItemListManager replace(String funcName,
                                           HashMap<Integer, ItemListManager> funcMap) {
        MyfuncListReplace myfuncListReplace = new MyfuncListReplace();
        return myfuncListReplace.replace(funcName, funcMap);
    }
    //下面所有的代码都是为了实现替换形参,返回一个替换形参后的ItemListManager
    //    public static ItemListManager
    //    replace(String funcName, HashMap<Integer, ItemListManager> funcMap) {
    // 返回一个替换形参后的ItemListManager
    //        ItemListManager itemListManager1 = new ItemListManager();
    //
    //        MyItem myItem = null;
    //        for (MyItem item : itemList) {
    //            if (item.getFuncName().equals(funcName)) {
    //                myItem = item;//找到了funcName对应的MyItem
    //                break;
    //            }
    //        }
    //        if (myItem == null) {
    //            System.err.println("Error: funcName is not in MyfuncList");
    //            return null;
    //        }
    //        HashMap<String, Integer> formPara = myItem.getFormPara();//获取形参
    //        ItemListManager itemListManager = myItem.getItemListManager();//获取函数表达式ItemListManager
    //        //判断形参是否为空
    //        if (formPara.isEmpty()) {
    //            return itemListManager;
    //        }
    //        //形参不为空,遍历itemListManager中的各个ItemList(项)
    //        //新建一个ArrayList,用于存放替换后的各个itemListManager
    //        //ArrayList<ItemListManager> itemListManagerListArray = new ArrayList<>();
    //
    //
    //        for (int i = 0; i < itemListManager.getManagerSize(); i++) {
    //            ItemList itemList = itemListManager.getItemList(i);
    //            itemListManager1 = itemListManager1.
    //            add(doReplace(itemList, funcMap, formPara));//替换形参
    //        }
    //
    //        return itemListManager1;
    //    }
    //
    //    public static ItemListManager replace(
    //    HashMap<Integer, ItemListManager> funcMap, HashMap<String, Integer> formPara,
    //                                          ItemListManager itemListManager) {
    // 返回一个替换形参后的ItemListManager
    //        ItemListManager itemListManager1 = new ItemListManager();
    //        //遍历itemListManager中的各个ItemList
    //        for (ItemList itemList : itemListManager) { //遍历itemListManager中的各个ItemList
    //            ItemListManager itemListManager2 = doReplace(itemList, funcMap, formPara);
    //            itemListManager1 = itemListManager1.add(itemListManager2);
    //        }
    //        return itemListManager1;
    //    }
    //
    //    private static ItemListManager doReplace(
    //    ItemList itemList, HashMap<Integer, ItemListManager> funcMap,
    //                                             HashMap<String, Integer> formPara) { //替换形参
    //        ItemListManager itemListManager = new ItemListManager();
    //        //1.itemListManager的系数等于itemList的系数
    //        ItemList itemList1 = new ItemList();
    //        itemList1.setCoefficient(itemList.getCoefficient());
    //        itemListManager.addItem(itemList1);
    //        //2.处理形参x
    //        itemListManager = itemListManager.
    //        multiply(doVarReplace("x", itemList, funcMap, formPara));
    //        //3.处理形参y
    //        itemListManager = itemListManager.
    //        multiply(doVarReplace("y", itemList, funcMap, formPara));
    //        //4.处理形参z
    //        itemListManager = itemListManager.
    //        multiply(doVarReplace("z", itemList, funcMap, formPara));
    //        //5.处理sin
    //        itemListManager = itemListManager.
    //        multiply(doTriReplace("sin", itemList, funcMap, formPara));
    //        //6.处理cos
    //        itemListManager = itemListManager.
    //        multiply(doTriReplace("cos", itemList, funcMap, formPara));
    //        return itemListManager;
    //    }
    //
    //    private static ItemListManager doVarReplace
    //    (String var, ItemList itemList, HashMap<Integer, ItemListManager> funcMap,
    //                                                HashMap<String, Integer> formPara ) {
    //        ItemListManager itemListManager = null;
    //        if (formPara.containsKey(var)) {
    //            int index = formPara.get(var);
    //            ItemListManager itemListManager1 = funcMap.get(index);
    //            if (var == "x")
    //                itemListManager = itemListManager1.pow(itemList.getxExp().intValue());
    //            else if (var == "y")
    //                itemListManager = itemListManager1.pow(itemList.getyExp().intValue());
    //            else if (var == "z")
    //                itemListManager = itemListManager1.pow(itemList.getzExp().intValue());
    //        } else { //如果没有形参
    //            int tmp = 0;
    //            if (var == "x")
    //                tmp = itemList.getxExp().intValue();
    //            else if (var == "y")
    //                tmp = itemList.getyExp().intValue();
    //            else if (var == "z")
    //                tmp = itemList.getzExp().intValue();
    //            //新建一个系数为1的ItemListManager
    //            ItemListManager itemListManager1 = new ItemListManager();//
    //            ItemList itemList2 = new ItemList();
    //            itemList2.setCoefficient(BigInteger.ONE);
    //            if (tmp != 0) {
    //                if (var == "x")
    //                    itemList2.setxExp(itemList.getxExp());
    //                else if (var == "y")
    //                    itemList2.setyExp(itemList.getyExp());
    //                else if (var == "z")
    //                    itemList2.setzExp(itemList.getzExp());
    //            }
    //            itemListManager1.addItem(itemList2);
    //            itemListManager = itemListManager1;
    //
    //        }
    //        return itemListManager;
    //    }
    //    private static ItemListManager doTriReplace
    //    (String tri, ItemList itemList, HashMap<Integer, ItemListManager> funcMap,
    //                                                HashMap<String, Integer> formPara ) {
    //        //新建一个系数为1的ItemListManager
    //        ItemListManager itemListManager = new ItemListManager();
    //        ItemList itemList1 = new ItemList();
    //        itemList1.setCoefficient(BigInteger.ONE);
    //        //获取sin或cos的PowObjectList
    //        TrigonomList trigonomList = null;
    //        int flag = 0;
    //        if (tri == "sin") {
    //            trigonomList = itemList.getSinTrigonomList();
    //            flag = 0;
    //        } else {
    //            trigonomList = itemList.getCosTrigonomList();
    //            flag = 1;
    //        }
    //        //遍历PowObjectList
    //        TrigonomList trigonomList1 = new TrigonomList(flag);
    //        for (PowObject powObject : trigonomList) {
    //            //取出PowObject的每个参数
    //            ItemListManager itemListManager1 = powObject.getItemListManager();
    //            BigInteger pow = powObject.getPow();
    //            //调用replace方法
    //            ItemListManager itemListManager2 = replace(funcMap, formPara, itemListManager1);
    //            //将结果加入到trigonomList1中
    //            trigonomList1.addPowObject(itemListManager2, pow);
    //        }
    //        trigonomList1.merge();
    //        //将trigonomList1加入到itemListManager中
    //        if (tri == "sin")
    //            itemList1.setSinTrigonomList(trigonomList1);
    //        else
    //            itemList1.setSinTrigonomList(trigonomList1);
    //        itemListManager.addItem(itemList1);
    //
    //        return itemListManager;
    //    }
    //public static class ItemListManager {
    //    //为了方便在MyfuncList中存储ItemListManager而创建的
    //}
}
