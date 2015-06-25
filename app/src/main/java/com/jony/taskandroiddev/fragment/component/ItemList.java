package com.jony.taskandroiddev.fragment.component;

/**
 * Created by Андрей on 25.06.2015.
 */
public class ItemList {
    public String id;
    public String content;

    public ItemList(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
