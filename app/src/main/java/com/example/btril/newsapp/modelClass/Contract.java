package com.example.btril.newsapp.modelClass;

import android.provider.BaseColumns;

/**
 * Created by btril on 07/26/17.
 */

/*creting a table fot the database to store the details of each news article in their
* respective columns in a table called "TABLE_ARTICLES"*/

public class Contract {

    public static class TABLE_ARTICLES implements BaseColumns{
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "image_url";
        public static final String COLUMN_NAME_PUBLISHED_DATE = "published_at";

    }
}
