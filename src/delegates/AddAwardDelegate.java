package delegates;

import model.Award;

import java.util.ArrayList;
import java.util.List;

public interface AddAwardDelegate {
    public void awarddatabaseSetup();

    public void insertAward(Award model);
    public void deleteAward(int aID);
    public void updateAward(int aID, String name);
    public void selectAward(List<String> award);
    public List<String[]> projectAward(List<String> fields);
    public List<String[]> joinAward(List<String> f1, List<String> f2, String t1, String t2, String c1, String c2);
    public String[][] showAward();
    public String findStaffIds(String role);
    // public String showAward();

    public void addAwardFinished();
}
