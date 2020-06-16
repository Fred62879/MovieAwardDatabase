package model;

public class Movie {
    private final String movie_id;
    private final String name;
    private final String genre;
    private final String year;

    public Movie(String movieId, String name_, String genre_, String year_) {
        this.movie_id = movieId;
        this.name = name_;
        this.genre = genre_;
        this.year = year_;
    }

    public String getMovieID() {
        return movie_id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() { return genre; }

    public String getYear() { return year; }

}
