package com.granolaBars;

public class Main {
    static ActiveDataManager activeDataManager;
    static MainFrame mainFrame;
    static MaintenanceFrame maintenanceFrame;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        activeDataManager = new ActiveDataManager();
        mainFrame.setVisible(true);
    }
}
