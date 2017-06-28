package com.example.android.booklisting;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;
    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=15";


    public BookAdapter mAdapter;

    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(LOG_TAG, "TEST : using on create in mainActivity");

        //**  public String startSearch (View v) {
        //       EditText getTitle = (EditText) findViewById(R.id.titleImput);
        //     String titleImput = getTitle.getText().toString().trim();

        //     EditText getAuthor = (EditText) findViewById(R.id.authorImput);
        //     String authorImput = getAuthor.getText().toString().trim();

//        String urlFinal = "https://www.googleapis.com/books/v1/volumes?q=" + titleImput + "+" + "inauthor:" + authorImput;

//        return urlFinal;

//    }

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        listView.setAdapter(mAdapter);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            Log.i(LOG_TAG, "TEST : initialising loader");

            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_connection);
        }

    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "TEST : using onCreateLoader");

        return new BookLoader(this, BOOK_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> books) {

        Log.i(LOG_TAG, "TEST : using onLoadFinished");

        View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_books);

        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        Log.i(LOG_TAG, "TEST : using onLoadReset");

        mAdapter.clear();
    }

}
