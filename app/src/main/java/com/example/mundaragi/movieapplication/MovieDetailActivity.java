package com.example.mundaragi.movieapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mundaragi.movieapplication.utilities.ParcelableResponse;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private String movieTitle;
    private TextView movieTitleTextView;
    private String overview;
    private TextView overviewTextView;
    private String releaseDate;
    private TextView releaseDateTextView;
    private String voteAverage;
    private TextView voteAverageTextView;
    private ImageView posterPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieTitleTextView = (TextView) findViewById(R.id.movie_title);
        posterPath = (ImageView) findViewById(R.id.poster_path);
        overviewTextView = (TextView) findViewById(R.id.overview);
        releaseDateTextView = (TextView) findViewById(R.id.release_date);
        voteAverageTextView = (TextView) findViewById(R.id.vote_average);

        Bundle bundle = getIntent().getExtras();
        ParcelableResponse customObj = bundle.getParcelable(Intent.EXTRA_TEXT);
        movieTitleTextView.setText(customObj.getTitle());
        String baseURL = "http://image.tmdb.org/t/p/w342/";
        Picasso.with(posterPath.getContext()).load(baseURL + customObj.getPoster_path()).into(posterPath);
        releaseDateTextView.setText(customObj.getRelease_date());
        voteAverageTextView.setText(customObj.getVote_average());
        overviewTextView.setText(customObj.getOverview());
    }
}
