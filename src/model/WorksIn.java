package model;

public class WorksIn {

    private final String movie_id;
    private final String staff_id;
    private final String name;

    public WorksIn(String movieId, String staffId, String name_) {
        this.movie_id = movieId;
        this.staff_id = staffId;
        this.name = name_;
    }

    public String getMovieID() {
        return movie_id;
    }

    public String getStaffID() {
        return staff_id;
    }

    public String getName() { return name; }

}
