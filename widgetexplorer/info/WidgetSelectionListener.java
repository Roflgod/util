package util.widgetexplorer.info;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.powerbot.game.api.wrappers.widget.WidgetChild;

import util.widgetexplorer.WidgetExplorer;
import util.widgetexplorer.gui.WidgetChildTreeNode;

class WidgetSelectionListener implements TreeSelectionListener {
	private final InfoScrollPane info;
	private final WidgetExplorer script;

	WidgetSelectionListener(final InfoScrollPane info, final WidgetExplorer script) {
		this.info = info;
		this.script = script;
	}

	public void valueChanged(TreeSelectionEvent e) {
		if (e.getPath().getPathCount() > 2) {
			final WidgetChildTreeNode childNode = (WidgetChildTreeNode) e.getPath().getLastPathComponent();
			final WidgetChild child = childNode.getWidgetChild();
			info.updateText(child);
			script.setSelectedWidget(child);
		}
	}
}