package model;

public class Nominee {
    private final String nom_ID;
    private final int vote_count;
    private final String award_id;

    public Nominee(String nomID, int voteCount, String awardId) {
        this.nom_ID = nomID;
        this.vote_count = voteCount;
        this.award_id = awardId;
    }

    public String getNomID() {
        return nom_ID;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public String getAwardID() {
        return award_id;
    }
}
