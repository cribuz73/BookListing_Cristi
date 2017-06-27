package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


/**
 * Created by Cristi on 6/26/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {


    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = BookLoader.class.getName();

    private String mUrl;


    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<Book> books = WebQuery.fetchBookdata(mUrl);
        return books;
    }

}
