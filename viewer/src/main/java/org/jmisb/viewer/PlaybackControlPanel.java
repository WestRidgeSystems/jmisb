package org.jmisb.viewer;

import net.miginfocom.swing.MigLayout;
import org.jmisb.api.video.IVideoListener;
import org.jmisb.api.video.IVideoFileInput;
import org.jmisb.api.video.VideoFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * User interface for controlling playback
 */
public class PlaybackControlPanel extends JPanel implements IVideoListener
{
    private static Logger logger = LoggerFactory.getLogger(PlaybackControlPanel.class);
    private JButton playPauseButton;
    private JButton fastForwardButton;
    private JSlider seekSlider;

    private IVideoFileInput videoFileInput;

    private ImageIcon playIcon;
    private ImageIcon pauseIcon;
    private ImageIcon ffOnIcon;
    private ImageIcon ffOffIcon;

    private static final int iconSize = 24;

    private boolean wasPlaying;

    private final class SeekSlider extends JSlider implements MouseListener, MouseMotionListener
    {
        SeekSlider()
        {
            addMouseListener(this);
            addMouseMotionListener(this);

            // When the user clicks in the slider track, change the behavior so it jumps to the clicked
            // spot. This is done in the L&F.
            //
            setUI(new BasicSliderUI(seekSlider)
            {
                @Override
                protected void scrollDueToClickInTrack(int direction)
                {
                    int value = this.valueForXPosition(slider.getMousePosition().x);
                    setValue(value);
                }
            });
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            if (videoFileInput != null && videoFileInput.isOpen())
            {
                if (videoFileInput.isPlaying())
                {
                    wasPlaying = true;
                    videoFileInput.pause();
                } else
                {
                    wasPlaying = false;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if (videoFileInput != null && videoFileInput.isOpen())
            {
                double position = getValue() / 1000.0;

                videoFileInput.seek(position);
                if (wasPlaying && !videoFileInput.isPlaying())
                {
                    videoFileInput.play();
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
        }
    }

    PlaybackControlPanel()
    {
        // Load icons
        ImageIcon origPlayIcon = new ImageIcon(PlaybackControlPanel.class.getResource("/icons/play.png"));
        playIcon = new ImageIcon(origPlayIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        ImageIcon origPauseIcon = new ImageIcon(PlaybackControlPanel.class.getResource("/icons/pause.png"));
        pauseIcon = new ImageIcon(origPauseIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        ImageIcon origFfOnIcon = new ImageIcon(PlaybackControlPanel.class.getResource("/icons/fast-forward-on.png"));
        ffOnIcon = new ImageIcon(origFfOnIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        ImageIcon origFfOffIcon = new ImageIcon(PlaybackControlPanel.class.getResource("/icons/fast-forward-off.png"));
        ffOffIcon = new ImageIcon(origFfOffIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        // Play/pause button
        playPauseButton = new JButton(playIcon);
        playPauseButton.setEnabled(false);
        playPauseButton.addActionListener(l ->
        {
            if (videoFileInput != null)
            {
                if (videoFileInput.isPlaying())
                {
                    videoFileInput.pause();
                } else
                {
                    videoFileInput.play();
                }
            }
            updateButtons();
        });

        // Fast forward button
        fastForwardButton = new JButton(ffOffIcon);
        fastForwardButton.setEnabled(false);
        fastForwardButton.addActionListener(l ->
        {
            if (videoFileInput != null && videoFileInput.isPlaying())
            {
                if (videoFileInput.getPlaybackSpeed() < 4.0)
                {
                    videoFileInput.setPlaybackSpeed(4.0);
                }
                else
                {
                    videoFileInput.setPlaybackSpeed(1.0);
                }
            }
            updateButtons();
        });

        // Seek slider
        seekSlider = new SeekSlider();
        seekSlider.setValue(0);
        seekSlider.setEnabled(false);

        setLayout(new MigLayout("fill",
                "[48:48:48]0[48:48:48][]",
                ""));

        add(playPauseButton, "w 40!");
        add(fastForwardButton, "w 40!");
        add(seekSlider, "growx, aligny center");
    }

    void close()
    {
        playPauseButton.setEnabled(false);
        videoFileInput = null;
        updateButtons();
    }

    void setInput(IVideoFileInput input)
    {
        videoFileInput = input;

        playPauseButton.setEnabled(true);
        seekSlider.setEnabled(true);

        int durationMs = (int)Math.round(input.getDuration()*1000);
        seekSlider.setMinimum(0);
        seekSlider.setMaximum(durationMs);

        logger.debug("Setting slider range to [0, " + durationMs + "]");

        updateButtons();
    }

    @Override
    public void onVideoReceived(VideoFrame image)
    {
        // Need to update Swing on the EDT
        SwingUtilities.invokeLater(() ->
        {
            int position = (int) Math.round(videoFileInput.getPosition() * 1000);
            if (!seekSlider.getValueIsAdjusting())
            {
                seekSlider.setValue(position);
            }
            updateButtons();
        });
    }

    private void updateButtons()
    {
        if (videoFileInput == null)
        {
            playPauseButton.setEnabled(false);
            fastForwardButton.setEnabled(false);
        }

        if (videoFileInput == null || !videoFileInput.isPlaying())
        {
            playPauseButton.setIcon(playIcon);
            playPauseButton.setToolTipText("Play");
            fastForwardButton.setEnabled(false);
        } else
        {
            playPauseButton.setIcon(pauseIcon);
            playPauseButton.setToolTipText("Pause");

            fastForwardButton.setEnabled(true);
            fastForwardButton.setIcon(videoFileInput.getPlaybackSpeed() > 1.0 ? ffOnIcon : ffOffIcon);
            fastForwardButton.setToolTipText("" + Math.round(videoFileInput.getPlaybackSpeed()) + "x");
        }
    }
}
