package ui;

// import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBUI extends JFrame implements ActionListener {

    private Container container;
    private GridBagConstraints constraints;

    // button panel
    private JPanel buttonPane;
    private JButton insert;
    private JButton delete;
    private JButton update;
    private JButton show;
    private JButton quit;

    private int cho;

    private JButton submit;
    // display panel
    private JPanel showPane;
    private JTable table;

    public DBUI() {
        super("MovieAward DBMS GUI");
    }

    public void invoke() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DBUI().initialize();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initialize user interface
    public void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setPreferredSize(new Dimension(300,400));
        container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        constraints = new GridBagConstraints();

        container.add(buttonPanel());
        container.add(showPanel());

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // EFFECTS: respond to user action
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "insert":
                this.cho = 1;
            case "delete":
                this.cho = 2;
            case "update":
                this.cho = 3;
            case "show":
                this.cho = 4;
            case "quit":
                this.cho = 5;
        }
    }

    private void button(String txt) {
        JButton res = new JButton(txt);
        res.addActionListener(this);
        constraints.ipady = 0; // reset
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 2;
        buttonPane.add(res, constraints);
    }

    public JPanel buttonPanel() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        // create field
        constraints.fill = GridBagConstraints.HORIZONTAL;
        // set panel
        String[] chos = { "insert", "delete", "update", "show", "quit" };

        constraints.ipady = 0; // reset
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 2;
        for (String cho : chos) {
            JButton res = new JButton(cho);
            res.addActionListener(this);
            buttonPane.add(res, constraints);
            // button(cho);
        }
        buttonPane.setBorder(BorderFactory.createLineBorder(Color.black));

        return buttonPane;
    }

    public JPanel showPanel() {
        showPane = new JPanel();
        showPane.setLayout(new GridBagLayout());
        // create field
        constraints.fill = GridBagConstraints.HORIZONTAL;
        buttonPane.setBorder(BorderFactory.createLineBorder(Color.black));

        return showPane;
    }

    private void display() {
        container.remove(showPane);
        showPane.add(new JLabel("Dummy"));
        showPane.setBorder(BorderFactory.createLineBorder(Color.black));
        reconstruct();
    }

    private void reconstruct() {
        container.add(showPane);
        revalidate();
        repaint();
    }


    // processing fns
    public void process() {

    }

    private void handleDeleteOption() {
//        int aID = INVALID_INPUT;
//        while (aID == INVALID_INPUT) {
//            System.out.print("Please enter the award ID you wish to delete: ");
//            aID = readInteger(false);
//            if (aID != INVALID_INPUT) {
//                delegate.deleteAward(aID);
//            }
//        }

    }

    /*
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

        // branch address is allowed to be null so we don't need to repeatedly ask for the addres
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

     */


}
