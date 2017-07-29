package com.example.btril.newsapp.modelClass;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.btril.newsapp.modelClass.Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION;
import static com.example.btril.newsapp.modelClass.Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE;
import static com.example.btril.newsapp.modelClass.Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE;
import static com.example.btril.newsapp.modelClass.Contract.TABLE_ARTICLES.COLUMN_NAME_URL;
import static com.example.btril.newsapp.modelClass.Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE;
import static com.example.btril.newsapp.modelClass.Contract.TABLE_ARTICLES.TABLE_NAME;

/**
 * Created by btril on 07/26/17.
 */

public class DatabaseUtils {

    public static Cursor getAll(SQLiteDatabase sdb) {
        Cursor cursor = sdb.query(TABLE_NAME, null, null, null, null, null, COLUMN_NAME_PUBLISHED_DATE + " DESC");

        return cursor;
    }


    /*
    * inserting the values retrieved from the json to the database columns
    * */
    public static void bulkInsert(SQLiteDatabase sdb, ArrayList<NewsItem> newsItems){
        sdb.beginTransaction();
        try{
            for(NewsItem ni: newsItems){
                ContentValues cv = new ContentValues();
                /*getting the values and placing them in their respective columns*/
                cv.put(COLUMN_NAME_TITLE, ni.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, ni.getDescription());
                cv.put(COLUMN_NAME_PUBLISHED_DATE, ni.getDate());
                cv.put(COLUMN_NAME_URL, ni.getUrl());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, ni.getUrlToImage());
                sdb.insert(TABLE_NAME, null, cv); // inserting the values of the columns to the database table
            }

            sdb.setTransactionSuccessful(); // setting the transaction as complete once the insertion is complete
        }finally {
            /*we end the connection to the database once we exit the application*/
            sdb.endTransaction();
            sdb.close();
        }
    }

    public static void deleteAll (SQLiteDatabase sdb){
        sdb.delete(TABLE_NAME, null, null);
    }

}