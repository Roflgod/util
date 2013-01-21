package util.widgetexplorer.gui;

import javax.swing.tree.DefaultMutableTreeNode;

import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class WidgetChildTreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1L;

	public WidgetChildTreeNode(WidgetChild child) {
		super(child);
		if (child.getChildren().length > 0)
			add(new DefaultMutableTreeNode("")); // shows expansion icon if there are any children
	}

	@Override
	public String toString() {
        return userObject == null ? "" : "WidgetChild-" + ((WidgetChild) userObject).getIndex();
    }

	public WidgetChild getWidgetChild() {
		return (WidgetChild) userObject;
	}
}
