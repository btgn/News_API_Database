package com.example.btril.newsapp.modelClass;

/**
 * Created by btril on 06/26/17.
 */


/*PoJo Class to store the information in an array list wherever necessary*/
public class NewsItem {
    private String title;
    private String description;
    private String url;
    private String date;
    private String urlToImage;

    public NewsItem(String title, String description, String url, String date, String urlToImage) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.date = date;
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
