package delegates;

import model.Award;
import java.util.List;

public interface AddAwardDelegate {
    public void awarddatabaseSetup();

    public void insertAward(Award model);
    public void deleteAward(int aID);
    public void updateAward(int aID, String name);
    public void selectAward(String award);
    public void projectAward(List<String> fields);
    public void joinAward(List<String> fields);

    public String[][] showAward();

    public void addAwardFinished();
}
