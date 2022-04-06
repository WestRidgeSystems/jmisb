package org.jmisb.examples.movingfeatures;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.video.IFileEventListener;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.IVideoFileInput;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileInput;
import org.jmisb.api.video.VideoFileInputOptions;
import org.jmisb.st0601.FrameCenterLatitude;
import org.jmisb.st0601.FrameCenterLongitude;
import org.jmisb.st0601.NestedVmtiLocalSet;
import org.jmisb.st0601.PrecisionTimeStamp;
import org.jmisb.st0601.UasDatalinkMessage;
import org.jmisb.st0601.UasDatalinkTag;
import org.jmisb.st0903.VTargetSeries;
import org.jmisb.st0903.VmtiLocalSet;
import org.jmisb.st0903.VmtiMetadataKey;
import org.jmisb.st0903.vtarget.TargetHAE;
import org.jmisb.st0903.vtarget.TargetLocationOffsetLat;
import org.jmisb.st0903.vtarget.TargetLocationOffsetLon;
import org.jmisb.st0903.vtarget.VTargetMetadataKey;
import org.jmisb.st0903.vtarget.VTargetPack;

public class MovingFeaturesConverter implements IMetadataListener, IFileEventListener {

    private IVideoFileInput fileInput;
    private MovingFeaturesCollection movingFeaturesCollection = new MovingFeaturesCollection();

    public MovingFeaturesConverter() {}

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
        IMisbMessage misbMessage = metadataFrame.getMisbMessage();
        if (misbMessage instanceof UasDatalinkMessage) {
            UasDatalinkMessage st0601message = (UasDatalinkMessage) misbMessage;
            FrameCenterLatitude frameCentreLatitude = null;
            FrameCenterLongitude frameCentreLongitude = null;
            PrecisionTimeStamp precisionTimeStamp = null;
            if (st0601HasFrameCentre(st0601message)) {
                frameCentreLatitude =
                        (FrameCenterLatitude)
                                st0601message.getField(UasDatalinkTag.FrameCenterLatitude);
                frameCentreLongitude =
                        (FrameCenterLongitude)
                                st0601message.getField(UasDatalinkTag.FrameCenterLongitude);
                precisionTimeStamp =
                        (PrecisionTimeStamp)
                                st0601message.getField(UasDatalinkTag.PrecisionTimeStamp);
            }
            if (st0601message.getTags().contains(UasDatalinkTag.VmtiLocalDataSet)) {
                handleNestedVmtiLocalSet(
                        st0601message,
                        frameCentreLatitude,
                        frameCentreLongitude,
                        precisionTimeStamp);
            }
        }
    }

    private void handleNestedVmtiLocalSet(
            UasDatalinkMessage st0601message,
            FrameCenterLatitude frameCentreLatitude,
            FrameCenterLongitude frameCentreLongitude,
            PrecisionTimeStamp precisionTimeStamp) {
        NestedVmtiLocalSet st0903LocalSet =
                (NestedVmtiLocalSet) st0601message.getField(UasDatalinkTag.VmtiLocalDataSet);
        VmtiLocalSet vmtiLocalSet = st0903LocalSet.getVmti();
        if (vmtiLocalSet.getIdentifiers().contains(VmtiMetadataKey.VTargetSeries)) {
            handleTargetSeries(
                    vmtiLocalSet, frameCentreLatitude, frameCentreLongitude, precisionTimeStamp);
        }
    }

    private void handleTargetSeries(
            VmtiLocalSet vmtiLocalSet,
            FrameCenterLatitude frameCentreLatitude,
            FrameCenterLongitude frameCentreLongitude,
            PrecisionTimeStamp precisionTimeStamp) {
        VTargetSeries targets =
                (VTargetSeries) vmtiLocalSet.getField(VmtiMetadataKey.VTargetSeries);
        for (VTargetPack targetPack : targets.getVTargets()) {
            Integer targetIdentifier = targetPack.getTargetIdentifier();
            if (targetPackHasTargetOffsets(targetPack)) {
                addCoordinateToFeature(
                        targetPack,
                        targetIdentifier,
                        frameCentreLatitude,
                        frameCentreLongitude,
                        precisionTimeStamp);
            }
        }
    }

    private static boolean st0601HasFrameCentre(UasDatalinkMessage st0601message) {
        return st0601message.getTags().contains(UasDatalinkTag.FrameCenterLatitude)
                && st0601message.getTags().contains(UasDatalinkTag.FrameCenterLongitude);
    }

    private static boolean targetPackHasTargetOffsets(VTargetPack targetPack) {
        return targetPack.getIdentifiers().contains(VTargetMetadataKey.TargetLocationOffsetLat)
                && targetPack.getIdentifiers().contains(VTargetMetadataKey.TargetLocationOffsetLon);
    }

    private void addCoordinateToFeature(
            VTargetPack targetPack,
            Integer targetIdentifier,
            FrameCenterLatitude frameCentreLatitude,
            FrameCenterLongitude frameCentreLongitude,
            PrecisionTimeStamp precisionTimeStamp) {
        TargetLocationOffsetLat offsetLat =
                (TargetLocationOffsetLat)
                        targetPack.getField(VTargetMetadataKey.TargetLocationOffsetLat);
        TargetLocationOffsetLon offsetLon =
                (TargetLocationOffsetLon)
                        targetPack.getField(VTargetMetadataKey.TargetLocationOffsetLon);

        if ((frameCentreLatitude != null) && (frameCentreLongitude != null)) {
            double latitude = frameCentreLatitude.getDegrees() + offsetLat.getValue();
            double longitude = frameCentreLongitude.getDegrees() + offsetLon.getValue();
            Double hae = getHAE(targetPack);
            Coordinate coordinatePair = new Coordinate(longitude, latitude, hae);
            ZonedDateTime timestamp =
                    ZonedDateTime.of(precisionTimeStamp.getDateTime(), ZoneOffset.UTC);
            movingFeaturesCollection.addCoordinates(targetIdentifier, coordinatePair, timestamp);
        }
    }

    private Double getHAE(VTargetPack targetPack) {
        Double hae = null;
        if (targetPack.getIdentifiers().contains(VTargetMetadataKey.TargetHAE)) {
            TargetHAE targetHAE = (TargetHAE) targetPack.getField(VTargetMetadataKey.TargetHAE);
            hae = targetHAE.getHAE();
        }
        return hae;
    }

    @Override
    // This is from IFileEventListener
    public void onEndOfFile() {
        try {
            fileInput.close();
        } catch (IOException ex) {
            System.out.println("Failed to close file: " + ex.getMessage());
        }
        serialiseOutTrajectoryJSON();
    }

    private void serialiseOutTrajectoryJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File("target/vmti.json"), movingFeaturesCollection);
        } catch (IOException ex) {
            System.out.println("Did not write output: " + ex.getMessage());
        }
    }
}
