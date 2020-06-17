package ui;

import delegates.AddAwardDelegate;
import model.Award;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DBUI extends JFrame implements ActionListener {

    private Container container;
    private GridBagConstraints constraints;
    private JPanel buttonPane;
    private JPanel showPane;
    private JFrame frame;
    private JPanel currentPane;
    private JTabbedPane tabbedPane = new JTabbedPane();

    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;
    private BufferedReader bufferedReader = null;
    private AddAwardDelegate delegate = null;

    private int tabnumber = 0;
    private FlowLayout flayout = new FlowLayout(FlowLayout.CENTER, 5, 0);


    public DBUI(AddAwardDelegate delegate) {
        super("MovieAward DBMS GUI");
        this.delegate = delegate;
    }

    public void invoke(AddAwardDelegate delegate) {
        this.delegate = delegate;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DBUI(delegate).initialize();
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

        JPanel pnlTab = new JPanel(flayout);
        tabbedPane.setVisible(true);
        add(tabbedPane);
        JComponent panel1 = makeTextPanel("");
        tabbedPane.addTab("Tab 1", null, panel1,
                "Does nothing");
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, pnlTab);

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public JPanel buttonPanel() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        // set panel
        String[] chos = { "insert", "delete", "update", "selection", "projection",
                "join", "aggregation", "nestedAgg", "division", "show", "addnom", "quit" };
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
            case "selection":
                handleSelectionOption();
            case "projection":
                handleProjectionOption();
            case "join":
                handleJoinOption();
            case "aggregate":
                handleAggregateOption();
            case "nestedAgg":
                handleNestedAggOption();
            case "division":
                handleDivisionOption();
            case "show":
                handleShowOption();
                break;
            case "addnom":
                handleInsertNomineeOption();
                break;
            case "quit":
                handleQuitOption();
                break;
            default:
                System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                break;
        }
    }

    // processing fns
    public void setupDatabase(AddAwardDelegate delegate) {
        this.delegate = delegate;
        if (delegate == null) System.out.println("3delegate = " + delegate);
        delegate.awarddatabaseSetup();
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
                String name = entername.getText();
                String startdate = enterstartdate.getText();
                String enddate = enterenddate.getText();
                if (aID != INVALID_INPUT) {
                    System.out.println(aID + startdate + enddate + name);
                    Award model = new Award(aID, startdate, enddate, name);
                    if (delegate == null) System.out.println("here");
                    delegate.insertAward(model);
                    createAwardTab(name, aID, startdate, enddate);
                }
            }
        }
    }

    private void handleInsertNomineeOption() {
        JPanel p = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Enter staff ID: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField enterid = new JTextField("");
        p.add(controls, BorderLayout.CENTER);
        controls.add(enterid);

        int input = JOptionPane.showOptionDialog(null, p, "Add Nominee",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            int id = INVALID_INPUT;
            while (id == INVALID_INPUT) {
                id = Integer.parseInt(enterid.getText());
                if (id != INVALID_INPUT) {
                    //Somehow need to get name, dob, role from database from id
                    addDestComp("name", "id", "dob", "role");
                }
            }
        }
    }

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

    private void handleSelectionOption() {
        JPanel p = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Enter staff role: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField enterrole = new JTextField("");
        p.add(controls, BorderLayout.CENTER);
        controls.add(enterrole);

        int input = JOptionPane.showOptionDialog(null, p, "Find Staff with Role",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
                String role = null;
                role = enterrole.getText();
                String roleIds = delegate.findStaffIds(role);
                System.out.println(roleIds);
                displayStringDialog(roleIds);
        }
    }

    private void handleProjectionOption() {}

    private void handleJoinOption() {}

    private void handleAggregateOption() {}

    private void handleNestedAggOption() {}

    private void handleDivisionOption() {}

    private void handleShowOption() {
        delegate.showAward();
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

    private void displayStringDialog(String text) {
        JOptionPane.showMessageDialog(frame,
                "Requested Data is as given: " + text, "Requested Data", JOptionPane.ERROR_MESSAGE);
    }

    //MODIFIES: this
    //EFFECTS: Creates start tab
    private void createAwardTab(String name, Integer aID, String startdate, String enddate) {
        tabnumber +=1;
        String title = name;
        JPanel mainPane = new JPanel();
        currentPane = mainPane;
        TitledBorder titled = BorderFactory.createTitledBorder("Award Information");
        addCompForBorder(titled, mainPane, aID, startdate, enddate);
        tabbedPane.add(title, mainPane);
        initTabComponent(tabnumber);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        setSize(new Dimension(40, 50));
        setVisible(true);
    }

    //EFFECTS: Given a border, adds labels inside it
    void addCompForBorder(Border border, Container container, Integer aID, String startdate, String enddate) {
        JLabel inputs = new JLabel("Award ID: " + aID + " | Start Date: " + startdate + " | End Date: " + enddate);
        JPanel comp = new JPanel(new GridLayout(2, 1), true);
        comp.add(inputs);
        comp.setBorder(border);

        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(comp);
    }

    //MODIFIES: this
    //EFFECTS: Creates new buttontab
    private void initTabComponent(int i) {
        tabbedPane.setTabComponentAt(i,
                new ButtonTab(tabbedPane));
    }

    //EFFECTS: Makes new text panel
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    //EFFECTS: Adds labels inside Destination border, and creates new Destination border
    private void addDestComp(String name, String iD, String dob, String role) {
        JLabel inputs = new JLabel("ID: " + iD + " | Date of Birth: " + dob + " | Role: " + role);
        JPanel comp = new JPanel(new GridLayout(2, 1), true);
        comp.add(inputs);
        TitledBorder border = BorderFactory.createTitledBorder("Nominee: " + name);
        comp.setBorder(border);

        currentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        currentPane.add(comp);
    }

    public static void main(String[] args) {
        // DBUI dbui = new DBUI();
        // dbui.invoke();
    }
}
