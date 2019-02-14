package com.granolaBars;

import javax.swing.*;
import java.awt.*;

public class MaintenanceFrame extends JFrame{
     private JLabel MaintenanceFormHeader;
     private JButton AddFileButton;
     private JButton RebuildButton;
     private JButton RemoveSelectedFilesButton;
     private JButton ResetWindowsButton;
     
    String frameTitle = "Search Engine Maintenance";
    int frameWidth = 700, frameHeight = 500;
    
      public MaintenanceFrame(){
        //Setting up its personal settings
        setTitle(frameTitle);
        setSize(frameWidth,frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        //This is the header on top
        MaintenanceFormHeader = new JLabel("Granola Bar - Maintenance");
        MaintenanceFormHeader.setLocation(175, 0);
        MaintenanceFormHeader.setSize(500, 50);
        MaintenanceFormHeader.setFont(new Font("Calibri", Font.BOLD, 30));
        add(MaintenanceFormHeader);
       
        //Add file button
        AddFileButton = new JButton("Add File");   
        AddFileButton.setSize(100, 30);
        AddFileButton.setLocation(75, 375);
        add(AddFileButton);
   
        //Add rebuild button
        RebuildButton = new JButton("Rebuild Out-Of-Date");   
        RebuildButton.setSize(150, 30);
        RebuildButton.setLocation(250, 375);
        add(RebuildButton);
        
        //Add remove selected files button
        RemoveSelectedFilesButton = new JButton("Remove Selected Files");   
        RemoveSelectedFilesButton.setSize(175, 30);
        RemoveSelectedFilesButton.setLocation(475, 375);
        add(RemoveSelectedFilesButton);
    
        //Add reset windows button
        ResetWindowsButton = new JButton("Reset Windows");   
        ResetWindowsButton.setSize(125, 30);
        ResetWindowsButton.setLocation(5, 425);
        add(ResetWindowsButton);
}
}
