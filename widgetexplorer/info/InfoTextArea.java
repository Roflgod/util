package util.widgetexplorer.info;

import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class InfoTextArea extends JTextArea {
	private static final long serialVersionUID = 1L;

	private static final String[] labels = {
		"Index",						// 0
		"Validated",					// 1
		"Visible",						// 2
		"Absolute location",			// 3
		"Relative location",			// 4
		"Width",						// 5
		"Height",						// 6
		"Id",							// 7
		"Type",							// 8
		"Special type",					// 9
		"Child id",						// 10
		"Child index",					// 11
		"Texture id",					// 12
		"Text",							// 13
		"Text color",					// 14
		"Shadow color",					// 15
		"Tooltip",						// 16
		"Border thickness",				// 17
		"Selected action",				// 18
		"Model id",						// 19
		"Model type",					// 20
		"Model zoom",					// 21
		"Inventory",					// 22
		"Child stack size",				// 23
		"Bound array index",			// 24
		"Scrollable area",				// 25
		"Parent id",					// 26
		"getHorizontalScrollPosition",	// 27
		"getVerticalScrollPosition",	// 28
		"getScrollableContentWidth",	// 29
		"getScrollableContentHeight",	// 30
		"getHorizontalScrollThumbSize",	// 31
		"getVerticalScrollThumbSize:" };// 32

	InfoTextArea() {
		setFont(UIManager.getFont("TextPane.font"));
		setBackground(UIManager.getColor("Button.background"));
	}

	void updateText(final WidgetChild w) {
		setText(getData(w));
	}

	/**
	 * Combines and formats the labels and the values.
	 * @param w
	 * @return
	 */
	private String getData(final WidgetChild w) {
		final String[] values = getValues(w);
		final StringBuilder s = new StringBuilder();
		for (int i = 0; i < labels.length; i++) {
			s.append(labels[i]);
			s.append(": ");
			s.append(values[i]);
			if (i < labels.length - 1)
				s.append("\r\n");
		}
		return s.toString();
	}

	private String[] getValues(final WidgetChild w) {
		final String[] values = new String[33];
		values[0] = Integer.toString(w.getIndex());
		values[1] = Boolean.toString(w.validate());
		values[2] = Boolean.toString(w.visible());
		values[3] = w.getAbsoluteLocation().toString();
		values[4] = w.getRelativeLocation().toString();
		values[5] = Integer.toString(w.getWidth());
		values[6] = Integer.toString(w.getHeight());
		values[7] = Integer.toString(w.getId());
		values[8] = Integer.toString(w.getType());
		values[9] = Integer.toString(w.getSpecialType());
		values[10] = Integer.toString(w.getChildId());
		values[11] = Integer.toString(w.getChildIndex());
		values[12] = Integer.toString(w.getTextureId());
		values[13] = w.getText();
		values[14] = Integer.toString(w.getTextColor());
		values[15] = Integer.toString(w.getShadowColor());
		values[16] = w.getTooltip();
		values[17] = Integer.toString(w.getBorderThickness());
		values[18] = w.getSelectedAction();
		values[19] = Integer.toString(w.getModelId());
		values[20] = Integer.toString(w.getModelType());
		values[21] = Integer.toString(w.getModelZoom());
		values[22] = Boolean.toString(w.isInventory());
		values[23] = Integer.toString(w.getChildStackSize());
		values[24] = Integer.toString(w.getBoundsArrayIndex());
		values[25] = Boolean.toString(w.isInScrollableArea());
		values[26] = Integer.toString(w.getParentId());
		values[27] = Integer.toString(w.getHorizontalScrollPosition());
		values[28] = Integer.toString(w.getVerticalScrollPosition());
		values[29] = Integer.toString(w.getScrollableContentWidth());
		values[30] = Integer.toString(w.getScrollableContentHeight());
		values[31] = Integer.toString(w.getHorizontalScrollThumbSize());
		values[32] = Integer.toString(w.getVerticalScrollThumbSize());
		return values;
	}

}
