package com.example.rakesh.eventmanagement.database.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rakesh on 26/12/15.
 */
public class DataBaseHelper {
    private static final String TAG = "DataBase Working";
    private DataBaseAdapter mDataBaseAdapter;

    public DataBaseHelper(Context context) {

        mDataBaseAdapter = new DataBaseAdapter(context);
    }
    public long insertElement(String name, String password,String description, String startdate,String enddate , String time) {

        Log.d(TAG, "inside insert data");

        SQLiteDatabase db = mDataBaseAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(mDataBaseAdapter.NAME,name);
        contentValues.put(mDataBaseAdapter.LOCATION, password);
        contentValues.put(mDataBaseAdapter.DESCRIPTION,description);
        contentValues.put(mDataBaseAdapter.START_DATE, startdate);
        contentValues.put(mDataBaseAdapter.END_DATE,enddate);
        contentValues.put(mDataBaseAdapter.TIME, time);

        long id = db.insert(mDataBaseAdapter.TABLE_NAME,null,contentValues);

        return id;
    }

    public Cursor  getAllData() {
        SQLiteDatabase db = mDataBaseAdapter.getWritableDatabase();

       String[] columns = {mDataBaseAdapter.UID,mDataBaseAdapter.NAME,mDataBaseAdapter.LOCATION,mDataBaseAdapter.DESCRIPTION,mDataBaseAdapter.START_DATE,mDataBaseAdapter.END_DATE,mDataBaseAdapter.TIME};
        Cursor cursor = db.query(mDataBaseAdapter.TABLE_NAME,columns,null,null,null,null,mDataBaseAdapter.NAME + " ASC");

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateName(String oldName, String newName) {
        SQLiteDatabase db = mDataBaseAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(mDataBaseAdapter.NAME,newName);
        String[] whrereAgrs = {oldName};
        int count = db.update(mDataBaseAdapter.TABLE_NAME,contentValues,mDataBaseAdapter.NAME+ " =? ",whrereAgrs);

        return count;
    }



    public int deleteRow(String position) {
        String i = position;
        SQLiteDatabase db = mDataBaseAdapter.getWritableDatabase();

        int  deleterow = db.delete(mDataBaseAdapter.TABLE_NAME, mDataBaseAdapter.UID+ "='" +i+ "'",null );
        return deleterow;
    }

    public Cursor getselectData(String name) {
        SQLiteDatabase db = mDataBaseAdapter.getWritableDatabase();

        String areaTyp = "SELECT * FROM " + mDataBaseAdapter.TABLE_NAME +
                " where `" + mDataBaseAdapter.NAME + "`=" + name;

        Cursor cursor = db.rawQuery(areaTyp, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }

    private long getDatabasePosition() {
        String sql = "SELECT COUNT(*) FROM " + mDataBaseAdapter.TABLE_NAME;
        SQLiteDatabase mDatabase = null;
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        return count;
    }



    public static class DataBaseAdapter extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "eventmanagement";
        private static final String TABLE_NAME = "eventdetail";
        private static final int DATABASE_VERSION =1;

        public static final String UID = "id";
        public static final String NAME = "Name";
        public static final String LOCATION = "Locatoin";
        public static final String DESCRIPTION = "Description";
        public static final String START_DATE = "StartDate";
        public static final String END_DATE = "EndDate";
        public static final String TIME = "Time";

        private Context mContext;
        private static final String TAG = "DataBase Working";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ " ( "

                +UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NAME+ " VARCHAR(255), "
                +LOCATION+" VARCHAR(255), "
                +DESCRIPTION+" VARCHAR(255), "
                +START_DATE+" VARCHAR(255),"
                +END_DATE+" VARCHAR(255), "
                +TIME+" VARCHAR(255));";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

        public DataBaseAdapter(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.mContext = context;
            Toast.makeText(mContext.getApplicationContext(),"upgrade working",Toast.LENGTH_SHORT);

            Log.d(TAG, "log constructor working");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                Log.d(TAG, "log oncreate working");

                Toast.makeText(mContext.getApplicationContext(), "ONCREATWE working", Toast.LENGTH_SHORT);
            }catch (SQLException E){
                Log.d(TAG, "log oncreate error working" + E);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try{
                Log.d(TAG, "log onupgrade working1");
                Toast.makeText(mContext.getApplicationContext(),"upgrade working",Toast.LENGTH_LONG);
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (SQLException E){
                Log.d(TAG, "log onupgrade error working");
            }
        }
    }
}

