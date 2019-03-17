package com.granolaBars;

public class Main {
    static ActiveDataManager activeDataManager;
    static MainFrame mainFrame;

    public static void main(String[] args) {
        ActiveDataManager.test();
        activeDataManager = new ActiveDataManager("DATA");
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
