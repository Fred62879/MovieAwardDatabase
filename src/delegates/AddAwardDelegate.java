package delegates;

import model.Award;
import java.util.List;

public interface AddAwardDelegate {
    public void awarddatabaseSetup();

    public void insertAward(Award model);
    public void deleteAward(int aID);
    public void selectAward(List<String> fields);
    public void updateAward(int aID, String name);
    public String findStaffIds(String role);
    public String showAward();

    public void addAwardFinished();
}
