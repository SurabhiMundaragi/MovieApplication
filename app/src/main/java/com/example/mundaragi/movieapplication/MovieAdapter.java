package com.example.mundaragi.movieapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.mundaragi.movieapplication.utilities.ParcelableResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mundaragi on 12/22/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    //private String[] movieData;
    private Activity MovieActivity;
    private int screenHeight;
    private int screenWidth;
    private List<ParcelableResponse> movieList;

    public MovieAdapter(Activity movieActivity, List<ParcelableResponse> movieData, MovieAdapterOnClickHandler ClickHandler) {
        movieList = movieData;
        mClickHandler = ClickHandler;
        WindowManager wm = (WindowManager) movieActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    private final MovieAdapterOnClickHandler mClickHandler;
    public interface MovieAdapterOnClickHandler {
        void onClick(ParcelableResponse movieListObject);
    }
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView movieImageView;
        public MovieAdapterViewHolder(View view) {
            super(view);
            movieImageView = (ImageView ) view.findViewById(R.id.movie_item_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            ParcelableResponse movieItem = movieList.get(adapterPosition);
            mClickHandler.onClick(movieItem);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForMovieListItem = R.layout.movie_list_ltem;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForMovieListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        final ParcelableResponse movieListObject = movieList.get(position);
        String baseURL = "http://image.tmdb.org/t/p/w780/";
        Log.d("surbhi", "onBindViewHolder: " + screenWidth);
        Picasso.with(movieAdapterViewHolder.movieImageView.getContext()).load(baseURL + movieListObject.getPoster_path()).resize(screenWidth / 2, screenHeight / 2)
                .into(movieAdapterViewHolder.movieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == movieList)
        return 0;
        return movieList.size();
    }

    public void setMovieData(List<ParcelableResponse> moviesData) {
        movieList = moviesData;
        notifyDataSetChanged();
    }
}
