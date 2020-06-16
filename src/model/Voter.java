package model;

public class Voter {
    private final String voter_ID;
    private final String user_ID;

    public Voter(String voterID, String userID) {
        this.voter_ID = voterID;
        this.user_ID = userID;
    }

    public String getVoterID() {
        return this.voter_ID;
    }

    public String getUserID() {
        return this.user_ID;
    }
}
