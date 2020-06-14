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
    private JButton bt1;
    private JButton bt2;
    private JButton bt3;
    private JButton bt4;
    private JButton bt5;
    private JButton bt6;
    // display panel
    private JPanel showPane;
    private JTable table;

    public DBUI() {
        super("MovieAward DBMS GUI");
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
        if (e.getActionCommand() == "Button 1") {
            display();
        }
    }

    private void button() {
        bt1 = new JButton("Button 1");
        bt1.addActionListener(this);
        constraints.ipady = 0; // reset
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 2;
        buttonPane.add(bt1, constraints);
    }

    public JPanel buttonPanel() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        // create field
        constraints.fill = GridBagConstraints.HORIZONTAL;
        // set panel
        button();
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
}
