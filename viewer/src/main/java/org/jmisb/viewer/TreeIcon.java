package org.jmisb.viewer;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

class TreeIcon implements Icon {

    private static final int SIZE = 0;

    public TreeIcon() {}

    @Override
    public int getIconWidth() {
        return SIZE;
    }

    @Override
    public int getIconHeight() {
        return SIZE;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // Nothing - we don't want to draw.
    }
}
