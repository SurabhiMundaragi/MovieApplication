package com.example.mundaragi.movieapplication.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mundaragi on 12/23/17.
 */

public final class NetworkUrlUtils {

    private final static String MOVIE_LIST_URL = "http://api.themoviedb.org/3/movie/";

    //Should add api_key here
    private final static String api_key = "";

    private final static String format = "json";

    static final String FORMAT_PARAM = "mode";

    final static String API_KEY_PARAM = "api_key";

    public static URL buildURL(String movieListOption) {
        Uri buildUri = Uri.parse(MOVIE_LIST_URL + movieListOption).buildUpon().appendQueryParameter(API_KEY_PARAM, api_key).appendQueryParameter(FORMAT_PARAM, format).build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getMovieListResponseFromHttpUrl(URL url) throws IOException {

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }

}
