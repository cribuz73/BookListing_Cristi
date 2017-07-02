package com.example.android.booklisting;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cristi on 6/26/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    private static final String LOG_TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
        Log.i(LOG_TAG, "TEST : using BOOK ADAPTER");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        String title = currentBook.getBookTitle();
        String subtitle = currentBook.getBookSubtitle();
        String year = currentBook.getBookYear();
        String author = currentBook.getBookAuthor();
        String publisher = currentBook.getBookPublisher();


        TextView titleTextView = (TextView) listView.findViewById(R.id.title);
        titleTextView.setText(title);

        TextView subtitleTextView = (TextView) listView.findViewById(R.id.subtitle);
        subtitleTextView.setText(subtitle);

        TextView authorTextView = (TextView) listView.findViewById(R.id.author);
        authorTextView.setText(author);

        TextView yearTextView = (TextView) listView.findViewById(R.id.year);
        yearTextView.setText(year);

        TextView publisherTextView = (TextView) listView.findViewById(R.id.publisher);
        publisherTextView.setText(publisher);

        return listView;
    }

}
