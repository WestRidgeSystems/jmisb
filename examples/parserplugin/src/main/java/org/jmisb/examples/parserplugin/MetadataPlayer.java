package org.jmisb.examples.parserplugin;

import java.io.IOException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.video.IFileEventListener;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.IVideoFileInput;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileInput;
import org.jmisb.api.video.VideoFileInputOptions;
import org.jmisb.core.klv.ArrayUtils;

public class MetadataPlayer implements IMetadataListener, IFileEventListener {

    private IVideoFileInput fileInput;

    public MetadataPlayer() {}

    public void play(String filename) throws IOException {
        // These options configure for metadata decode only
        VideoFileInputOptions videoFileInputOptions =
                new VideoFileInputOptions(false, true, false, false);
        fileInput = new VideoFileInput(videoFileInputOptions);
        fileInput.addMetadataListener(this);
        fileInput.addFileEventListener(this);
        fileInput.open(filename);
    }

    @Override
    // This is from IMetadataListener
    public void onMetadataReceived(MetadataFrame metadataFrame) {
        outputTopLevelMessageHeader(metadataFrame.getMisbMessage());
        outputNestedKlvValue(metadataFrame.getMisbMessage(), 1);
    }

    private void outputTopLevelMessageHeader(IMisbMessage misbMessage) {
        String displayHeader = misbMessage.displayHeader();
        if (displayHeader.equalsIgnoreCase("Unknown")) {
            System.out.println(
                    displayHeader
                            + " ["
                            + ArrayUtils.toHexString(misbMessage.getUniversalLabel().getBytes())
                                    .trim()
                            + "]");
            outputUnknownMessageContent(misbMessage.frameMessage(true));
        } else {
            System.out.println(displayHeader);
        }
    }

    private void outputUnknownMessageContent(byte[] frameMessage) {
        System.out.println(ArrayUtils.toHexString(frameMessage, 16, true));
    }

    @Override
    // This is from IFileEventListener
    public void onEndOfFile() {
        try {
            fileInput.close();
        } catch (IOException ex) {
            System.out.println("Failed to close file: " + ex.getMessage());
        }
    }

    private void outputNestedKlvValue(INestedKlvValue nestedKlvValue, int indentationLevel) {
        for (IKlvKey identifier : nestedKlvValue.getIdentifiers()) {
            IKlvValue value = nestedKlvValue.getField(identifier);
            outputValueWithIndentation(value, indentationLevel);
            // if this has nested content, output that at the next indentation level
            if (value instanceof INestedKlvValue) {
                outputNestedKlvValue((INestedKlvValue) value, indentationLevel + 1);
            }
        }
    }

    private void outputValueWithIndentation(IKlvValue value, int indentationLevel) {
        for (int i = 0; i < indentationLevel; ++i) {
            System.out.print("\t");
        }
        System.out.println(value.getDisplayName() + ": " + value.getDisplayableValue());
    }
}
