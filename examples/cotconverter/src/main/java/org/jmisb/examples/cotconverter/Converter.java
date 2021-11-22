package org.jmisb.examples.cotconverter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.jmisb.api.klv.st0805.KlvToCot;
import org.jmisb.api.klv.st0805.PlatformPosition;
import org.jmisb.api.klv.st0805.SensorPointOfInterest;
import org.jmisb.api.video.IFileEventListener;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.IVideoFileInput;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileInput;
import org.jmisb.api.video.VideoFileInputOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converter implements IMetadataListener, IFileEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);
    private IVideoFileInput fileInput;
    private final KlvToCot conv = new KlvToCot();
    private final InetAddress address;
    private final DatagramSocket socket;
    private final int port = 18999;
    private long baseOffset = -1;

    public Converter() throws UnknownHostException, SocketException {
        address = InetAddress.getByName("localhost");
        socket = new DatagramSocket();
    }

    public void play(String filename) throws IOException {
        VideoFileInputOptions videoFileInputOptions =
                new VideoFileInputOptions(false, true, true, false);
        fileInput = new VideoFileInput(videoFileInputOptions);
        fileInput.addMetadataListener(this);
        fileInput.addFileEventListener(this);
        fileInput.open(filename);
    }

    @Override
    public void onMetadataReceived(MetadataFrame metadataFrame) {
        IMisbMessage message = metadataFrame.getMisbMessage();
        if (message instanceof UasDatalinkMessage) {
            UasDatalinkMessage uasDatalinkMessage = (UasDatalinkMessage) message;
            updateBaseOffsetIfRequired(uasDatalinkMessage);
            UasDatalinkMessage retimedMessage = retimeMessage(uasDatalinkMessage);
            PlatformPosition platformPosition = conv.getPlatformPosition(retimedMessage);
            String ppXml = platformPosition.toXml();
            sendXmlToUDP(ppXml);
            SensorPointOfInterest sensorPosition = conv.getSensorPointOfInterest(retimedMessage);
            String spoiXml = sensorPosition.toXml();
            sendXmlToUDP(spoiXml);
        }
    }

    private void sendXmlToUDP(String ppXml) {
        byte[] buf = ppXml.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            LOGGER.error("Failed to send UDP: " + ex.getMessage());
        }
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

    private void updateBaseOffsetIfRequired(UasDatalinkMessage uasDatalinkMessage) {
        if (baseOffset < 0) {
            long nowMicroseconds = ZonedDateTime.now().toInstant().toEpochMilli() * 1000;
            PrecisionTimeStamp pts =
                    (PrecisionTimeStamp)
                            uasDatalinkMessage.getField(UasDatalinkTag.PrecisionTimeStamp);
            baseOffset = nowMicroseconds - pts.getMicroseconds();
        }
    }

    private UasDatalinkMessage retimeMessage(UasDatalinkMessage uasDatalinkMessage) {
        PrecisionTimeStamp pts =
                (PrecisionTimeStamp) uasDatalinkMessage.getField(UasDatalinkTag.PrecisionTimeStamp);
        long modifiedTime = pts.getMicroseconds() + baseOffset;
        PrecisionTimeStamp retimedPts = new PrecisionTimeStamp(modifiedTime);
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();
        for (UasDatalinkTag tag : uasDatalinkMessage.getIdentifiers()) {
            if (tag == UasDatalinkTag.PrecisionTimeStamp) {
                values.put(tag, retimedPts);
            } else {
                values.put(tag, uasDatalinkMessage.getField(tag));
            }
        }
        return new UasDatalinkMessage(values);
    }
}
