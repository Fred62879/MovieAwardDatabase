package controller;

import database.AwardDatabaseHandler;
import delegates.AddAwardDelegate;
import delegates.LoginWindowDelegate;
import model.Award;
import ui.AddAward;
import ui.DBUI;
import ui.LoginWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class AwardManager implements LoginWindowDelegate, AddAwardDelegate {
    private AwardDatabaseHandler adbHandler = null;
    private LoginWindow loginWindow = null;

    public AwardManager() {
        adbHandler = new AwardDatabaseHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = adbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();
            // AddAward addAward = new AddAward();
            // addAward.setupDatabase(this);
            // addAward.showMainMenu(this);
            DBUI dbui = new DBUI(this);
            dbui.setupDatabase(this);
            dbui.invoke(this);
        } else {
            loginWindow.handleLoginFailed();
            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    public void awarddatabaseSetup() {
        adbHandler.awarddatabaseSetup();
    }

    public void insertAward(Award model) {
        adbHandler.insertAward(model);
    }

    public void deleteAward(int aID) {
        adbHandler.deleteAward(aID);
    }

    public void updateAward(int aID, String name) {
        adbHandler.updateAward(aID, name);
    }


//    public void selectAward(String award) {
//        adbHandler.selectAward(award);
    public String findStaffIds(String role) {
        return adbHandler.findStaffIds(role);
    }

    public void selectAward(List<String> fields) {
        // adbHandler.selectAward(fields);
    }

    public List<String[]> projectAward(List<String> fields) { return adbHandler.projectAward(fields); }

    public List<String[]> joinAward(List<String> f1, List<String> f2, String t1, String t2, String c1, String c2) { return adbHandler.joinAward(f1, f2, t1, t2, c1, c2); }

    public String[][] showAward() {
        Award[] models = adbHandler.getAwardInfo();
        String[][] res = new String[models.length][4];

        for (int i = 0; i < models.length; i++) {
            Award model = models[i];
            String[] cur = { Integer.toString(model.getAID()), model.getName(), model.getStartDate(), model.getEndDate() };
            res[i] = cur;
            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getAID());
            System.out.printf("%-20.20s", model.getName());
            System.out.printf("%-20.20s", model.getStartDate());
            System.out.printf("%-20.20s", model.getEndDate());
            System.out.println();
        }
        return res;
    }

    public void addAwardFinished() {
        adbHandler.close();
        adbHandler = null;

        System.exit(0);
    }

    public static void main(String args[]) {
        AwardManager awardManager = new AwardManager();
        awardManager.start();
    }
}
