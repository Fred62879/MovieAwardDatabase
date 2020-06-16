package model;

public class MovieCategoryAwardTo {
    private final String award_ID;
    private final String movie_ID;

    public MovieCategoryAward(String awardID, String movieID) {
        this.award_ID = awardID;
        this.movie_ID = movieID;
    }

    public String getAwardID() {
        return this.award_ID;
    }

    public String getMovieID() {
        return this.movie_ID;
    }
}
