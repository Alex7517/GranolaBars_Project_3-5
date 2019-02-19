package com.granolaBars;

import javax.swing.*;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
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
     private JTable FileNameAndStatus;
     
    String frameTitle = "Search Engine Maintenance";
    int frameWidth = 700, frameHeight = 500;
    
      public MaintenanceFrame(final MainFrame mainFrame){
        //Setting up its personal settings
        setTitle(frameTitle);
        setSize(frameWidth,frameHeight);
        setResizable(false);
        setLayout(null);
        setAlwaysOnTop(true);

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
        
        //Table to store data
        String[] columnsNames = {"File", "Status"};

        Object[][] data = {
            {"ReadMe.txt", "Pending"}
        };

        JTable FileNameAndStatus = new JTable(data, columnsNames);
        FileNameAndStatus.setLocation(15, 70);
        FileNameAndStatus.setSize(675, 310);
        add(FileNameAndStatus);
        FileNameAndStatus.setEnabled(false);

        //Add a WindowListener to manage closing the frame
        addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent winEvt) {
                mainFrame.MaintenanceFrameOpen = false;
            }
        });
    }
}