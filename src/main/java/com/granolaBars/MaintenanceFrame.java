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
     private JScrollPane maintScrollPane;
     private JLabel FileNameLabel;
     private JLabel StatusLabel;
     private JButton AddFileButton;
     private JButton LoadDataButton;
     private JButton RemoveSelectedFilesButton;
     private JTable FileInfoTable;
     private String[] columnsNames = {"File Name", "Data of last modification"};
     
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
        StatusLabel = new JLabel("Data of last modification");
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
        LoadDataButton = new JButton("Reload Data");
        LoadDataButton.setMnemonic(KeyEvent.VK_O);
        LoadDataButton.setSize(150, 30);
        LoadDataButton.setLocation(250, 390);
        add(LoadDataButton);
        
        //Add remove selected files button
        RemoveSelectedFilesButton = new JButton("Remove Files");
        RemoveSelectedFilesButton.setMnemonic(KeyEvent.VK_R);
        RemoveSelectedFilesButton.setSize(175, 30);
        RemoveSelectedFilesButton.setLocation(475, 390);
        add(RemoveSelectedFilesButton);
        
        //Table to store data
        Object[][] data = {
                {"NO", "DATA"}
            };


        FileInfoTable = new JTable(data, columnsNames);
        FileInfoTable.setEnabled(false);
        maintScrollPane = new JScrollPane(FileInfoTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        maintScrollPane.setLocation(15, 70);
        maintScrollPane.setSize(650, 310);
        add(maintScrollPane);
        
        
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

        LoadDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doLoadData();
            }
        });

        RemoveSelectedFilesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doRemoveSelected();
            }
        });
    }
    

    public void updateTable(Object[][] tableData) {
        FileInfoTable.setModel(new DefaultTableModel(tableData, columnsNames));
    }

    //When the add file button is pressed, this opens
    //the file selector, gets path/data
    private void doAddFile() {
        String fileToAdd = selectFile();
        if(fileToAdd != null)
            Main.activeDataManager.addData(fileToAdd);
    }
    //When the load data button is pressed
    private void doLoadData() {
        Main.activeDataManager.loadData();
    }
    
    //When the remove button is pressed, this opens
    //the file selector so the use is able to choose
    //which file needs to be removed
    private void doRemoveSelected() {
        String fileToRemove = selectFile();
        if(fileToRemove != null)
            Main.activeDataManager.removeData(fileToRemove);
    }

    private String selectFile(){
        JFileChooser FileSelect = new JFileChooser();
        FileSelect.showOpenDialog(this);
        File f = FileSelect.getSelectedFile();
        try {
            return f.getCanonicalPath();
        }
        catch (NullPointerException e){
            return null;
        }
        catch (IOException e){
            return null;
        }
    }

    public void showMessageDialog(final String MSG_TITLE, final String MSG_INFO){
        JOptionPane.showMessageDialog(this, MSG_INFO, MSG_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    public Boolean showConfirmDialog(final String MSG_TITLE, final String MSG_INFO){
        int userAnswer = JOptionPane.showConfirmDialog(this, MSG_INFO, MSG_TITLE, JOptionPane.YES_NO_OPTION);
        if (userAnswer == 0){
            return true;
        }
        else if (userAnswer == 1){
            return false;
        }
        else{
            return false;
        }
    }
}

