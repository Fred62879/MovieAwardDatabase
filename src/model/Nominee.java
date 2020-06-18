package model;

public class Nominee {
    private final int nom_ID;
    private final int vote_count;
    private final int id;
    private final int award_id;

    public Nominee(int nomID, int voteCount, int id, int  awardId) {
        this.nom_ID = nomID;
        this.vote_count = voteCount;
        this.id = id;
        this.award_id = awardId;
    }

    public int getNomID() {
        return nom_ID;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public int getID() {
        return id;
    }

    public int getAwardID() {
        return award_id;
    }
}
