package org.jmisb.viewer;

import org.jmisb.api.video.IVideoInput;
import org.jmisb.api.video.PesInfo;

import javax.swing.*;
import java.awt.Dimension;

/**
 * Dialog to display basic stream information
 */
class StreamInfoDialog extends JDialog
{
    StreamInfoDialog(JFrame owner, IVideoInput videoInput)
    {
        super(owner, "Stream Info");
        initComponents(videoInput);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents(IVideoInput videoInput)
    {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 280));

        JPanel panel = new JPanel();
        panel.add(scrollPane);

        getContentPane().add(panel);
        pack();

        if (videoInput != null)
            textArea.setText(PesInfo.asJson(videoInput.getPesInfo()));
    }
}

