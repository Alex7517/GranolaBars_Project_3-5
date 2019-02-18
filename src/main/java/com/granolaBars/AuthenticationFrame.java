package com.granolaBars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This JFrame will be used by MainFrame to check a users authentication prior to opening the MaintenanceFrame
 *
 */
public class AuthenticationFrame extends JFrame {
    /**
     * When called this will self create the AuthenticationFrame's standard settings
     */
    final private String frameTitle = "Login";
    final private int frameWidth = 250, frameHeight = 170;
    final private String FONT = "Calibri";

    private Box centerPanel = Box.createVerticalBox();
    private JLabel authenticationFrameWelcomeLabel, authenticationFrameUserIDLabel, authenticationFramePasswordLabel;
    private JTextField authenticationFrameUserIDTextField, authenticationFramePasswordTextField;
    private JPanel southPanel;
    private JButton authenticationFrameConfirmButton, authenticationFrameCancelButton;
    AuthenticationFrame myself = this;


    public AuthenticationFrame(final MainFrame mainFrame){
        setTitle(frameTitle);
        setSize(frameWidth,frameHeight);
        setLayout(new BorderLayout());
        setResizable(false);
        setAlwaysOnTop(true);

        //Create the Welcome Label
        authenticationFrameWelcomeLabel = new JLabel("Please Enter a UserID and Password");
        authenticationFrameWelcomeLabel.setPreferredSize(new Dimension(150,20));
        authenticationFrameWelcomeLabel.setFont(new Font(FONT, Font.BOLD, 14));
        add(authenticationFrameWelcomeLabel,BorderLayout.NORTH);

        //Create input labels and textFields
        authenticationFrameUserIDLabel = new JLabel("UserID");
        authenticationFrameUserIDLabel.setFont(new Font(FONT, Font.BOLD, 11));
        authenticationFrameUserIDTextField = new JTextField();
        authenticationFrameUserIDTextField.setPreferredSize(new Dimension(150,30));

        authenticationFramePasswordLabel = new JLabel("Password");
        authenticationFramePasswordLabel.setFont(new Font(FONT, Font.BOLD, 11));
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
        authenticationFrameConfirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //The Logic for testing the UserID and PW will be here
                MaintenanceFrame maintenanceFrame = new MaintenanceFrame(mainFrame);
                maintenanceFrame.setVisible(true);
                setVisible(false);
                dispose();
            }
        });

        authenticationFrameCancelButton = new JButton("Cancel");
        authenticationFrameCancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new java.awt.event.WindowEvent(myself,java.awt.event.WindowEvent.WINDOW_CLOSING));
                setVisible(false);
                dispose();
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent winEvt) {
                mainFrame.MaintenanceFrameOpen = false;
            }
        });

        //Create a South Panel for Buttons
        southPanel = new JPanel();
        southPanel.add(authenticationFrameConfirmButton,BorderLayout.WEST);
        southPanel.add(authenticationFrameCancelButton,BorderLayout.EAST);
        add(southPanel,BorderLayout.SOUTH);

    }

}
