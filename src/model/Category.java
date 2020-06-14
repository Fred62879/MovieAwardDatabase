package model;

public class Category {
    private final String catID;
    private final String name;
    private final String description;

    public Category(String catID, String name, String description) {
        this.catID = catID;
        this.description = description;
        this.name = name;
    }

    public String getCatID() {
        return catID;
    }

    public String getCatName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
