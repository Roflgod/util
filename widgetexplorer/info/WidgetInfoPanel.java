package util.widgetexplorer.info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import util.widgetexplorer.WidgetExplorer;


public class WidgetInfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private InfoScrollPane scrollPaneInfo = new InfoScrollPane();
	private WidgetScrollPane scrollPaneWidgets;

	public WidgetInfoPanel(final WidgetExplorer script) {
		scrollPaneWidgets = new WidgetScrollPane(scrollPaneInfo, script);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{240, 262, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		GridBagConstraints gbc_scrollPaneWidgets = new GridBagConstraints();
		gbc_scrollPaneWidgets.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneWidgets.gridx = 0;
		gbc_scrollPaneWidgets.gridy = 0;
		add(scrollPaneWidgets, gbc_scrollPaneWidgets);

		GridBagConstraints gbc_scrollPaneInfo = new GridBagConstraints();
		gbc_scrollPaneInfo.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneInfo.gridx = 1;
		gbc_scrollPaneInfo.gridy = 0;
		add(scrollPaneInfo, gbc_scrollPaneInfo);
	}

	public void updateModel(String searchTxt) {
		scrollPaneWidgets.updateModel(searchTxt);
	}

	public void setSearch(JTextField search) {
		scrollPaneWidgets.setSearch(search);
	}
}
