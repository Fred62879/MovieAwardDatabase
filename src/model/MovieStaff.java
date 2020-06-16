package model;

public class MovieStaff {
    private final String id;
    private final String name;
    private final String dob;
    private final String genre;
    private final String role;

    public MovieStaff(String id_, String name_, String dob_, String genre_, String role_) {
        this.id = id_;
        this.name = name_;
        this.dob = dob_;
        this.genre = genre_;
        this.role = role_;
    }

    public String getMSID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDob() { return dob; }

    public String getGenre() { return genre; }

    public String getRole() { return role; }

}
