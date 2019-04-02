package com.granolaBars;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * This class is used to create and manage the main window, which allows the user to search the index and read see the results
 */
public class MainFrame extends JFrame{
    //MainFrames default settings
    String frameTitle = "The Granola Bar Search Engine";
    int frameWidth = 600;
    int frameHeight = 840;
    Dimension dime = new Dimension (frameWidth, frameHeight);

    private JPanel northPanel, southPanel;
    private JLabel label;
    private JButton searchButton, maintenanceButton, aboutButton;
    private JRadioButton radioButtonMOr, radioButtonMAnd, radioButtonMPHRASE ;
    private JTextField  searchBarTextField;
    private JTable searchResult;
    private JScrollPane searchScrollPane;
    private ButtonGroup buttonGroup;
    private String[] columnsNames = {"File Name", "Date of last modification"};

    public boolean maintenanceFrameOpen = false;

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

        //Panel dimensions for NORTH panel (Everything for the search)
        northPanel = new JPanel();
        Dimension panelDimension = new Dimension(frameWidth, 200);
        northPanel.setPreferredSize(panelDimension);

        //Panel dimensions for SOUTH panel which contains the search results
        southPanel = new JPanel();
        Dimension panelDimension2 = new Dimension(frameWidth, 600);
        southPanel.setPreferredSize(panelDimension2);

        //Label for name creation and size settings
        label = new JLabel("Search Engine");
        label.setFont(new Font("Calibri", Font.BOLD, 23));
        Dimension labelDimension = new Dimension(150,30);
        label.setPreferredSize(labelDimension);
        label.setHorizontalTextPosition(SwingConstants.LEFT);


        //Search button creation and size settings
        searchButton = new JButton("search");
        Dimension buttonDimension = new Dimension(120,30);
        searchButton.setPreferredSize(buttonDimension);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doSearch();
            }
        });

        //About button
        aboutButton = new JButton("About");
        aboutButton.setPreferredSize(buttonDimension);
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAbout();
            }
        });

        //Or radio button
        radioButtonMOr = new JRadioButton("OR");
        radioButtonMOr.setSelected(true);
        radioButtonMOr.setFont(new Font("Calibri", Font.BOLD, 14));

        // And radio button
        radioButtonMAnd = new JRadioButton("AND");
        radioButtonMAnd.setFont(new Font("Calibri", Font.BOLD, 14));
      
        //Phrase radio button
        radioButtonMPHRASE = new JRadioButton("PHRASE");
        radioButtonMPHRASE.setFont(new Font("Calibri", Font.BOLD, 14));

        //Add Radio Button Group
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonMOr);
        buttonGroup.add(radioButtonMAnd);
        buttonGroup.add(radioButtonMPHRASE);


        //Rows, columns, table and scrollable pane to view search result
        Object[][] data = {
                {"Nothing Searched", "Remember to select a search type"}
        };

        searchResult = new JTable(data,columnsNames);      
        searchScrollPane = new JScrollPane(searchResult, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        searchScrollPane.setPreferredSize(new Dimension(550,450));
        searchResult.setEnabled(false);

        //Maintenance button, button size, and action listener
        maintenanceButton = new JButton("Maintenance");
        maintenanceButton.setPreferredSize(buttonDimension);
        maintenanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //This is to prevent multiple AuthenticationFrames/MaintenanceFrames open at the same time
                if (!maintenanceFrameOpen) {
                    Main.maintenanceFrame.setVisible(true);
                    maintenanceFrameOpen = true;
                }
            }
        });

        //This panel contains the customizable search options,
        // search field, search button and header in the
        // GridBagLayout
        add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        northPanel.add(label, gbc);

        //Search bar location in the layout
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        northPanel.add(searchBarTextField, gbc);

        //About button in the layout
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        northPanel.add(aboutButton, gbc);

        //Search button in the layout
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        northPanel.add(searchButton, gbc);

        //Radio buttons in the layout
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        northPanel.add(radioButtonMOr, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        northPanel.add(radioButtonMAnd, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        northPanel.add(radioButtonMPHRASE, gbc);

        // This panel contains the search results, scroll pane and maintenance button
        add(southPanel,BorderLayout.SOUTH);
        southPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(15,15,15,15);
        southPanel.add(maintenanceButton, gc);
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.NORTH;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 3;
        gc.insets = new Insets(0,20,0,20);
        southPanel.add(searchScrollPane, gc);

    }
    public void updateTable(Object[][] tableData) {
        searchResult.setModel(new DefaultTableModel(tableData, columnsNames));
    }

    /**
     * This method will manage calling the appropriate search method and update the table
     */
    private void doSearch() {

        //Call the correct method for the search selected
        if(radioButtonMOr.isSelected()){
            updateTable(Main.activeDataManager.searchDataOr(searchBarTextField.getText()));
        }
        else if(radioButtonMAnd.isSelected()){
            updateTable(Main.activeDataManager.searchDataAnd(searchBarTextField.getText()));
        }
        else if(radioButtonMPHRASE.isSelected()){
            updateTable(Main.activeDataManager.searchDataPhrase(searchBarTextField.getText()));
        }
        else{
            System.out.println("Nothing Selected");
        }
    }

    //Shows message box when about button is clicked
    private void doAbout() {
        JOptionPane.showMessageDialog(this, "GranolaBars Search Engine", "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
