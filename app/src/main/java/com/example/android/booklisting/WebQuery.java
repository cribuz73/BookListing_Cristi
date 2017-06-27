package com.example.android.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.booklisting.MainActivity.LOG_TAG;

/**
 * Created by Cristi on 6/26/2017.
 */

public class WebQuery {
    public static List<Book> fetchBookdata(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Book> book1 = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return book1;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractFeatureFromJson(String bookJSON) {
        List<Book> books = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        //extracting the JSON response//

        try {

            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            if (baseJsonResponse.has("items")) {

                JSONArray itemsArray = baseJsonResponse.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject currentBook = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                    String title = volumeInfo.getString("title");
                    String year = volumeInfo.getString("publishedDate");
                    String publisher = volumeInfo.getString("publisher");
                    String subtitle = " ";
                    if (volumeInfo.has("subtitle")) {
                        subtitle = volumeInfo.getString("subtitle");
                    } else {
                        return null;
                    }
                    String author = "Author: ";
                    if (volumeInfo.has("authors")) {
                        JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                        for (int j = 0; j < authorsArray.length(); j++) {
                            author = author + authorsArray.getString(j) + ", ";
                        }
                        //author = author.substring(0, author.length() - 1);

                    } else {
                        author = "No authors found";
                    }


                    Book book = new Book(title, subtitle, author, year, publisher);
                    books.add(book);
                }
            } else {
                return null;
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }

}

