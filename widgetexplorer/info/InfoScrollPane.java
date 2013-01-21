package util.widgetexplorer.info;

import javax.swing.JScrollPane;

import org.powerbot.game.api.wrappers.widget.WidgetChild;


class InfoScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private InfoTextArea textArea = new InfoTextArea();

	InfoScrollPane() {
		setViewportView(textArea);
	}

	void updateText(final WidgetChild w) {
		textArea.updateText(w);
	}
}
