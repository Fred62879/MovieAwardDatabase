package model;

public class Poll {
    private final String poll_name;
    private final String award_ID;

    public Poll(String pollName, String awardID) {
        this.poll_name = pollName;
        this.award_ID = awardID;
    }

    public String getPollName() {
        return this.poll_name;
    }

    public String getAwardID() {
        return this.award_ID;
    }
}
