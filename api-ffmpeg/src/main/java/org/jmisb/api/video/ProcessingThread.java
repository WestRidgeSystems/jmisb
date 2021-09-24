package org.jmisb.api.video;

import org.jmisb.core.video.TimingUtils;

/** Thread allowing itself to be paused and unpaused. */
class ProcessingThread extends Thread {
    private final Object pauseLock = new Object();
    private boolean shutdown = false;
    private boolean paused = false;
    private boolean pauseRequested = false;

    /**
     * Pause if requested and check whether to shut down.
     *
     * @return True if the thread should shut down
     */
    protected boolean pauseOrResume() {
        synchronized (pauseLock) {
            if (shutdown) return true;
            if (pauseRequested) {
                paused = true;
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    return true;
                }

                paused = false;
                pauseRequested = false;

                if (shutdown) return true;
            }
        }
        return false;
    }

    protected void shutdown() {
        shutdown = true;
        play(); // to unblock
    }

    protected boolean isShutdown() {
        return shutdown;
    }

    /**
     * Pause the thread.
     *
     * <p>This method blocks until the processing thread is paused.
     */
    protected void pause() {
        requestPause();
        while (!isPaused()) {
            TimingUtils.shortWait(10);
        }
    }

    protected boolean isPaused() {
        return paused;
    }

    /**
     * Request the thread be paused.
     *
     * <p>This is a non-blocking call.
     */
    protected void requestPause() {
        pauseRequested = true;
    }

    protected boolean isPauseRequested() {
        return pauseRequested;
    }

    protected void play() {
        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }
}
