package com.example.android.booklisting;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cristi on 6/26/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    private static final String LOG_TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Activity context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        String title = currentBook.getBookTitle();
        String subtitle = currentBook.getBookSubtitle();
        String year = currentBook.getBookYear();
        String author = currentBook.getBookAuthor();
        String publisher = currentBook.getBookPublisher();


        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(title);

        TextView subtitleTextView = (TextView) listItemView.findViewById(R.id.subtitle);
        subtitleTextView.setText(subtitle);

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        authorTextView.setText(author);

        TextView yearTextView = (TextView) listItemView.findViewById(R.id.year);
        yearTextView.setText(year);

        TextView publisherTextView = (TextView) listItemView.findViewById(R.id.publisher);
        publisherTextView.setText(publisher);

        return listItemView;
    }

}
