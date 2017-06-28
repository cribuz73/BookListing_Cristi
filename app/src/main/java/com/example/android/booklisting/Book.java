package com.example.android.booklisting;

import android.util.Log;

/**
 * Created by Cristi on 6/26/2017.
 */

public class Book {

    private static final String LOG_TAG = Book.class.getSimpleName();

    private String mBook_title;

    private String mBook_subtitle;

    private String mBook_author;

    private String mBook_year;

    private String mBook_publisher;


    public Book(String vTitle, String vSubtitle, String vAuthor, String vYear, String vPublisher) {

        Log.i(LOG_TAG, "TEST : using Book JavaClass");

        mBook_title = vTitle;
        mBook_subtitle = vSubtitle;
        mBook_author = vAuthor;
        mBook_year = vYear;
        mBook_publisher = vPublisher;
    }

    public String getBookTitle() {
        return mBook_title;
    }

    public String getBookSubtitle() {
        return mBook_subtitle;
    }

    public String getBookAuthor() {
        return mBook_author;
    }

    public String getBookYear() {
        return mBook_year;
    }

    public String getBookPublisher() {
        return mBook_publisher;
    }

}


