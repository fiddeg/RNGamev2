package com.mygdx.rngame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Fidde on 2018-03-14.
 */

public class FeedReaderAddToDatabase {
    FeedReaderDbHelper mDbHelper;
    SQLiteDatabase dbWrite;
    SQLiteDatabase dbRead;

    public FeedReaderAddToDatabase(Context context) {
        mDbHelper = new FeedReaderDbHelper(context);
        dbWrite = mDbHelper.getWritableDatabase();
        dbRead = mDbHelper.getReadableDatabase();
    }

    public void writeNew(String score){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE, score);
        dbWrite.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, "_id=1",null);
    }

    public String readHighScore(){
        String[] projection = {FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE};
        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = { "1" };
        String sortOrder = FeedReaderContract.FeedEntry._ID + " DESC";

        Cursor cursor = dbRead.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        String highScore = "";
        if(cursor.moveToFirst()){
            highScore = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE));
        }
        cursor.close();
        return highScore;
    }
}
