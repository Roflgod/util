package util.widgetexplorer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.powerbot.game.bot.Context;

import util.widgetexplorer.info.SearchPanel;
import util.widgetexplorer.info.WidgetInfoPanel;

class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private WidgetInfoPanel panelWidgetInfo;
	private SearchPanel panelSearch;

	GUI(final WidgetExplorer script) {
		panelWidgetInfo = new WidgetInfoPanel(script);
		panelSearch = new SearchPanel(panelWidgetInfo);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Context.get().getScriptHandler().stop();
			}
		});

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Widget Explorer");
		setBounds(100, 100, 640, 670);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 170, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_panelSearch = new GridBagConstraints();
		gbc_panelSearch.gridwidth = 3;
		gbc_panelSearch.insets = new Insets(5, 5, 5, 5);
		gbc_panelSearch.fill = GridBagConstraints.BOTH;
		gbc_panelSearch.gridx = 0;
		gbc_panelSearch.gridy = 0;
		contentPane.add(panelSearch, gbc_panelSearch);

		GridBagConstraints gbc_panelWidgetInfo = new GridBagConstraints();
		gbc_panelWidgetInfo.gridwidth = 3;
		gbc_panelWidgetInfo.fill = GridBagConstraints.BOTH;
		gbc_panelWidgetInfo.gridx = 0;
		gbc_panelWidgetInfo.gridy = 1;
		contentPane.add(panelWidgetInfo, gbc_panelWidgetInfo);
	}
}
