package expr;

//import itemlist.ItemList;
import itemlist.ItemListManager;
//import itemlist.TrigonomList;
//import itemlist.MyfuncList;
import itemlist.MyfuncListReplace;
//import java.math.BigInteger;
import java.util.HashMap;

public class Myfunc implements Factor {
    private ItemListManager itemListManager = new ItemListManager();
    private HashMap<Integer, ItemListManager> funcMap = new HashMap<>();
    private String funcName;

    public Myfunc(String funcName) {
        this.funcName = funcName;
    }

    //有参数构造函数
    public Myfunc(String funcName, HashMap<Integer, ItemListManager> funcMap) {
        this.funcMap = funcMap;
        this.funcName = funcName;
    }

    //add
    public void add(int key, ItemListManager itemListManager) {
        funcMap.put(key, itemListManager);
    }

    public void setItemListManager() {
        MyfuncListReplace myfuncListReplace = new MyfuncListReplace();
        //使用MyfuncList中的替换方法
        itemListManager = myfuncListReplace.replace(funcName, funcMap);
    }



    //getItemListManager
    @Override
    public ItemListManager getItemListManager() {
        setItemListManager();
        return itemListManager;
    }

}
