package com.jony.taskandroiddev.model.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Андрей on 25.06.2015.
 */
@DatabaseTable(tableName = "record")
public class Record {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String str;

    public Record() {
    }

    public Record(String str) {
        this.str = str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString(){
        return Integer.toString(id) + " " + str;
    }


}
