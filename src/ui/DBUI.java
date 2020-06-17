package ui;

import delegates.AddAwardDelegate;
import model.Award;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DBUI extends JFrame implements ActionListener {

    private Container container;
    private GridBagConstraints constraints;
    private JPanel buttonPane;
    private JPanel showPane;
    private JTable table;

    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;
    private BufferedReader bufferedReader = null;
    private AddAwardDelegate delegate = null;


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

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public JPanel buttonPanel() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        // set panel
        String[] chos = {"insert", "delete", "update", "selection", "projection",
                "join", "aggregation", "nestedAgg", "division", "show", "quit"};
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
                handleShowOption();
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
                String name = null;
                name = entername.getText();
                String startdate = null;
                startdate = enterstartdate.getText();
                String enddate = null;
                enddate = enterenddate.getText();
                if (aID != INVALID_INPUT) {
                    System.out.println(aID + startdate + enddate + name);
                    Award model = new Award(aID, startdate, enddate, name);
                    if (delegate == null) System.out.println("here");
                    delegate.insertAward(model);
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
        labels.add(new JLabel("ID condition: ", SwingConstants.RIGHT));
        // labels.add(new JLabel("Award name condition: ", SwingConstants.RIGHT));
        // labels.add(new JLabel("Start date condition: ", SwingConstants.RIGHT));
        // labels.add(new JLabel("End date condition: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField f1 = new JTextField("");
        // JTextField f2 = new JTextField("");
        // JTextField f3 = new JTextField("");
        // JTextField f4 = new JTextField("");

        p.add(controls, BorderLayout.CENTER);
        controls.add(f1);
        // controls.add(f2);
        // controls.add(f3);
        // controls.add(f4);

        int input = JOptionPane.showOptionDialog(null, p, "Award Selection",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        JTextField[] cur = {f1}; // , f2, f3, f4};
        // List<String> res = new ArrayList<>();
        String res = "";
        if (input == JOptionPane.OK_OPTION) {
//            for (JTextField j : cur) {
//                String cond = j.getText();
//                if (!cond.equals("")) res.add(cond);
//            }
            res = f1.getText();
        }
        delegate.selectAward(res);
    }

    private void handleProjectionOption() {
        JPanel p = new JPanel(new BorderLayout(5, 5));

        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
        labels.add(new JLabel("Selected Fields: ", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField f1 = new JTextField("");
        JTextField f2 = new JTextField("");
        // JTextField f3 = new JTextField("");
        // JTextField f4 = new JTextField("");

        p.add(controls, BorderLayout.CENTER);
        controls.add(f1);
        controls.add(f2);
        // controls.add(f3);
        // controls.add(f4);

        int input = JOptionPane.showOptionDialog(null, p, "Award Projection",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        JTextField[] cur = {f1, f2}; // , f3, f4};
        List<String> res = new ArrayList<>();
        if (input == JOptionPane.OK_OPTION) {
            for (JTextField j : cur) {
                String name = j.getText();
                if (!name.equals("")) res.add(name);
            }
        }
        delegate.projectAward(res);
    }

    private void handleJoinOption() {
//        JPanel p = new JPanel(new BorderLayout(5, 5));
//
//        JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
//        labels.add(new JLabel("Selected Fields: ", SwingConstants.RIGHT));
//        p.add(labels, BorderLayout.WEST);
//
//        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
//        JTextField f1 = new JTextField("");
//        JTextField f2 = new JTextField("");
//        JTextField f3 = new JTextField("");
//        JTextField f4 = new JTextField("");
//
//        p.add(controls, BorderLayout.CENTER);
//        controls.add(f1);
//        controls.add(f2);
//        controls.add(f3);
//        controls.add(f4);
//
//        int input = JOptionPane.showOptionDialog(null, p, "Award Selection",
//                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
//
//        JTextField[] cur = {f1, f2, f3, f4};
//        List<String> res = new ArrayList<>();
//        if (input == JOptionPane.OK_OPTION) {
//            for (JTextField j : cur) {
//                String cond = j.getText();
//                if (!cond.equals("")) res.add(cond);
//            }
//        }
//        delegate.joinAward(res);
    }

    private void handleAggregateOption() {
    }

    private void handleNestedAggOption() {
    }

    private void handleDivisionOption() {
    }

    private void handleShowOption() {
        String[] colnm = {"ID", "StartDate", "EndDate", "AwardName"};
        String[][] data = delegate.showAward();

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

        container.remove(showPane);
        showPanel();
        showPane.add(table);
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

    public static void main(String[] args) {
        // DBUI dbui = new DBUI();
        DBUI dbui = new DBUI();
        dbui.invoke();
    }
}
