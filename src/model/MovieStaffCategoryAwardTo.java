package model;

public class MovieStaffCategoryAwardTo {
    private final String award_ID;
    private final String staff_ID;

    public MovieCategoryAward(String awardID, String staffID) {
        this.award_ID = awardID;
        this.staff_ID = staffID;
    }

    public String getAwardID() {
        return this.award_ID;
    }

    public String getStaffID() {
        return this.staff_ID;
    }
}
