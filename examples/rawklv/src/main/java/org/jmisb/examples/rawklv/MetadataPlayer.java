package org.jmisb.examples.rawklv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.KlvParser;
import org.jmisb.core.klv.ArrayUtils;

public class MetadataPlayer {

    public MetadataPlayer() {
        setUpErrorHandlers();
    }

    private void setUpErrorHandlers() {
        InvalidDataHandler.getInstance()
                .setInvalidFieldEncodingStrategy(new PrintOnInvalidDataStrategy());
        InvalidDataHandler.getInstance()
                .setInvalidChecksumStrategy(new PrintOnInvalidDataStrategy());
    }

    public void play(String filename) throws IOException, KlvParseException {
        byte[] bytes = Files.readAllBytes(Paths.get(filename));
        List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
        messages.forEach(
                message -> {
                    outputTopLevelMessageHeader(message);
                    outputNestedKlvValue(message, 1);
                });
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
