package com.granolaBars;

/**
 * This interface is used by the ActiveDataManager to automatically update Jtables whenever the Data changes
 */
interface updatableGUI {
        void updateTable(Object[][] tableData);
        void showMessageDialog(final String MSG_TITLE, final String MSG_INFO);
        Boolean showConfirmDialog(final String MSG_TITLE, final String MSG_INFO);
        }
