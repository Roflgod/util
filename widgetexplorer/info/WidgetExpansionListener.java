package util.widgetexplorer.info;

import javax.swing.JTextField;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;

import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import util.widgetexplorer.gui.WidgetChildTreeNode;
import util.widgetexplorer.gui.WidgetTreeNode;

class WidgetExpansionListener implements TreeWillExpandListener {

	private JTextField search;

	@Override
	public void treeWillCollapse(TreeExpansionEvent event) { }

	@Override
	public void treeWillExpand(final TreeExpansionEvent event) {
		final int index = event.getPath().getPathCount();
		final String searchTxt = search.getText().toLowerCase();

		if (index == 2) {
			final WidgetTreeNode widgetNode = (WidgetTreeNode) event.getPath().getLastPathComponent();
			widgetNode.removeAllChildren();

			final Widget widget = widgetNode.getWidget();
			final WidgetChild[] children = widget.getChildren();
			for (final WidgetChild child : children) {
				final WidgetChildTreeNode childNode = new WidgetChildTreeNode(child);
				if (searchTxt.isEmpty() || child.getText().toLowerCase().contains(searchTxt)) {
					widgetNode.add(childNode);
				} else {
					final WidgetChild[] subChildren = child.getChildren();
					for (final WidgetChild subChild : subChildren) {
						if (subChild.getText().toLowerCase().contains(searchTxt)) {
							widgetNode.add(childNode);
						}
					}
				}
			}
		} else if (index == 3) {
			final WidgetChildTreeNode childNode = (WidgetChildTreeNode) event.getPath().getLastPathComponent();
			childNode.removeAllChildren();

			final WidgetChild child = childNode.getWidgetChild();
			final WidgetChild[] subChildren = child.getChildren();
			for (final WidgetChild subChild : subChildren) {
				if (searchTxt.isEmpty() || subChild.getText().toLowerCase().contains(searchTxt)) {
					final WidgetChildTreeNode subChildNode = new WidgetChildTreeNode(subChild);
					childNode.add(subChildNode);
				}
			}
		}
	}

	public void setSearch(JTextField search) {
		this.search = search;
	}

}