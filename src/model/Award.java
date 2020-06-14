package model;

public class Award {
    private final int aID;
    private final String startDate;
    private final String endDate;
    private final String name;

    public Award(int aID, String startDate, String endDate, String name) {
        this.aID = aID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }

    public int getAID() {
        return aID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getName() {
        return name;
    }

    public String getEndDate() {
        return endDate;
    }
}

