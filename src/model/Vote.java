package model;

public class Vote {
    private final String vote_id;
    private final String award_id;
    private final String voter_id;
    private final String nom_id;

    public Vote(String voteId, String awardId, String voterId, String nomId) {
        this.vote_id = voteId;
        this.voter_id = voterId;
        this.award_id = awardId;
        this.nom_id = nomId;
    }

    public String getVoteID() {
        return vote_id;
    }

    public String getAwardID() {
        return award_id;
    }

    public String getVoterId() { return voter_id; }

    public String getNomId() { return nom_id; }
}
