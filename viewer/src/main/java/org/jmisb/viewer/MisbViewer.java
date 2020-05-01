package org.jmisb.viewer;

import net.miginfocom.swing.MigLayout;
import org.jmisb.api.video.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Main window for a sample Swing-based video player application
 */
public class MisbViewer extends JFrame implements ActionListener
{
    private static Logger logger = LoggerFactory.getLogger(MisbViewer.class);
    private IVideoInput videoInput;
    private VideoPanel videoPanel;
    private MetadataPanel metadataPanel;
    private JScrollPane metadataPanelScroll;
    private PlaybackControlPanel controlPanel;
    private JMenu fileMenu;
    private List<JMenuItem> mruFiles;
    private int mruStartPos;

    private MisbViewer()
    {
        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        // Side effect of decorated look and feel is that the JFrame's layout manager will respect
        // the minimum size of its components
        //
        JFrame.setDefaultLookAndFeelDecorated(true);

        new MisbViewer().setVisible(true);
    }

    private void initComponents()
    {
        setTitle("MISB Viewer");
        JMenuBar menuBar = new JMenuBar();

        // File menu
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem openFile = new JMenuItem("Open File...", KeyEvent.VK_O);
        openFile.setName("File|OpenFile");
        openFile.addActionListener(this);
        fileMenu.add(openFile);

        JMenuItem openUrl = new JMenuItem("Open URL...", KeyEvent.VK_U);
        openUrl.setName("File|OpenUrl");
        openUrl.addActionListener(this);
        fileMenu.add(openUrl);

        addMruMenuItems();

        fileMenu.addSeparator();

        JMenuItem close = new JMenuItem("Close", KeyEvent.VK_C);
        close.setName("File|Close");
        close.addActionListener(this);
        fileMenu.add(close);

        fileMenu.addSeparator();

        JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.setName("File|Exit");
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);

        // View menu
        JMenu viewMenu = new JMenu("View");
        fileMenu.setMnemonic(KeyEvent.VK_V);
        menuBar.add(viewMenu);

        JMenuItem streamInfo = new JMenuItem("Stream Info", KeyEvent.VK_I);
        streamInfo.setName("View|StreamInfo");
        streamInfo.addActionListener(this);
        viewMenu.add(streamInfo);

        setJMenuBar(menuBar);

        setLayout(new MigLayout(
                "fill",
                "",
                "[fill][38:38:38]")
        );

        videoPanel = new VideoPanel();
        metadataPanel = new MetadataPanel();
        metadataPanelScroll = new JScrollPane(metadataPanel);

        // Create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, videoPanel, metadataPanelScroll);
        splitPane.setDividerLocation(640 + splitPane.getInsets().left);
        splitPane.setResizeWeight(0.5);
        add(splitPane, "grow, wrap");

        controlPanel = new PlaybackControlPanel();
        add(controlPanel, "growx, alignx center");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof JMenuItem)
        {
            JMenuItem item = (JMenuItem)e.getSource();
            switch (item.getName())
            {
                case "File|OpenFile":
                    openFile();
                    break;
                case "File|OpenUrl":
                    openUrl();
                    break;
                case "File|Close":
                    closeVideo();
                    break;
                case "File|Exit":
                    closeApplication();
                    break;
                case "View|StreamInfo":
                    displayStreamInfo();
                    break;
                default:
                    if (item.getName().startsWith("File|Mru|"))
                    {
                        String[] parts = item.getName().split("\\|");
                        openFile(parts[2]);
                    }
                    break;
            }
        }
    }

    /**
     * Show file chooser to open a file
     */
    private void openFile()
    {
        JFileChooser fileChooser;

        // Starting directory for file chooser should be that of the last manually-opened file
        Preferences prefs = Preferences.userNodeForPackage(MisbViewer.class);
        String directoryName = prefs.get("openFileDir", "");
        if (!directoryName.isEmpty())
        {
            fileChooser = new JFileChooser(new File(directoryName));
        }
        else
        {
            fileChooser = new JFileChooser();
        }

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogTitle("Select Media File");

        fileChooser.setFileFilter(new FileNameExtensionFilter("Media Files", ViewerConstants.VIDEO_FORMATS));

        int returnVal = fileChooser.showOpenDialog(JFrame.getFrames()[0]);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            openFile(file.getAbsolutePath());

            // Update default directory
            File directory = file.getParentFile();
            prefs.put("openFileDir", directory.getAbsolutePath());
        }
    }

    private void openFile(String filename)
    {
        try
        {
            closeVideo();

            IVideoFileInput fileInput = new VideoFileInput(new VideoFileInputOptions());
            fileInput.open(filename);

            setTitle("jmisb - " + filename);

            fileInput.addFrameListener(videoPanel);
            fileInput.addMetadataListener(metadataPanel);
            fileInput.addFrameListener(controlPanel);

            controlPanel.setInput(fileInput);
            videoInput = fileInput;

            updateFileMruList(filename);
        }
        catch (IOException ex)
        {
            logger.error("Could not open file: " + filename, ex);
            JOptionPane.showMessageDialog(this,
                    "Could not open file: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayStreamInfo()
    {
        StreamInfoDialog dialog = new StreamInfoDialog(this, videoInput);
        dialog.setVisible(true);
    }

    private void addMruMenuItems()
    {
        mruFiles = new ArrayList<>();

        // Record the position in the file menu where MRU entries should start
        fileMenu.addSeparator();
        mruStartPos = fileMenu.getItemCount();

        updateFileMruList(null);
    }

    private void updateFileMruList(String filename)
    {
        for (JMenuItem mruItem : mruFiles)
        {
            fileMenu.remove(mruItem);
        }

        if (filename != null)
        {
            MruFileList.add(filename);
        }

        mruFiles = MruFileList.getList();

        int pos = mruStartPos;

        for (JMenuItem mruItem : mruFiles)
        {
            mruItem.addActionListener(this);
            fileMenu.insert(mruItem, pos++);
        }
    }

    /**
     * Show dialog to connect to URL
     */
    private void openUrl()
    {
        UrlDialog urlDialog = new UrlDialog(this, "Connect to Stream");
        urlDialog.setVisible(true);
    }

    /**
     * Close the file or stream
     */
    private void closeVideo()
    {
        controlPanel.close();
        if (videoInput != null)
        {
            videoInput.removeFrameListener(videoPanel);
            videoInput.removeMetadataListener(metadataPanel);
            videoInput.removeFrameListener(controlPanel);
            try
            {
                videoInput.close();
            } catch (IOException ex)
            {
                logger.error("An exception was thrown attempting to close the input", ex);
                JOptionPane.showMessageDialog(this,
                        "An exception was thrown attempting to close the input: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        videoPanel.clear();
        metadataPanel.clear();
    }

    /**
     * Close the application
     */
    private void closeApplication()
    {
        closeVideo();
        System.exit(0);
    }

    private class UrlDialog extends JDialog
    {
        private JTextField urlField;
        private JButton btnConnect;
        private JButton btnCancel;
        private String url = "";

        UrlDialog(Frame owner, String title)
        {
            super(owner, title);
            initComponents(owner);
        }

        private void initComponents(Frame owner)
        {
            urlField = new JTextField();
            urlField.setEditable(true);
            // TODO: MRU for URLs
            urlField.setText("udp://225.1.1.1:30120");
            urlField.setPreferredSize(new Dimension(400, 24));

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();

            cs.fill = GridBagConstraints.HORIZONTAL;

            JLabel labelUrl = new JLabel();
            labelUrl.setText("URL:");
            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            panel.add(labelUrl, cs);

            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 2;
            panel.add(urlField, cs);

            btnConnect = new JButton("Connect");
            btnCancel = new JButton("Cancel");

            btnConnect.addActionListener(e ->
            {
                url = urlField.getText();
                if (url != null)
                {
                    try
                    {
                        VideoStreamInputOptions options = new VideoStreamInputOptions();

                        videoInput = new VideoStreamInput(options);

                        videoInput.open(url);
                        owner.setTitle("jmisb - " + url);
                        videoInput.addFrameListener(videoPanel);
                        videoInput.addMetadataListener(metadataPanel);
                        dispose();
                    }
                    catch (IOException ex)
                    {
                        logger.error("Could not connect to url: " + url, ex);
                        JOptionPane.showMessageDialog(UrlDialog.this,
                                "Could not connect to stream: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            btnCancel.addActionListener(e -> dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(btnConnect);
            buttonPanel.add(btnCancel);

            getRootPane().setDefaultButton(btnConnect);

            getContentPane().add(panel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
            pack();
            setResizable(false);
            setLocationRelativeTo(owner);
        }
    }
}
