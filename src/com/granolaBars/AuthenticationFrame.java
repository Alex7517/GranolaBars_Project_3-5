package com.granolaBars;

import javax.swing.*;
import java.awt.*;

/**
 * This JFrame will be used by MainFrame to check a users authentication prior to opening the MaintenanceFrame
 *
 */
public class AuthenticationFrame extends JFrame {
    /**
     * When called this will self create the AuthenticationFrame's standard settings
     */
    private String frameTitle = "";
    private int frameWidth = 200;
    private int frameHeight = 300;

    private JLabel authenticationFrameWelcomeLabel;
    private JTextField authenticationFrameUserIDTextField;
    private JLabel authenticationFrameUserIDLabel;
    private JTextField authenticationFramePasswordTextField;
    private JLabel authenticationFramePasswordLabel;
    private JButton authenticationFrameConfirmButton;
    private JButton authenticationFrameReturnButton;

    private LayoutManager authenticationFrameLayoutManager = new BorderLayout();


    public AuthenticationFrame(){
        setTitle(frameTitle);
        setSize(frameWidth,frameHeight);
        setLayout(authenticationFrameLayoutManager);
        setResizable(false);

        //Create the Welcome Label
        authenticationFrameWelcomeLabel = new JLabel("Please Enter a UserID and Password");
        authenticationFrameWelcomeLabel.setBounds(10,0,100,50);
        authenticationFrameWelcomeLabel.setFont(new Font("Calibri", Font.BOLD, 11));
        add(authenticationFrameWelcomeLabel,BorderLayout.NORTH);

        //Create the UserID text Field and label
        authenticationFrameUserIDLabel = new JLabel("UserID");
        authenticationFrameUserIDLabel.setBounds(10,40,100,50);
        authenticationFrameUserIDLabel.setFont(new Font("Calibri", Font.BOLD, 9));
        add(authenticationFrameUserIDLabel, BorderLayout.CENTER);

        authenticationFrameUserIDTextField = new JTextField();
        authenticationFrameUserIDTextField.setBounds(20,80,100,10);
        authenticationFrameUserIDTextField.setFont(new Font("Calibri", Font.BOLD, 9));
        add(authenticationFrameUserIDTextField, BorderLayout.CENTER);

        //Create the Password text Field and label

        //Create the Confirm Button

        //Create the return/go back button

    }

}
