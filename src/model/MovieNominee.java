package model;

public class MovieNominee {
    private final String movie_id;
    private final String nom_ID;

    public MovieNominee(String movId, String nomId) {
        this.movie_id = movId;
        this.nom_ID = nomId;
    }

    public String getNomID() {
        return nom_ID;
    }

    public String getMovieID() {
        return movie_id;
    }

}
