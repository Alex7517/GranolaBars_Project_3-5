package com.granolaBars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    //MainFrames default settings
    String frameTitle = "The Granola Bar Search Engine";
    int frameWidth = 600;
    int frameHeight = 840;
    Dimension dime = new Dimension (frameWidth, frameHeight);

    private JPanel panel, panel2;
    private JLabel label, label2;
    private JButton searchButton, maintenanceButton;
    private JRadioButton radioButton, radioButton1, radioButton2;
    private JTextField  searchBarTextField;
    private JTable searchResult;
    private JScrollPane searchScrollPane;
    private int rows, columns;
    //Others dont know the mainFrame object, so this is passed to other JOptions (This will likely be swapped with a global elsewhere)
    MainFrame myself = this;
    public boolean MaintenanceFrameOpen = false;

    public MainFrame(){
        //Setting up its personal settings
        setTitle(frameTitle);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setPreferredSize(dime);
        this.pack();

        //Creating a textField for the search bar
        searchBarTextField = new JTextField (16);

        // panel dimensions for NORTH panel (Everything for the search)
        panel = new JPanel();
        Dimension panelDimension = new Dimension(frameWidth, 200);
        panel.setPreferredSize(panelDimension);

        // panel dimensions for SOUTH panel which contains the search results
        panel2 = new JPanel();
        Dimension panelDimension2 = new Dimension(frameWidth, 600);
        panel2.setPreferredSize(panelDimension2);

        // label for name creation and size set
        label = new JLabel("Search Engine");
        label.setFont(new Font("Calibri", Font.BOLD, 23));
        Dimension labelDimension = new Dimension(150,30);
        label.setPreferredSize(labelDimension);
        label.setHorizontalTextPosition(SwingConstants.LEFT);

        // search button creation and size
        searchButton = new JButton("search");
        Dimension buttonDimension = new Dimension(120,30);
        searchButton.setPreferredSize(buttonDimension);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(searchButton.getText() + " pressed");
            }
        });

        radioButton = new JRadioButton("match all");
        radioButton.setFont(new Font("Calibri", Font.BOLD, 14));
        radioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(radioButton.getText() + " button pressed");
            }
        });

        radioButton1 = new JRadioButton("match any");
        radioButton1.setFont(new Font("Calibri", Font.BOLD, 14));
        radioButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(radioButton1.getText() + " button pressed");
            }
        });

        radioButton2 = new JRadioButton("match exactly");
        radioButton2.setFont(new Font("Calibri", Font.BOLD, 14));
        radioButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(radioButton2.getText() + " button pressed");
            }
        });

        // buttonGroup = new ButtonGroup();
        // will need to be added later

        // rows, columns, table and scrollable pane to view search result
        rows = 36;
        columns = 8;
        searchResult = new JTable(rows,columns);
        searchScrollPane = new JScrollPane(searchResult, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        searchScrollPane.setPreferredSize(new Dimension(550,450));
        //searchResult.pack(searchScrollPane);

        // button, button size and button clicked event
        maintenanceButton = new JButton("Maintenance");
        maintenanceButton.setPreferredSize(buttonDimension);
        maintenanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //This is to prevent multiple AuthenticationFrames/MaintenanceFrames open at the same time
                if (!MaintenanceFrameOpen) {
                    AuthenticationFrame authenticationFrame = new AuthenticationFrame(myself);
                    authenticationFrame.setVisible(true);
                    MaintenanceFrameOpen = true;
                }
            }
        });

        // this panel contains the customizable search options,
        // search field, search button and header in the
        // GridBagLayout
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);

        // search bar location in the layout
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchBarTextField, gbc);

        // search button in the layout
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchButton, gbc);

        // radio buttons in the layout
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(radioButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(radioButton1, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(radioButton2, gbc);

        // This panel contains the search results, scroll pane and maintenance button
        add(panel2,BorderLayout.SOUTH);
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(15,15,15,15);
        panel2.add(maintenanceButton, gc);
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.NORTH;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 3;
        gc.insets = new Insets(0,20,0,20);
        panel2.add(searchScrollPane, gc);

        // Need to make an about tab & page
    }
}
