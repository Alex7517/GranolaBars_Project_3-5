package com.granolaBars;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 * This class is used to create and manage the maintenance window, which allows the user to add and remove index DATA.
 */
public class MaintenanceFrame extends JFrame implements updatableGUI{
     private JLabel MaintenanceFormHeader;
     private JLabel FileNameLabel;
     private JLabel StatusLabel;
     private JButton AddFileButton;
     private JButton RebuildButton;
     private JButton RemoveSelectedFilesButton;
     private JTable FileNameAndStatus;
     private String[] columnsNames = {"File", "Status"};
     
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
    }

    public void updateTable(Object[][] tableData) {
        FileNameAndStatus.setModel(new DefaultTableModel(tableData, columnsNames));
    }

    private void doAddFile() {
        JFileChooser FileSelect = new JFileChooser();
        FileSelect.showOpenDialog(this);
        File f = FileSelect.getSelectedFile();
        String tmpPath;
        try {
            tmpPath = f.getCanonicalPath();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        Main.activeDataManager.addData(tmpPath);
    }

    private void doRebuild() {
        System.out.println(RebuildButton.getText() + " button pressed");
    }

    private void doRemoveSelected() {
        JFileChooser FileSelect = new JFileChooser();
        FileSelect.showOpenDialog(this);
        File f = FileSelect.getSelectedFile();
        String tmpPath;
        try {
            tmpPath = f.getCanonicalPath();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        Main.activeDataManager.removeData(tmpPath);
    }
}

