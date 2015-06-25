package com.jony.taskandroiddev.model;

import android.content.Context;
//import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jony.taskandroiddev.model.dao.RecordDao;
import com.jony.taskandroiddev.model.entity.Record;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();


    private static final String DATABASE_NAME ="test.db";
    private static final int DATABASE_VERSION = 1;

    private RecordDao recordDao = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try{

            TableUtils.createTable(connectionSource, Record.class);

        } catch (java.sql.SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{

            TableUtils.dropTable(connectionSource, Record.class, true);
            onCreate(db, connectionSource);

        } catch (java.sql.SQLException e) {
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+" from ver "+oldVer);
            e.printStackTrace();
        }
    }



    public RecordDao getRecordDao() throws SQLException {
        if(recordDao == null){
            recordDao = new RecordDao(getConnectionSource());
        }
        return recordDao;
    }




    @Override
    public void close(){
        super.close();
        recordDao = null;
    }
}