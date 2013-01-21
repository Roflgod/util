package util.widgetexplorer.gui;

import javax.swing.tree.DefaultMutableTreeNode;

import org.powerbot.game.api.wrappers.widget.Widget;

public class WidgetTreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1L;

	public WidgetTreeNode(Widget widget) {
		super(widget);
		if (widget.getChildrenCount() > 0)
			add(new DefaultMutableTreeNode("")); // shows expansion icon if there are any children
	}

	@Override
	public String toString() {
        return userObject == null ? "" : "Widget-" + ((Widget) userObject).getId();
    }

	public Widget getWidget() {
		return (Widget) userObject;
	}
}
