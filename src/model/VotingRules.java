package model;

public class VotingRules {
    private final String rule_ID;
    private final String award_ID;

    public VotingRules(String ruleID, String awardID) {
        this.rule_ID = ruleID;
        this.award_ID = awardID;
    }

    public String getRuleID() {
        return this.rule_ID;
    }

    public String getAwardID() {
        return this.award_ID;
    }
}
