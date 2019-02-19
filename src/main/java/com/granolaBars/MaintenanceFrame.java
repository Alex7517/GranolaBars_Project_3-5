package com.granolaBars;

import javax.swing.*;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.TextArea;

public class MaintenanceFrame extends JFrame{
     private JLabel MaintenanceFormHeader;
     private JLabel FileNameLabel;
     private JLabel StatusLabel;
     private JButton AddFileButton;
     private JButton RebuildButton;
     private JButton RemoveSelectedFilesButton;
     private JButton ResetWindowsButton;
     private TextArea FileTextArea;
     
    String frameTitle = "Search Engine Maintenance";
    int frameWidth = 700, frameHeight = 500;
    
      public MaintenanceFrame(final MainFrame mainFrame){
        //Setting up its personal settings
        setTitle(frameTitle);
        setSize(frameWidth,frameHeight);
        setResizable(false);
        setLayout(null);
        setAlwaysOnTop(true);
        
        //Text area where file information will show
         FileTextArea = new TextArea("", 24, 80);
         FileTextArea.setFont(new Font("Calibri", Font.PLAIN, 12));
         FileTextArea.setLocation(15, 70);
         FileTextArea.setSize(675, 310);
         FileTextArea.setEditable(false);
         add(FileTextArea);

        //This is the header on top
        MaintenanceFormHeader = new JLabel("Granola Bar - Maintenance");
        MaintenanceFormHeader.setLocation(175, 0);
        MaintenanceFormHeader.setSize(500, 50);
        MaintenanceFormHeader.setFont(new Font("Calibri", Font.BOLD, 30));
        add(MaintenanceFormHeader);
        
        // File name label
        FileNameLabel = new JLabel("File Name");
        FileNameLabel.setLocation(175, 50);
        FileNameLabel.setSize(250, 20);
        FileNameLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        add(FileNameLabel);
        
        //Status label
        StatusLabel = new JLabel("Status");
        StatusLabel.setLocation(475, 50);
        StatusLabel.setSize(250, 20);
        StatusLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        add(StatusLabel);
       
        //Add file button
        AddFileButton = new JButton("Add File");  
        AddFileButton.setMnemonic(KeyEvent.VK_A);
        AddFileButton.setSize(100, 30);
        AddFileButton.setLocation(75, 390);
        add(AddFileButton);
   
        //Add rebuild button
        RebuildButton = new JButton("Rebuild Out-Of-Date");  
        RebuildButton.setMnemonic(KeyEvent.VK_O);
        RebuildButton.setSize(150, 30);
        RebuildButton.setLocation(250, 390);
        add(RebuildButton);
        
        //Add remove selected files button
        RemoveSelectedFilesButton = new JButton("Remove Selected Files");
        RemoveSelectedFilesButton.setMnemonic(KeyEvent.VK_R);
        RemoveSelectedFilesButton.setSize(175, 30);
        RemoveSelectedFilesButton.setLocation(475, 390);
        add(RemoveSelectedFilesButton);
    
        //Add reset windows button
        ResetWindowsButton = new JButton("Reset Windows");   
        ResetWindowsButton.setMnemonic(KeyEvent.VK_W);
        ResetWindowsButton.setSize(125, 30);
        ResetWindowsButton.setLocation(10, 425);
        add(ResetWindowsButton);

        //Add a WindowListener to manage closing the frame
        addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent winEvt) {
                mainFrame.MaintenanceFrameOpen = false;
            }
        });

        // Adding action listeners for buttons

        AddFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doAddFile();
            }
        });

        RebuildButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doRebuild();
            }
        });

        RemoveSelectedFilesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doRemoveSelected();
            }
        });

        ResetWindowsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doReset();
            }
        });
    }

    public void doAddFile() {
        System.out.println(AddFileButton.getText() + " button pressed");
    }

    public void doRebuild() {
        System.out.println(RebuildButton.getText() + " button pressed");
    }

    public void doRemoveSelected() {
        System.out.println(RemoveSelectedFilesButton.getText() + " button pressed");
    }

    public void doReset() {
        System.out.println(ResetWindowsButton.getText() + " button pressed");
    }
}
