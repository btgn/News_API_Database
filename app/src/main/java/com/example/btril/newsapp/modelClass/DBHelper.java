package com.example.btril.newsapp.modelClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by btril on 07/26/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "articles.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*Creating a table to store the values retrieved from the json query from the news API */
    @Override
    public void onCreate(SQLiteDatabase sdb) {
        String queryString = "CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME + " ("+
                Contract.TABLE_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE + " DATE, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE + " TEXT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL + " TEXT" +
                "); ";

        Log.d(TAG, "SQL Query for Create Table: " + queryString);
        sdb.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
// we don't really need this
    }
}
