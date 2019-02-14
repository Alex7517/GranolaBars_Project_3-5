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
        
        //This is the header on top
        MaintenanceFormHeader = new JLabel("Granola Bar - Maintenance");
        MaintenanceFormHeader.setHorizontalAlignment(JLabel.CENTER);
        MaintenanceFormHeader.setVerticalAlignment(JLabel.TOP);
        MaintenanceFormHeader.setFont(new Font("Calibri", Font.BOLD, 30));
        add(MaintenanceFormHeader);
       
        AddFileButton = new JButton("Add File");
        add(AddFileButton);
   
    
}
}
