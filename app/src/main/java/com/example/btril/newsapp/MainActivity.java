package com.example.btril.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.btril.newsapp.modelClass.Contract;
import com.example.btril.newsapp.modelClass.DBHelper;
import com.example.btril.newsapp.modelClass.DatabaseUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener {
    static final String TAG = "mainactivty";
    private ProgressBar progress;
    private EditText search;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    // Instantiating Cursor and SQLitew Databse Objects
    private Cursor cursor;
    private SQLiteDatabase sdb;
    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        search = (EditText) findViewById(R.id.searchQuery);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean first = sp.getBoolean("first", true);

        /*Load values into the database if you're running the app fir the first time*/
        if (first) {
            load();
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("first", false);
            edit.commit();
        }

        /*else it just refreshes the page and loads it with new data to the TextView of the application*/
        RefreshInterval.refreshTime(this);
    }

    private void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

    }


    @Override
    protected void onStart() {
        super.onStart();
        /*Retrieving all the values from the Database*/
        sdb = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(sdb);
        adapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
    }

    /*Closes the database connection when the app is closed*/
    @Override
    protected void onStop() {
        super.onStop();
        sdb.close();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();
        if (itemNumber == R.id.search) {
            String s = search.getText().toString();
            load();
//            NetworkTask nt = new NetworkTask(s);
//            nt.execute();
        }
        return true;
    }

    @Override
    public android.support.v4.content.Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE); // setting the ProgressBar to VISIBLE when the refresh button is clicked
            }

            @Override
            public Void loadInBackground() {
                RefreshTasks.refreshArticles(MainActivity.this); // refreshes the page for every minute in the background when the app is running
                return null;
            }

        };
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Void> loader, Void data) {
        progress.setVisibility(View.GONE); // visibility of the ProgressBar to GONE when the TextView is displayed
        sdb = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(sdb);

        adapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Void> loader) {

    }


    public void onItemClick(Cursor cursor, int itemIndex) {
        cursor.moveToPosition(itemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
