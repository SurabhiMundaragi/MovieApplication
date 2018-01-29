package com.example.mundaragi.movieapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mundaragi.movieapplication.utilities.JsonResponseUtils;
import com.example.mundaragi.movieapplication.utilities.NetworkUrlUtils;
import com.example.mundaragi.movieapplication.utilities.ParcelableResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private final static String POPULAR = "popular";
    private final static String TOP_RATED = "top_rated";
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private final static int SPAN_COUNT = 2;
    List<ParcelableResponse> list = new ArrayList<ParcelableResponse>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie);
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this, list, this);

        mRecyclerView.setAdapter(movieAdapter);
        if (isOnline()) {
            loadMovieData(POPULAR);
        }
    }

    private void loadMovieData(String movieOption) {
        if (isOnline()) {
            new FetchMovieListTask().execute(movieOption);
        }
    }

    @Override
    public void onClick(ParcelableResponse movieListObject) {
        Context context = this;
        Class movieDetailClass = MovieDetailActivity.class;
        Intent intentToStartActivity = new Intent(context, movieDetailClass);
        intentToStartActivity.putExtra(Intent.EXTRA_TEXT, movieListObject);
        startActivity(intentToStartActivity);
    }

    public class FetchMovieListTask extends AsyncTask<String, Void, ParcelableResponse[]> {

        //String[] posters;
        ParcelableResponse[] movieItems;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ParcelableResponse[] doInBackground(String... strings) {

            if (strings.length==0){
                return null;
            }
            String option = strings[0];
            URL MovieListUrl = NetworkUrlUtils.buildURL(option);
            try {
                String movieListResponse = NetworkUrlUtils.getMovieListResponseFromHttpUrl(MovieListUrl);
                Gson gson = new Gson();
                JsonResponseUtils response = gson.fromJson(movieListResponse, JsonResponseUtils.class);
                movieItems = new ParcelableResponse[response.results.length];
                for(int i=0;i<response.results.length; i++) {
                     movieItems[i] = new ParcelableResponse(response.getTitle(i), response.getPosterPath(i), response.getOverview(i), response.getVoteAverage(i), response.getReleaseDate(i));
                }
                 return movieItems;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(ParcelableResponse[] movieData) {
            list.clear();
            for(int i=0;i<movieData.length;i++) {
                list.add(movieData[i]);
            }
            if(list !=null) {
                movieAdapter.setMovieData(list);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.popular_movies) {
            loadMovieData(POPULAR);
            return true;
        }

        if(id==R.id.top_rated_movies) {
            loadMovieData(TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
