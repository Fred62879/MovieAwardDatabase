package ui;

// import model.*;

import delegates.AddAwardDelegate;
import model.Award;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBUI extends JFrame implements ActionListener {

    private Container container;
    private GridBagConstraints constraints;

    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;
    private BufferedReader bufferedReader = null;
    private AddAwardDelegate delegate = null;

    // button panel
    private JPanel buttonPane;
    private JButton insert;
    private JButton delete;
    private JButton update;
    private JButton show;
    private JButton quit;

    private int cho = -1;

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
                handleInsertOption();
                break;
            case "delete":
                handleDeleteOption();
                break;
            case "update":
                handleUpdateOption();
                break;
            case "show":
                break;
            case "quit":
                handleQuitOption();
                break;
            default:
                System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                break;
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
        // set panel
        String[] chos = { "insert", "delete", "update", "show", "quit" };
        int i = 0, c = 3;
        JButton cur;
        for (String cho : chos) {
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = i % c;
            constraints.gridy = i / c;
            i++;
            cur = new JButton(cho);
            cur.addActionListener(this);
            buttonPane.add(cur, constraints);
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

    private void display(String txt) {
        container.remove(showPane);
        showPanel();
        showPane.add(new JLabel(txt));
        showPane.setBorder(BorderFactory.createLineBorder(Color.black));
        reconstruct();
    }

    private void reconstruct() {
        container.add(showPane);
        revalidate();
        repaint();
    }

    public int getCho() {
        return this.cho;
    }

    // processing fns
    private void handleDeleteOption() {
        JPanel p = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Enter award ID: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField enterid = new JTextField("");
        p.add(controls, BorderLayout.CENTER);
        controls.add(enterid);

        int input = JOptionPane.showOptionDialog(null, p, "Delete Award",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (input == JOptionPane.OK_OPTION) {
            int aID = INVALID_INPUT;
            while (aID == INVALID_INPUT) {
                aID = Integer.parseInt(enterid.getText());
                if (aID != INVALID_INPUT) {
                    delegate.deleteAward(aID);
                }
            }
        }
    }


    private void handleInsertOption() {
        JPanel p = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Enter award ID: ", SwingConstants.RIGHT));
        labels.add(new JLabel("Enter award name: ", SwingConstants.RIGHT));
        labels.add(new JLabel("Enter award start date: ", SwingConstants.RIGHT));
        labels.add(new JLabel("Enter award end date: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField enterid = new JTextField("");
        JTextField entername = new JTextField("");
        JTextField enterstartdate = new JTextField("");
        JTextField enterenddate = new JTextField("");
        p.add(controls, BorderLayout.CENTER);
        controls.add(enterid);
        controls.add(entername);
        controls.add(enterstartdate);
        controls.add(enterenddate);

        int input = JOptionPane.showOptionDialog(null, p, "Insert Award",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            int aID = INVALID_INPUT;
            while (aID == INVALID_INPUT) {
                aID = Integer.parseInt(enterid.getText());
                String name = null;
                name = entername.getText();
                String startdate = null;
                startdate = enterstartdate.getText();
                String enddate = null;
                enddate = enterenddate.getText();
                if (aID != INVALID_INPUT) {
                    System.out.println(aID + startdate + enddate + name);
                    Award model = new Award(aID, startdate, enddate, name);
                    delegate.insertAward(model);
                }
            }
        }
    }

    private void handleQuitOption() {
        display("quit operation result");
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
        JPanel p = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Enter award ID: ", SwingConstants.RIGHT));
        labels.add(new JLabel("Enter award name: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField enterid = new JTextField("");
        JTextField entername = new JTextField("");
        p.add(controls, BorderLayout.CENTER);
        controls.add(enterid);
        controls.add(entername);

        int input = JOptionPane.showOptionDialog(null, p, "Update Award",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            int aID = INVALID_INPUT;
            while (aID == INVALID_INPUT) {
                aID = Integer.parseInt(enterid.getText());
                String name = null;
                name = entername.getText();
                if (aID != INVALID_INPUT) {
                    delegate.updateAward(aID, name);
                }
            }
        }
    }

    public static void main(String[] args) {
        DBUI dbui = new DBUI();
        dbui.invoke();
    }
}
