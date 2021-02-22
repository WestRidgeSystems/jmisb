package org.jmisb.examples.gstsink;

import java.nio.ByteBuffer;
import java.util.List;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.AppSink;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvParser;

public class Main {

    /** @param args the command line arguments */
    public static void main(String[] args) {
        Gst.init();
        String srcVideo = args[0];
        System.out.println(srcVideo);
        String pipeSpec = "filesrc name=src ! tsdemux ! meta/x-klv ! appsink name=klv";
        Pipeline pipe = (Pipeline) Gst.parseLaunch(pipeSpec);
        pipe.getElementByName("src").set("location", srcVideo);
        AppSink klv = (AppSink) pipe.getElementByName("klv");
        klv.set("emit-signals", true);
        klv.set("sync", false);
        klv.connect(
                (AppSink.NEW_SAMPLE)
                        (AppSink elem) -> {
                            Sample sample = elem.pullSample();
                            Buffer buffer = sample.getBuffer();
                            ByteBuffer byteBuffer = buffer.map(false);
                            byte[] bytes = new byte[byteBuffer.capacity()];
                            byteBuffer.get(bytes);
                            try {
                                List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
                                for (IMisbMessage message : messages) {
                                    System.out.println(message.displayHeader());
                                    // you could dump out the rest of the message here
                                }
                            } catch (KlvParseException e) {
                                System.err.println(e);
                            }
                            buffer.unmap();
                            sample.dispose();
                            return FlowReturn.OK;
                        });

        klv.connect(
                (AppSink.NEW_PREROLL)
                        (AppSink elem) -> {
                            Sample sample = elem.pullSample();
                            ByteBuffer bytes = sample.getBuffer().map(false);
                            try {
                                System.out.println("preroll: " + bytes.array().length);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                            sample.dispose();
                            return FlowReturn.OK;
                        });
        Bus bus = pipe.getBus();
        bus.connect((Bus.EOS) obj -> Gst.quit());

        pipe.play();

        Gst.main();
    }
}
