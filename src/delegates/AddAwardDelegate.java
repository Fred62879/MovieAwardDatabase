package delegates;

import model.Award;

public interface AddAwardDelegate {
    public void awarddatabaseSetup();

    public void deleteAward(int aID);
    public void insertAward(Award model);
    public void showAward();
    public void updateAward(int aID, String name);

    public void addAwardFinished();
}
