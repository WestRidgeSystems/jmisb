package org.jmisb.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.*;

/** Stores most-recently-used files */
class MruFileList {
    private static int MAX_ENTRIES = 4;

    private MruFileList() {}

    static List<JMenuItem> getList() {
        Preferences prefs = Preferences.userNodeForPackage(MruFileList.class);
        List<String> filenames = getFilenames(prefs);

        List<JMenuItem> list = new ArrayList<>();
        for (String filename : filenames) {
            JMenuItem menuItem = new JMenuItem(filename);
            menuItem.setName("File|Mru|" + filename);
            list.add(menuItem);
        }
        return list;
    }

    static void add(String filename) {
        Preferences prefs = Preferences.userNodeForPackage(MruFileList.class);
        List<String> currentList = getFilenames(prefs);

        prefs.put("mruFile0", filename);

        int i = 1;
        for (String mruFile : currentList) {
            if (!mruFile.equals(filename)) {
                prefs.put("mruFile" + i, mruFile);
                i++;
            }
            if (i > MAX_ENTRIES) {
                break;
            }
        }
    }

    private static List<String> getFilenames(Preferences prefs) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < MAX_ENTRIES; i++) {
            String mruFile = prefs.get("mruFile" + i, "");
            if (!mruFile.isEmpty()) {
                list.add(mruFile);
            }
        }
        return list;
    }
}
