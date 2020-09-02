package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;

public class MimlTextBlock {
    private final List<String> textLines = new ArrayList<>();

    public List<String> getText() {
        return textLines;
    }

    void addLine(String line) {
        textLines.add(line);
    }
}
