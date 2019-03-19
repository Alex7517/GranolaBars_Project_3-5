package com.granolaBars;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class MaintenanceFrame extends JFrame{
     private JLabel MaintenanceFormHeader;
     private JLabel FileNameLabel;
     private JLabel StatusLabel;
     private JButton AddFileButton;
     private JButton RebuildButton;
     private JButton RemoveSelectedFilesButton;
     private JButton ResetWindowsButton;
     private JTable FileNameAndStatus;
     private String[] columnsNames = {"File", "Status"};
     private DefaultTableModel tableModel;
     
    String frameTitle = "Search Engine Maintenance";
    int frameWidth = 700, frameHeight = 500;
    
    public MaintenanceFrame(){
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
        AddFileButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser FileSelect = new JFileChooser();
                FileSelect.showOpenDialog(null);
                File f = FileSelect.getSelectedFile();
                //FileIndexDBManager.createFile(f.getName(), f.getAbsolutePath(), new Date(f.lastModified()));
            }
        });
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


        FileNameAndStatus = new JTable(data, columnsNames);
        FileNameAndStatus.setLocation(15, 70);
        FileNameAndStatus.setSize(675, 310);
        add(FileNameAndStatus);
        FileNameAndStatus.setEnabled(false);

        //Add a WindowListener to manage closing the frame
        addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent winEvt) {
                Main.mainFrame.maintenanceFrameOpen = false;
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

    void updateTable(Object[][] data) {
        FileNameAndStatus.setModel(new DefaultTableModel(data, columnsNames));
    }

    private void doAddFile() {
        System.out.println(AddFileButton.getText() + " button pressed");
    }

    private void doRebuild() {
        System.out.println(RebuildButton.getText() + " button pressed");
    }

    private void doRemoveSelected() {
        System.out.println(RemoveSelectedFilesButton.getText() + " button pressed");
    }

    private void doReset() {
        System.out.println(ResetWindowsButton.getText() + " button pressed");
    }
}

