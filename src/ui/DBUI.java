package ui;

import delegates.AddAwardDelegate;
import model.Award;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBUI extends JFrame implements ActionListener {

    private Container container;
    private GridBagConstraints constraints;
    private JPanel buttonPane;
    private JPanel showPane;
    //<<<<<<< HEAD
    private JTable table;
    //
    private JFrame frame;
    private JPanel currentPane;
    private JTabbedPane tabbedPane = new JTabbedPane();
    // >>>>>>> refs/remotes/origin/master

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

    public DBUI() {
        super("MovieAward DBMS GUI");
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

        this.setPreferredSize(new Dimension(500, 600));
        container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        constraints = new GridBagConstraints();

        container.add(buttonPanel());
        container.add(showPanel());

//        JPanel pnlTab = new JPanel(flayout);
//        tabbedPane.setVisible(true);
//        add(tabbedPane);
//        JComponent panel1 = makeTextPanel("");
//        tabbedPane.addTab("Tab 1", null, panel1,
//                "Does nothing");
//        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, pnlTab);

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
                break;
            case "projection":
                handleProjectionOption();
                break;
            case "join":
                handleJoinOption();
                break;
            case "aggregate":
                handleAggregateOption();
                break;
            case "nestedAgg":
                handleNestedAggOption();
                break;
            case "division":
                handleDivisionOption();
                break;
            case "show":
                String[] colnm = {"ID", "StartDate", "EndDate", "AwardName"};
                String[][] data = delegate.showAward();
                handleShowOption(colnm, data);
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
                    // createAwardTab(name, aID, startdate, enddate);
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
                    // addDestComp("name", "id", "dob", "role");
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
// <<<<<<< HEAD
//        labels.add(new JLabel("ID condition: ", SwingConstants.RIGHT));
//        // labels.add(new JLabel("Award name condition: ", SwingConstants.RIGHT));
//        // labels.add(new JLabel("Start date condition: ", SwingConstants.RIGHT));
//        // labels.add(new JLabel("End date condition: ", SwingConstants.RIGHT));
//        p.add(labels, BorderLayout.WEST);
//
//        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
//        JTextField f1 = new JTextField("");
//        // JTextField f2 = new JTextField("");
//        // JTextField f3 = new JTextField("");
//        // JTextField f4 = new JTextField("");
//
//        p.add(controls, BorderLayout.CENTER);
//        controls.add(f1);
//        // controls.add(f2);
//        // controls.add(f3);
//        // controls.add(f4);
//
//        int input = JOptionPane.showOptionDialog(null, p, "Award Selection",
// =======
        labels.add(new JLabel("Enter staff role: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField enterrole = new JTextField("");
        p.add(controls, BorderLayout.CENTER);
        controls.add(enterrole);

        int input = JOptionPane.showOptionDialog(null, p, "Find Staff with Role",
// >>>>>>> refs/remotes/origin/master
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        // JTextField[] cur = {f1}; // , f2, f3, f4};
        // List<String> res = new ArrayList<>();
        // String res = "";
        if (input == JOptionPane.OK_OPTION) {
            String role = null;
            role = enterrole.getText();
            String roleIds = delegate.findStaffIds(role);
            System.out.println(roleIds);
            displayStringDialog(roleIds);
        }
        // delegate.selectAward(res);
    }

    private void handleProjectionOption() {
        JPanel p = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Selected Fields: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField f1 = new JTextField("");
        JTextField f2 = new JTextField("");
        JTextField f3 = new JTextField("");
        JTextField f4 = new JTextField("");

        p.add(controls, BorderLayout.CENTER);
        controls.add(f1);
        controls.add(f2);
        controls.add(f3);
        controls.add(f4);

        int input = JOptionPane.showOptionDialog(null, p, "Award Projection",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        JTextField[] cur = {f1, f2, f3, f4};
        List<String> res = new ArrayList<>();
        if (input == JOptionPane.OK_OPTION) {
            for (JTextField j : cur) {
                String name = j.getText();
                if (!name.equals("")) res.add(name);
            }
        }
        List<String[]> ret = delegate.projectAward(res);

        String[][] data = new String[ret.size()][ret.get(0).length];
        for (int i = 0; i < ret.size(); i++) data[i] = ret.get(i);
        String[] nm = res.toArray(new String[res.size()]);
        handleShowOption(nm, data);
    }

    private void handleJoinOption() {
        JPanel p = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Selected Fields: ", SwingConstants.RIGHT));
        labels.add(new JLabel("               : ", SwingConstants.RIGHT));
        labels.add(new JLabel("               : ", SwingConstants.RIGHT));
        labels.add(new JLabel("               : ", SwingConstants.RIGHT));
        labels.add(new JLabel(" Tables: ", SwingConstants.RIGHT));
        labels.add(new JLabel(" Conditions: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 2, 2, 2));
        JTextField f1 = new JTextField(""); JTextField f7 = new JTextField("");
        JTextField f2 = new JTextField(""); JTextField f8 = new JTextField("");
        JTextField f3 = new JTextField(""); JTextField f9 = new JTextField("");
        JTextField f4 = new JTextField(""); JTextField f10 = new JTextField("");
        JTextField f5 = new JTextField(""); JTextField f11 = new JTextField("");
        JTextField f6 = new JTextField(""); JTextField f12 = new JTextField("");

        p.add(controls, BorderLayout.CENTER);
        controls.add(f1); controls.add(f2);
        controls.add(f3); controls.add(f4);
        controls.add(f5); controls.add(f6);
        controls.add(f7); controls.add(f8);
        controls.add(f9); controls.add(f10);
        controls.add(f11); controls.add(f12);

        int input = JOptionPane.showOptionDialog(null, p, "Award Selection",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        JTextField[] c1 = {f1, f3, f5, f7}, c2 = {f2, f4, f6, f8};
        List<String> field1 = new ArrayList<>(), field2 = new ArrayList<>();
        String t1 = "", t2 = "", con1 = "", con2 = "";
        if (input == JOptionPane.OK_OPTION) {
            for (int i = 0; i < 4; i++) {
                String cond1 = c1[i].getText();
                if (!cond1.equals("")) field1.add(cond1);
                String cond2 = c2[i].getText();
                if (!cond2.equals("")) field2.add(cond2);
            }
            t1 = f9.getText(); t2 = f10.getText();
            con1 = f11.getText(); con2 = f12.getText();
        }

        List<String[]> res = delegate.joinAward(field1, field2, t1, t2, con1, con2);
        String[] af1 = field1.toArray(new String[field1.size()]), af2 = field2.toArray(new String[field2.size()]);
        String[] nm = Arrays.copyOf(af1, af1.length + af2.length);
        System.arraycopy(af2, 0, nm, af1.length, af2.length);
        String[][] data = new String[res.size()][res.get(0).length];
        for (int i = 0; i < res.size(); i++) data[i] = res.get(i);
        handleShowOption(nm, data);
    }

    private void handleAggregateOption() {
    }

    private void handleNestedAggOption() {
    }

    private void handleDivisionOption() {
    }

    private void handleShowOption(String[] colnm, String[][] data) {
        /*
        DefaultTableModel model = new DefaultTableModel(data, colnm);
        table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
         */
        JTable table = new JTable(data, colnm);
        table.setFillsViewportHeight(true);
        table = new JTable(data, colnm);
        container.remove(showPane);
        showPanel();
        showPane.add(table);
        showPane.setLayout(new BorderLayout());
        showPane.add(table.getTableHeader(), BorderLayout.PAGE_START);
        showPane.add(table, BorderLayout.CENTER);
        showPane.setBorder(BorderFactory.createLineBorder(Color.black));
        reconstruct();
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

    /*
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


     */
    public static void main(String[] args) {
        // DBUI dbui = new DBUI();
        DBUI dbui = new DBUI();
        dbui.invoke();
    }
}
