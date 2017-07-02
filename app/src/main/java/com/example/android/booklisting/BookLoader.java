package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Cristi on 6/26/2017.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {


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
        Log.i(LOG_TAG, "TEST : using onStartLoading");

        forceLoad();
    }


    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<Book> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        ArrayList<Book> books = WebQuery.fetchBookdata(mUrl);
        return books;
    }

}
