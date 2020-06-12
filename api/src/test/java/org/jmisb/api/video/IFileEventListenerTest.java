package org.jmisb.api.video;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * IFileEventListener tests.
 */
public class IFileEventListenerTest
{
    class TestListener implements IFileEventListener {
        boolean gotNotify = false;

        @Override
        public void onEndOfFile() {
            gotNotify = true;
        }
    }

    public IFileEventListenerTest()
    {
    }

    @Test
    public void checkListener()
    {
        TestListener testListener = new TestListener();
        IVideoFileInput fileInput = new VideoFileInput();
        fileInput.addFileEventListener(testListener);
        assertFalse(testListener.gotNotify);
        fileInput.notifyEOF();
        assertTrue(testListener.gotNotify);
        fileInput.removeFileEventListener(testListener);
    }

    @Test
    public void checkListenerRemoval()
    {
        TestListener testListener = new TestListener();
        IVideoFileInput fileInput = new VideoFileInput();
        fileInput.addFileEventListener(testListener);
        assertFalse(testListener.gotNotify);
        fileInput.removeFileEventListener(testListener);
        fileInput.notifyEOF();
        assertFalse(testListener.gotNotify);
    }
}
