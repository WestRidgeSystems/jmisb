package org.jmisb.viewer;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st0603.TimeStatus;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.IVideoListener;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFrame;

public class MetadataTreePanel extends JPanel implements IMetadataListener, IVideoListener {

    private JTree tree;
    private static final String ROOT_NODE_NAME = "Metadata";
    private static final String VIDEO_METADATA_LABEL = "Video Frame Metadata";

    public MetadataTreePanel() {
        super(new BorderLayout());
        initTree();
    }

    private void initTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(ROOT_NODE_NAME);
        tree = new JTree(top);

        Icon emptyIcon = new TreeIcon();
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(emptyIcon);
        renderer.setOpenIcon(emptyIcon);
        renderer.setClosedIcon(emptyIcon);
        tree.setCellRenderer(renderer);
        tree.expandRow(0);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        this.add(tree);
    }

    @Override
    public void onMetadataReceived(MetadataFrame metadataFrame) {
        final String displayHeader = metadataFrame.getMisbMessage().displayHeader();
        final DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        final DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        // if a node already exists for the message type, update it and return
        Enumeration<?> childEnumeration = rootNode.children();
        while (childEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) childEnumeration.nextElement();
            if (child.toString().equals(displayHeader)) {
                addMetadataToNode(model, child, metadataFrame.getMisbMessage());
                return;
            }
        }
        // otherwise, the tree does not yet contain a node for this message type; add one and
        // auto-expand it
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(displayHeader);
        addMetadataToNode(model, child, metadataFrame.getMisbMessage());
        final int index = rootNode.getChildCount();
        model.insertNodeInto(child, rootNode, index);
        tree.invalidate();
        tree.scrollPathToVisible(new TreePath(child.getPath()));
        tree.expandRow(index);
    }

    private void addMetadataToNode(
            DefaultTreeModel model,
            DefaultMutableTreeNode node,
            INestedKlvValue valueWithNestedValues) {
        valueWithNestedValues
                .getIdentifiers()
                .forEach(
                        (tag) -> {
                            doItem(tag, valueWithNestedValues, node, model);
                        });
    }

    private void doItem(
            IKlvKey tag,
            INestedKlvValue valueWithNestedValues,
            DefaultMutableTreeNode node,
            DefaultTreeModel model) {
        IKlvValue value = valueWithNestedValues.getField(tag);
        Enumeration childEnumeration = node.children();
        boolean didFind = false;
        while (childEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) childEnumeration.nextElement();
            MetadataEntry entry = (MetadataEntry) child.getUserObject();
            if (entry.getTag().equals(tag.toString())) {
                didFind = true;
                updateValue(entry, value, child, model);
                break;
            }
        }
        if (!didFind) {
            addValue(tag, value, model, node);
        }
    }

    private void addValue(
            IKlvKey tag, IKlvValue value, DefaultTreeModel model, DefaultMutableTreeNode node) {
        MetadataEntry metadataEntry =
                new MetadataEntry(
                        tag.toString(), value.getDisplayName(), value.getDisplayableValue());
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(metadataEntry);
        if (value instanceof INestedKlvValue) {
            INestedKlvValue nested = (INestedKlvValue) value;
            nested.getIdentifiers()
                    .forEach(
                            (nestedTag) -> {
                                IKlvValue childValue = nested.getField(nestedTag);
                                addValue(nestedTag, childValue, model, child);
                            });
        }
        try {
            // Insert at the end. Wait is undesirable, but otherwise we get duplicate entries
            SwingUtilities.invokeAndWait(
                    () -> model.insertNodeInto(child, node, node.getChildCount()));
        } catch (InterruptedException | InvocationTargetException ex) {
            // Ignore at this stage
        }
    }

    private void updateValue(
            MetadataEntry entry,
            IKlvValue value,
            DefaultMutableTreeNode node,
            DefaultTreeModel model) {
        if (!entry.getValue().equals(value.getDisplayableValue())) {
            entry.setValue(value.getDisplayableValue());
            node.setUserObject(entry);
            SwingUtilities.invokeLater(() -> model.reload(node));
        }
        if (value instanceof INestedKlvValue) {
            INestedKlvValue nested = (INestedKlvValue) value;
            addMetadataToNode(model, node, nested);
        }
    }

    /**
     * Clear the tree.
     *
     * <p>This removes all content.
     */
    void clear() {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.setRoot(new DefaultMutableTreeNode(ROOT_NODE_NAME));
    }

    @Override
    public void onVideoReceived(VideoFrame frame) {
        if ((frame.getMiisCoreId() != null)
                || (frame.getTimeStamp() != null)
                || (frame.getTimeStatus() != null)) {
            final DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            final DefaultMutableTreeNode rootNode =
                    (DefaultMutableTreeNode) tree.getModel().getRoot();
            // if a node already exists for the message type, update it and return
            Enumeration<?> childEnumeration = rootNode.children();
            while (childEnumeration.hasMoreElements()) {
                DefaultMutableTreeNode child =
                        (DefaultMutableTreeNode) childEnumeration.nextElement();
                if (child.toString().equals(VIDEO_METADATA_LABEL)) {
                    addFrameMetadata(model, child, frame);
                    return;
                }
            }
            DefaultMutableTreeNode parent = new DefaultMutableTreeNode(VIDEO_METADATA_LABEL);
            addFrameMetadata(model, parent, frame);
            final int index = rootNode.getChildCount();
            model.insertNodeInto(parent, rootNode, index);
            tree.invalidate();
            tree.scrollPathToVisible(new TreePath(parent.getPath()));
            tree.expandRow(index);
        }
    }

    private void addFrameMetadata(
            DefaultTreeModel model, DefaultMutableTreeNode parent, VideoFrame frame) {
        if (frame.getTimeStamp() != null) {
            addTimeStampFrameMetadata(model, parent, frame.getTimeStamp());
        }
        if (frame.getTimeStatus() != null) {
            addTimeStatusFrameMetadata(model, parent, frame.getTimeStatus());
        }
        if (frame.getMiisCoreId() != null) {
            addMiisFrameMetadata(model, parent, frame.getMiisCoreId());
        }
    }

    private void addTimeStampFrameMetadata(
            DefaultTreeModel model, DefaultMutableTreeNode parent, ST0603TimeStamp timestamp) {
        MetadataEntry metadataEntry =
                new MetadataEntry("Time Stamp", "Time Stamp", timestamp.getDisplayableValue());
        addFrameMetadataEntry(metadataEntry, model, parent);
    }

    private void addTimeStatusFrameMetadata(
            DefaultTreeModel model, DefaultMutableTreeNode parent, TimeStatus status) {
        MetadataEntry metadataEntry =
                new MetadataEntry("Time Status", "Time Status", status.toString());
        addFrameMetadataEntry(metadataEntry, model, parent);
    }

    private void addMiisFrameMetadata(
            DefaultTreeModel model, DefaultMutableTreeNode parent, CoreIdentifier miisCoreId) {
        MetadataEntry metadataEntry =
                new MetadataEntry(
                        "MIIS Core Identifier",
                        "MIIS Core Identifier",
                        miisCoreId.getTextRepresentation());
        addFrameMetadataEntry(metadataEntry, model, parent);
    }

    private void addFrameMetadataEntry(
            MetadataEntry metadataEntry, DefaultTreeModel model, DefaultMutableTreeNode parent) {
        Enumeration childEnumeration = parent.children();
        boolean didFind = false;
        while (childEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) childEnumeration.nextElement();
            MetadataEntry entry = (MetadataEntry) child.getUserObject();
            if (entry.getTag().equals(metadataEntry.getTag())) {
                didFind = true;
                if (!entry.getValue().equals(metadataEntry.getValue())) {
                    entry.setValue(metadataEntry.getValue());
                    child.setUserObject(entry);
                    SwingUtilities.invokeLater(() -> model.reload(child));
                }
                break;
            }
        }
        if (!didFind) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(metadataEntry);
            try {
                SwingUtilities.invokeAndWait(
                        () -> model.insertNodeInto(node, parent, parent.getChildCount()));
            } catch (InterruptedException | InvocationTargetException ex) {
                // Ignore at this stage
            }
        }
    }
}
