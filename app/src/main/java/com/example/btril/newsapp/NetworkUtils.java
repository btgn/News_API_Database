package com.example.btril.newsapp;

import android.net.Uri;
import android.util.Log;

import com.example.btril.newsapp.modelClass.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by btril on 06/16/17.
 */

public class NetworkUtils {
    public static final String NEWSAPI_BASE_URL = "https://newsapi.org/v1/articles";
    private static final String SOURCE = "source";
    private static final String SORT_BY = "sortBy";
    private static final String LATEST = "latest";
    private static final String API_KEY = "apiKey";
    private static final String source = "the-next-web";


    public static URL makeURL() {
        Uri uri = Uri.parse(NEWSAPI_BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE, source)
                .appendQueryParameter(SORT_BY, LATEST)
                .appendQueryParameter(API_KEY, "8eee4df4a54c40eba1f140c8116f344c")
                .build();

        URL url = null;
        try {
            String urlString = uri.toString();
            Log.d(TAG, "url:" + urlString);
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    /*This method returns the gets the JSON output from the HttpResponse */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /*Creating an ArrayList to store the values from the HttpResponse as a JSON output*/
    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> res = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");

        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);
            String title = article.getString("title");
            String desc = article.getString("description");
            String url = article.getString("url");
            String date = article.getString("publishedAt");
            String urlToImage = article.getString("urlToImage");
            NewsItem data = new NewsItem(title, desc, url, date, urlToImage);
            res.add(data); // adding the retrieved values to the ArrayList
        }
        return res;
    }
}
