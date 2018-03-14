package com.mygdx.rngame;

import android.provider.BaseColumns;

/**
 * Created by Fidde on 2018-03-13.
 */

public class FeedReaderContract {
    private FeedReaderContract(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "HighScore";
        public static final String COLUMN_NAME_SCORE = "score";
    }
}
