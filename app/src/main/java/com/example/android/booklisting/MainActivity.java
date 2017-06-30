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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.android.booklisting.R.id.authorImput;
import static com.example.android.booklisting.R.id.search;
import static com.example.android.booklisting.R.id.titleImput;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;
    public BookAdapter mAdapter;
    private String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(LOG_TAG, "TEST : using on create in mainActivity");

        final LoaderManager loaderManager = getLoaderManager();

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        listView.setAdapter(mAdapter);

        final View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        final EditText getTitle = (EditText) findViewById(titleImput);
        final EditText getAuthor = (EditText) findViewById(authorImput);

        final Button SearchButton = (Button) findViewById(search);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clear();

                getTitle.setVisibility(View.GONE);
                getAuthor.setVisibility(View.GONE);
                SearchButton.setVisibility(View.GONE);
                mEmptyStateTextView.setText("");
                String titleImput = getTitle.getText().toString().trim();
                String authorImput = getAuthor.getText().toString().trim();
                BOOK_REQUEST_URL = BOOK_REQUEST_URL + titleImput + "+" + "inauthor:" + authorImput;
                Log.v("my_tag", "url being created is: " + BOOK_REQUEST_URL);

                if (iConnection()) {

                    loadingIndicator.setVisibility(View.VISIBLE);
                    Log.i(LOG_TAG, "TEST : initialising loader");
                    loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {
                    View loadingIndicator = findViewById(R.id.progress_bar);
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setText(R.string.no_connection);
                }
            }
        });

    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "TEST : using onCreateLoader");
        Log.v("my_tag", "url being created is: " + BOOK_REQUEST_URL);

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

    private boolean iConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
