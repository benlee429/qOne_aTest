package com.example.qone_atest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "test_db";
	private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "test_table";
    private static final String CREATE_QUERY = "create query";
    private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + CREATE_QUERY + ";";
    
    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }
    
    //bl- not sure why i need this
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}  
}
