package com.granolaBars;

import javax.swing.*;

public class MainFrame extends JFrame {
    //MainFrames default settings
    String frameTitle = "The Granola Bar Search Engine";
    int frameWidth = 600, frameHeight = 800;

    public MainFrame(){
        //Setting up its personal settings
        setTitle(frameTitle);
        setSize(frameWidth,frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //Creating a textField for the search bar
        MainFrameSearchBarTextField searchBarTextField = new MainFrameSearchBarTextField();
        add(searchBarTextField);
    }
}
