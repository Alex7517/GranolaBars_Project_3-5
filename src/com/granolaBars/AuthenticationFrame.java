package com.granolaBars;

import javax.swing.*;
/**
 * This JFrame will be used by MainFrame to check a users authentication prior to opening the MaintenanceFrame
 *
 */
public class AuthenticationFrame extends JFrame {
    /**
     * When call this will self create the AuthenticationFrame's standard settings
     */
    private String frameTitle = "";
    private int frameWidth = 200;
    private int frameHeight = 300;


    public void AuthenticationFrame(){
        setTitle(frameTitle);
        setSize(frameWidth,frameHeight);
        setLayout(null);
        setResizable(false);
    }
}
