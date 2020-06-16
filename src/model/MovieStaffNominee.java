package model;

public class MovieStaffNominee {

    private final String staff_id;
    private final String nom_ID;

    public MovieStaffNominee(String staffId, String nomId) {
        this.staff_id = staffId;
        this.nom_ID = nomId;
    }

    public String getNomID() {
        return nom_ID;
    }

    public String getStaffID() {
        return staff_id;
    }
}
