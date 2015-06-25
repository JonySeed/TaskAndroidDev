package com.jony.taskandroiddev.fragment.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModelList {


    public static List<ItemList> ITEMS = new ArrayList<ItemList>();

    public static Map<String, ItemList> ITEM_MAP = new HashMap<String, ItemList>();

    public static void addItem(ItemList item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void clearList(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

}
