package com.example.btril.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btril.newsapp.modelClass.Contract;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

/**
 * Created by btril on 06/27/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private Context con;

    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    /*creating an interface to handle click events for the application*/
    public interface ItemClickListener{
        void onItemClick(Cursor cursor, int clickedItem);
    }

    /*view holder to get the items from the layout */
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        con = parent.getContext();
        LayoutInflater li = LayoutInflater.from(con);
        View view = li.inflate(R.layout.list_item, parent, false);
        ItemHolder ih = new ItemHolder(view);
        return ih;
    }

    /*binding the clicked item from the view of the app*/
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView desc;
        TextView date;
        ImageView image;

        ItemHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            desc = (TextView) view.findViewById(R.id.desc);
            date = (TextView) view.findViewById(R.id.date);
            image = (ImageView) view.findViewById(R.id.image);
            view.setOnClickListener(this);

        }

        /*adding the selected items details like title, description, date, url and image to the database*/
        public void bind(int index) {
            cursor.moveToPosition(index);
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            desc.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            date.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE)));
            String img = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE));
            Log.d(TAG, img);
            if (img != null) {
                Picasso.with(con)
                        .load(img) // Load the image into picasso
                        .into(image); // Display this image in the image view of the app
            }
        }

        @Override
        public void onClick(View v) {
            int ind = getAdapterPosition();
            listener.onItemClick(cursor, ind);


        }
    }
}
