package org.jmisb.viewer;

import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.MetadataFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static java.awt.Font.PLAIN;
import org.jmisb.api.klv.eg0104.IPredatorMetadataValue;
import org.jmisb.api.klv.eg0104.PredatorMetadataKey;
import org.jmisb.api.klv.eg0104.PredatorUavMessage;

/**
 * Simple text pane to display MISB metadata
 */
public class MetadataPanel extends JTextPane implements IMetadataListener
{
    private static Logger logger = LoggerFactory.getLogger(MetadataPanel.class);
    private long previous = 0;

    /**
     * Constructor
     */
    MetadataPanel()
    {
        setEditable(false);
        setContentType("text/html");
        setFont(new Font("Dialog", PLAIN, 12));
        clear();
    }

    @Override
    public void updateUI()
    {
        super.updateUI();
        // Make HTML renderer use the component's font setting
        putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
    }

    @Override
    public void onMetadataReceived(MetadataFrame metadataFrame)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("pts = " + metadataFrame.getPts());
        }

        // Refresh at most once per second
        long current = System.nanoTime();
        if ((current - previous) > 1_000_000_000)
        {
            SwingUtilities.invokeLater(() ->
            {
                StringBuilder sb = new StringBuilder();
                sb.append("<html>");
                sb.append("<head>");
                sb.append("</head>");
                sb.append("<body>");

                sb.append("<h1>");
                sb.append(metadataFrame.getMisbMessage().displayHeader());
                sb.append("</h1>");
                // TODO: handle other message types, including nested local sets
                if (metadataFrame.getMisbMessage() instanceof UasDatalinkMessage)
                {
                    UasDatalinkMessage uasDatalinkMessage = (UasDatalinkMessage) metadataFrame.getMisbMessage();
                    for (UasDatalinkTag tag : uasDatalinkMessage.getTags())
                    {
                        IUasDatalinkValue value = uasDatalinkMessage.getField(tag);
                        sb.append("<b>").append(tag).append(":</b> ").append(value.getDisplayableValue()).append("<br>");
                    }
                }
                if (metadataFrame.getMisbMessage() instanceof PredatorUavMessage)
                {
                    PredatorUavMessage message = (PredatorUavMessage) metadataFrame.getMisbMessage();
                    for (PredatorMetadataKey key : message.getKeys())
                    {
                        IPredatorMetadataValue value = message.getField(key);
                        sb.append("<b>").append(key).append(":</b> ").append(value.getDisplayableValue()).append("<br>");
                    }
                }
                sb.append("</body>");
                sb.append("</html>");
                setText(sb.toString());
            });

            previous = current;
        }
    }

    public final void clear() {
        this.setText("<html><head/><body/></html>");
    }
}
