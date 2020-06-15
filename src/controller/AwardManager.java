package controller;

import database.AwardDatabaseHandler;
import delegates.AddAwardDelegate;
import delegates.LoginWindowDelegate;
import model.Award;
import ui.AddAward;
import ui.DBUI;
import ui.LoginWindow;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class AwardManager implements LoginWindowDelegate, AddAwardDelegate {
    private AwardDatabaseHandler adbHandler = null;
    private LoginWindow loginWindow = null;
    private DBUI dbui;

    public AwardManager() {
        adbHandler = new AwardDatabaseHandler();
        dbui = new DBUI();
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

            AddAward addAward = new AddAward();
            addAward.setupDatabase(this);
            dbui.invoke();
            // addAward.showMainMenu(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    public void insertAward(Award model) {
        adbHandler.insertAward(model);
    }

    public void awarddatabaseSetup() {
        adbHandler.awarddatabaseSetup();
    }

    public void deleteAward(int aID) {
        adbHandler.deleteAward(aID);
    }

    public void updateAward(int aID, String name) {
        adbHandler.updateAward(aID, name);
    }

    public void showAward() {
        Award[] models = adbHandler.getAwardInfo();

        for (int i = 0; i < models.length; i++) {
            Award model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getAID());
            System.out.printf("%-20.20s", model.getName());
            System.out.printf("%-20.20s", model.getStartDate());
            System.out.printf("%-20.20s", model.getEndDate());
            System.out.println();
        }
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
