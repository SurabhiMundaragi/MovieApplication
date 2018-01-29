package com.example.mundaragi.movieapplication.utilities;

/**
 * Created by mundaragi on 12/24/17.
 */

public class JsonResponseUtils {

    public results[] results;
    public static class results {
        public String title;
        public String poster_path;
        public String overview;
        public String vote_average;
        public String release_date;
    }

    public String getTitle(int titleCount) {
        return results[titleCount].title;
    }

    public String getPosterPath(int posterPathCount) {
        return results[posterPathCount].poster_path;
    }

    public String getOverview(int overviewCount) {
        return results[overviewCount].overview;
    }

    public String getVoteAverage(int voteAverageCount) {
        return results[voteAverageCount].vote_average;
    }

    public String getReleaseDate(int releaseDateCount) {
        return results[releaseDateCount].release_date;
    }
}
