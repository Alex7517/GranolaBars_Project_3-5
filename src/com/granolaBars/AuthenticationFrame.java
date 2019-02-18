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
    private int frameWidth = 250, frameHeight = 200;

    private Box centerPanel = Box.createVerticalBox();
    private JLabel authenticationFrameWelcomeLabel, authenticationFrameUserIDLabel, authenticationFramePasswordLabel;
    private JTextField authenticationFrameUserIDTextField, authenticationFramePasswordTextField;
    private JPanel southPanel;
    private JButton authenticationFrameConfirmButton, authenticationFrameCancelButton;


    public AuthenticationFrame(){
        setTitle(frameTitle);
        setSize(frameWidth,frameHeight);
        setLayout(new BorderLayout());
        setResizable(true);

        //Create the Welcome Label
        authenticationFrameWelcomeLabel = new JLabel("Please Enter a UserID and Password");
        authenticationFrameWelcomeLabel.setPreferredSize(new Dimension(150,20));
        authenticationFrameWelcomeLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        add(authenticationFrameWelcomeLabel,BorderLayout.NORTH);

        //Create input labels and textFields
        authenticationFrameUserIDLabel = new JLabel("UserID");
        authenticationFrameUserIDLabel.setFont(new Font("Calibri", Font.BOLD, 11));
        authenticationFrameUserIDTextField = new JTextField();
        authenticationFrameUserIDTextField.setPreferredSize(new Dimension(150,30));

        authenticationFramePasswordLabel = new JLabel("Password");
        authenticationFramePasswordLabel.setFont(new Font("Calibri", Font.BOLD, 11));
        authenticationFramePasswordTextField = new JTextField();
        authenticationFramePasswordTextField.setPreferredSize(new Dimension(150,30));

        //Create a Center box for input labels and textFields
        centerPanel = Box.createVerticalBox();
        centerPanel.add(authenticationFrameUserIDLabel);
        centerPanel.add(authenticationFrameUserIDTextField);
        centerPanel.add(authenticationFramePasswordLabel);
        centerPanel.add(authenticationFramePasswordTextField);
        add(centerPanel, BorderLayout.WEST); //This is set to west instead of center to have a left lean for textFields

        //Create Confirm and Cancel Buttons
        authenticationFrameConfirmButton = new JButton("Confirm");

        authenticationFrameCancelButton = new JButton("Cancel");

        //Create a South Panel for Buttons
        southPanel = new JPanel();
        southPanel.add(authenticationFrameConfirmButton,BorderLayout.WEST);
        southPanel.add(authenticationFrameCancelButton,BorderLayout.EAST);
        add(southPanel,BorderLayout.SOUTH);

    }

}
