//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

public class Organizer {
    private final String org_ID;
    private final String user_ID;

    public Organizer(String orgID, String userID) {
        this.org_ID = orgID;
        this.user_ID = userID;
    }

    public String getOrgID() {
        return this.org_ID;
    }

    public String getUserID() {
        return this.user_ID;
    }
}
