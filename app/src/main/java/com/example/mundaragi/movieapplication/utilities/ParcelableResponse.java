package com.example.mundaragi.movieapplication.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mundaragi on 12/27/17.
 */

public class ParcelableResponse implements Parcelable{

    String title;
    String poster_path;
    String overview;
    String vote_average;
    String release_date;

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public ParcelableResponse(String title, String poster_path, String overview, String vote_average, String release_date)
    {
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    private ParcelableResponse(Parcel in){
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        vote_average = in.readString();
        release_date = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(vote_average);
        parcel.writeString(release_date);
    }

    public final static Parcelable.Creator<ParcelableResponse> CREATOR = new Parcelable.Creator<ParcelableResponse>() {
        @Override
        public ParcelableResponse createFromParcel(Parcel parcel) {
            return new ParcelableResponse(parcel);
        }

        @Override
        public ParcelableResponse[] newArray(int i) {
            return new ParcelableResponse[i];
        }

    };
}
