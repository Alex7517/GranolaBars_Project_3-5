package com.granolaBars;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    //MainFrames default settings
    String frameTitle = "The Granola Bar Search Engine";
    int frameWidth = 600, frameHeight = 800;

    public MainFrame(){
        //Setting up its personal settings
        setTitle(frameTitle);
        setLayout(null);
        setSize(frameWidth,frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //Creating a textField for the search bar
        TextField  searchBarTextField = new TextField ();
        searchBarTextField.setBounds(1,2,100,20);
        add(searchBarTextField);
    }
}
