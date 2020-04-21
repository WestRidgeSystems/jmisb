package org.jmisb.viewer;

import java.awt.BorderLayout;
import java.util.Enumeration;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.IKlvKey;

public class MetadataTreePanel extends JPanel implements IMetadataListener {

    private JTree tree;
    private static final String ROOT_NODE_NAME = "Metadata";

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

        // otherwise, the tree does not yet contain a node for this message type; add one and auto-expand it
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(displayHeader);
        addMetadataToNode(model, child, metadataFrame.getMisbMessage());
        final int index = rootNode.getChildCount();
        model.insertNodeInto(child, rootNode, index);
        tree.scrollPathToVisible(new TreePath(child.getPath()));
        tree.expandRow(index);
    }

    private void addMetadataToNode(DefaultTreeModel model, DefaultMutableTreeNode node, INestedKlvValue valueWithNestedValues)
    {
        valueWithNestedValues.getTags().forEach((tag) -> {
            doItem(tag, valueWithNestedValues, node, model);
        });
    }

    private void doItem(IKlvKey tag, INestedKlvValue valueWithNestedValues, DefaultMutableTreeNode node, DefaultTreeModel model)
    {
        IKlvValue value = valueWithNestedValues.getField(tag);
        Enumeration<?> childEnumeration = node.children();
        boolean didFind = false;
        while (childEnumeration.hasMoreElements())
        {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)childEnumeration.nextElement();
            MetadataEntry entry = (MetadataEntry) child.getUserObject();
            if (entry.getTag().equals(tag.toString()))
            {
                didFind = true;
                updateValue(entry, value, child, model);
                break;
            }
        }
        if (!didFind)
        {
            addValue(tag, value, model, node);
        }
    }

    private void addValue(IKlvKey tag, IKlvValue value, DefaultTreeModel model, DefaultMutableTreeNode node)
    {
        MetadataEntry metadataEntry = new MetadataEntry(tag.toString(), value.getDisplayName(), value.getDisplayableValue());
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(metadataEntry);
        if (value instanceof INestedKlvValue)
        {
            INestedKlvValue nested = (INestedKlvValue)value;
            nested.getTags().forEach((nestedTag) -> {
                IKlvValue childValue = nested.getField(nestedTag);
                addValue(nestedTag, childValue, model, child);
            });
        }
        model.insertNodeInto(child, node, node.getChildCount());
    }

    private void updateValue(MetadataEntry entry, IKlvValue value, DefaultMutableTreeNode node, DefaultTreeModel model)
    {
        if (!entry.getValue().equals(value.getDisplayableValue()))
        {
            entry.setValue(value.getDisplayableValue());
            node.setUserObject(entry);
            SwingUtilities.invokeLater(() -> model.reload(node));
        }
        if (value instanceof INestedKlvValue)
        {
            INestedKlvValue nested = (INestedKlvValue)value;
            addMetadataToNode(model, node, nested);
        }
    }
    
    void clear()
    {
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        model.setRoot(new DefaultMutableTreeNode(ROOT_NODE_NAME));
    }
}
