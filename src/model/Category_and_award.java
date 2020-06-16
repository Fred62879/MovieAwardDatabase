package model;

public class Category_and_award {

    private final String cat_name;
    private final String award_id;

    public Category_and_award(String catName, String awardId) {
        this.cat_name = catName;
        this.award_id = awardId;
    }

    public String getCatName() {
        return this.cat_name;
    }

    public String getUserID() {
        return this.award_id;
    }

}
