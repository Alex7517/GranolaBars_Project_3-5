package com.granolaBars;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    //MainFrames default settings
    String frameTitle = "The Granola Bar Search Engine";
    int frameWidth = 600, frameHeight = 800;

    private JPanel panel;
    private JPanel panel2;
    private JLabel label;
    private JButton searchButton;
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JTextField  searchBarTextField;
    private JTable searchResult;
    private JScrollPane searchScrollPane;

    public MainFrame(){
        //Setting up its personal settings
        setTitle(frameTitle);
        setLayout(new BorderLayout());
        setSize(frameWidth,frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //Creating a textField for the search bar
        searchBarTextField = new JTextField (16);

        // panel dimensions for NORTH panel (Everything for the search)
        panel = new JPanel();
        Dimension panelD = new Dimension(frameWidth, 200);
        panel.setPreferredSize(panelD);

        // panel dimensions for SOUTH panel (The results)
        panel2 = new JPanel();
        Dimension panelD2 = new Dimension(frameWidth, 600);
        panel2.setPreferredSize(panelD2);

        // label for name in Header
        label = new JLabel("Search Engine");
        label.setFont(new Font("Serif", Font.BOLD, 23));
        Dimension labelD = new Dimension(150,30);
        label.setPreferredSize(labelD);
        label.setHorizontalTextPosition(SwingConstants.LEFT);

        searchButton = new JButton("search");
        Dimension buttonD = new Dimension(120,30);
        searchButton.setPreferredSize(buttonD);

        radioButton = new JRadioButton("match all");
        radioButton1 = new JRadioButton("match any");
        radioButton2 = new JRadioButton("match exactly");

        // will need to be added later
        // buttonGroup = new ButtonGroup();

        searchScrollPane = new JScrollPane();
        searchResult = new JTable();

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
        gbc.ipadx = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);

        // search bar location in the layout
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchBarTextField, gbc);

        // search button in the layout
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(searchButton, gbc);

        // radial buttons in the layout
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

        // This panel contains the search results
        add(panel2,BorderLayout.SOUTH);
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        panel2.add(searchScrollPane, gc);
        searchScrollPane.add(searchResult, gc);

        // Need to make an about tab & page
    }
}
