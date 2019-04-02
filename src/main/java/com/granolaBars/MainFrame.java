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
public class MainFrame extends JFrame implements updatableGUI{
    //MainFrames default settings
    String frameTitle = "The Granola Bar Search Engine";
    int frameWidth = 600;
    int frameHeight = 840;
    Dimension dime = new Dimension (frameWidth, frameHeight);

    private JPanel panel, panel2;
    private JLabel label;
    private JButton searchButton, maintenanceButton, aboutButton;
    private JRadioButton radioButtonMOr, radioButtonMAnd, radioButtonMPHRASE ;
    private JTextField  searchBarTextField;
    private JTable searchResult;
    private JScrollPane searchScrollPane;
    private ButtonGroup buttonGroup;
    private String[] columnsNames = {"File", "Status"};

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
                doSearch();
            }
        });

        // about button
        aboutButton = new JButton("about");
        aboutButton.setPreferredSize(buttonDimension);
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAbout();
            }
        });

        radioButtonMOr = new JRadioButton("OR");
        radioButtonMOr.setFont(new Font("Calibri", Font.BOLD, 14));
        radioButtonMOr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doMatchAll();
            }
        });

        radioButtonMAnd = new JRadioButton("AND");
        radioButtonMAnd.setFont(new Font("Calibri", Font.BOLD, 14));
        radioButtonMAnd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doMatchAny();
            }
        });

        radioButtonMPHRASE = new JRadioButton("PHRASE");
        radioButtonMPHRASE.setFont(new Font("Calibri", Font.BOLD, 14));
        radioButtonMPHRASE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doMatchExactly();
            }
        });

        //Add Radio Button Group
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonMOr);
        buttonGroup.add(radioButtonMAnd);
        buttonGroup.add(radioButtonMPHRASE);


        // rows, columns, table and scrollable pane to view search result
        //This DATA is just a stub at this time
        Object[][] data = {
                {"ReadMe.txt", "Pending"}
        };

        searchResult = new JTable(data,columnsNames);
        searchScrollPane = new JScrollPane(searchResult, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        searchScrollPane.setPreferredSize(new Dimension(550,450));
        //searchResult.pack(searchScrollPane);

        // button, button size and button clicked event
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

        // about button in the layout
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(aboutButton, gbc);

        // search button in the layout
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchButton, gbc);

        // radio buttons in the layout
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(radioButtonMOr, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(radioButtonMAnd, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(radioButtonMPHRASE, gbc);

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
        //Table will not update like this in final product. This
        // will have to be changed or removed.
    public void updateTable(Object[][] tableData) {
        searchResult.setModel(new DefaultTableModel(tableData, columnsNames));
    }

    /**
     * This method will manage calling the appropriate search method and update the table
     */
    //STUB
    private void doSearch() {
        System.out.println(searchButton.getText() + " button pressed");
        //Organize the searchButtons text into a list
        List searchedWordsList = ActiveDataManager.prepTextInput(searchBarTextField.getText());

        System.out.println(searchedWordsList);

        //Call the correct method for the search selected
        if(radioButtonMOr.isSelected()){
            System.out.println("OR");
            updateTable(Main.activeDataManager.searchDataOr(new HashSet<String>(searchedWordsList)));
        }
        else if(radioButtonMAnd.isSelected()){
            System.out.println("And");
            updateTable(Main.activeDataManager.searchDataAnd(new HashSet<String>(searchedWordsList)));
        }
        else if(radioButtonMPHRASE.isSelected()){
            System.out.println("Phrase");
            updateTable(Main.activeDataManager.searchDataPhrase(searchedWordsList));
        }
        else{
            System.out.println("Nothing");
        }
    }

    private void doMatchAll() {
        System.out.println(radioButtonMOr.getText() + " button pressed");
    }

    private void doMatchAny() {
        System.out.println(radioButtonMAnd.getText() + " button pressed");
    }

    private void doMatchExactly() {
        System.out.println(radioButtonMPHRASE.getText() + " button pressed");
    }

    private void doAbout() {
        JOptionPane.showMessageDialog(this, "GranolaBars Search Engine", "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
