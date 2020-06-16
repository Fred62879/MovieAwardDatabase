package model;

public class RuleMaking {
    private final String org_ID;
    private final String award_ID;
    private final String rules;

    public RuleMaking(String orgID, String awardID, String rules_) {
        this.org_ID = orgID;
        this.award_ID = awardID;
        this.rules = rules_;
    }

    public String getOrgID() {
        return org_ID;
    }

    public String getAwardID() {
        return award_ID;
    }

    public String getRules() {
        return rules;
    }
}
