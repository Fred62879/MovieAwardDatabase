package ui;

import delegates.AddAwardDelegate;
import model.Award;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class AddAward {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;

    private BufferedReader bufferedReader = null;
    private AddAwardDelegate delegate = null;

    public AddAward() {
    }

    public void setupDatabase(AddAwardDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while(choice != 1 && choice != 2) {
            System.out.println("If you have a table called Branch in your database (capitialization of the name does not matter), it will be dropped and a new Branch table will be created.\nIf you want to proceed, enter 1; if you want to quit, enter 2.");

            choice = readInteger(false);

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        delegate.awarddatabaseSetup();
                        break;
                    case 2:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
                        break;
                }
            }
        }
    }

    public void showMainMenu(AddAwardDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println();
            System.out.println("1. Insert Award");
            System.out.println("2. Delete Award");
            System.out.println("3. Update Award Name");
            System.out.println("4. Show Award");
            System.out.println("5. Quit");
            System.out.print("Please choose one of the above 5 options: ");

            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleInsertOption();
                        break;
                    case 2:
                        handleDeleteOption();
                        break;
                    case 3:
                        handleUpdateOption();
                        break;
                    case 4:
                        delegate.showAward();
                        break;
                    case 5:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }

    private void handleDeleteOption() {
        int aID = INVALID_INPUT;
        while (aID == INVALID_INPUT) {
            System.out.print("Please enter the award ID you wish to delete: ");
            aID = readInteger(false);
            if (aID != INVALID_INPUT) {
                delegate.deleteAward(aID);
            }
        }
    }

    private void handleInsertOption() {
        int aID = INVALID_INPUT;
        while (aID == INVALID_INPUT) {
            System.out.print("Please enter the award ID you wish to insert: ");
           aID = readInteger(false);
        }

        String name = null;
        while (name == null || name.length() <= 0) {
            System.out.print("Please enter the award name you wish to insert: ");
            name = readLine().trim();
        }

        // branch address is allowed to be null so we don't need to repeatedly ask for the address
        System.out.print("Please enter the award start date you wish to insert: ");
        String startdate = readLine().trim();
        if (startdate.length() == 0) {
            startdate = null;
        }

        String enddate = null;
        while (enddate == null || enddate.length() <= 0) {
            System.out.print("Please enter the award end date you wish to insert: ");
            enddate = readLine().trim();
        }

        Award model = new Award(aID, startdate, enddate, name);
        delegate.insertAward(model);
    }

    private void handleQuitOption() {
        System.out.println("Good Bye!");

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("IOException!");
            }
        }

        delegate.addAwardFinished();
    }

    private void handleUpdateOption() {
        int aID = INVALID_INPUT;
        while (aID == INVALID_INPUT) {
            System.out.print("Please enter the award ID you wish to update: ");
           aID = readInteger(false);
        }

        String name = null;
        while (name == null || name.length() <= 0) {
            System.out.print("Please enter the award name you wish to update: ");
            name = readLine().trim();
        }

        delegate.updateAward(aID, name);
    }

    private int readInteger(boolean allowEmpty) {
        String line = null;
        int input = INVALID_INPUT;
        try {
            line = bufferedReader.readLine();
            input = Integer.parseInt(line);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        } catch (NumberFormatException e) {
            if (allowEmpty && line.length() == 0) {
                input = EMPTY_INPUT;
            } else {
                System.out.println(WARNING_TAG + " Your input was not an integer");
            }
        }
        return input;
    }

    private String readLine() {
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }
}
