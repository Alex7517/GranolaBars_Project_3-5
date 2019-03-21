package com.granolaBars;

import java.util.ArrayList;

public class Main {
    static ActiveDataManager activeDataManager;
    static MainFrame mainFrame;
    static MaintenanceFrame maintenanceFrame;

    public static void main(String[] args) {
        ArrayList<updatableGUI> GUI = new ArrayList<>();
        mainFrame = new MainFrame();
        GUI.add(mainFrame);
        maintenanceFrame = new MaintenanceFrame();
        GUI.add(maintenanceFrame);
        activeDataManager = new ActiveDataManager();
        activeDataManager.addDisplayGUI(GUI);
        mainFrame.setVisible(true);
    }
}
