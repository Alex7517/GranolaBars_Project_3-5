package com.granolaBars;

public class Main {
    static ActiveDataManager activeDataManager;
    static MainFrame mainFrame;
    static MaintenanceFrame maintenanceFrame;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        ActiveDataManager.test();
        activeDataManager = new ActiveDataManager("DATA");
        mainFrame.setVisible(true);
        maintenanceFrame.setVisible(false);
    }
}
