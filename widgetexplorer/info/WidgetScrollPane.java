package util.widgetexplorer.info;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import util.widgetexplorer.WidgetExplorer;
import util.widgetexplorer.gui.WidgetTreeNode;

class WidgetScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private final InfoScrollPane info;

	private JTree tree;
	private WidgetExpansionListener expansionListener = new WidgetExpansionListener();

	WidgetScrollPane(final InfoScrollPane info, final WidgetExplorer script) {
		this.info = info;
		setViewportView(getTree(script));
	}

	private JTree getTree(final WidgetExplorer script) {
		if (tree == null) {
			tree = new JTree();
			tree.setRootVisible(false);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeWillExpandListener(expansionListener);
			tree.addTreeSelectionListener(new WidgetSelectionListener(info, script));
		}
		return tree;
	}

	void updateModel(String searchTxt) {
		TreeNode node = getSearchedNodes(searchTxt);
		if (searchTxt.isEmpty()) {
			tree.setModel(new DefaultTreeModel(getWidgetNodes()));
		} else if (!node.toString().isEmpty()){
			tree.setModel(new DefaultTreeModel(node));
		}
	}

	private TreeNode getWidgetNodes() {
		final DefaultMutableTreeNode master = new DefaultMutableTreeNode();
		final Widget[] widgets = Widgets.getLoaded();
		for (final Widget widget : widgets) {
			addWidgetNode(master, widget);
		}
		return master;
	}

	private TreeNode getSearchedNodes(String searchTxt) {
		final DefaultMutableTreeNode master = new DefaultMutableTreeNode();
		final Widget[] widgets = Widgets.getLoaded();
		outer:
		for (final Widget widget : widgets) {
			if (widget.getChildrenCount() > 0) {
				final WidgetChild[] children = widget.getChildren();
				for (final WidgetChild child : children) {
					if (child.getText().toLowerCase().contains(searchTxt.toLowerCase())) {
						addWidgetNode(master, widget);
						continue outer;
					}

					final WidgetChild[] subChildren = child.getChildren();
					if (subChildren.length > 0) {
						for (WidgetChild subChild : subChildren) {
							if (subChild.getText().toLowerCase().contains(searchTxt.toLowerCase())) {
								addWidgetNode(master, widget);
								continue outer;
							}
						}
					}
				}
			}
		}
		return master;
	}

	private void addWidgetNode(final DefaultMutableTreeNode master, final Widget widget) {
		final DefaultMutableTreeNode widgetNode = new WidgetTreeNode(widget);
		widgetNode.add(new DefaultMutableTreeNode(""));
		master.add(widgetNode);
	}

	public void setSearch(JTextField search) {
		expansionListener.setSearch(search);
	}
}