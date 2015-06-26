package com.jony.taskandroiddev.model.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.jony.taskandroiddev.model.entity.Record;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Андрей on 25.06.2015.
 */
public class RecordDao {

    private Dao<Record,String> dao;

    public RecordDao(ConnectionSource source) throws SQLException {
            dao = DaoManager.createDao(source, Record.class);
    }

    public List<Record> getAllRecord() throws SQLException {
        return dao.queryForAll();
    }

    public void addRecord(Record record) throws SQLException {
        dao.create(record);
    }


    public void clearList() throws SQLException {
        deleteList(getAllRecord());
    }

    public void deleteList(List<Record> list) throws SQLException {
        dao.delete(list);
    }

}
