package util.widgetexplorer.info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private WidgetInfoPanel panel;
	private JLabel lblFilter = new JLabel("Filter:");
	private JTextField txtSearch;
	private JButton btnUpdate;

	public SearchPanel(WidgetInfoPanel panel) {
		this.panel = panel;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 170, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		GridBagConstraints gbc_lblFilter = new GridBagConstraints();
		gbc_lblFilter.insets = new Insets(0, 0, 0, 5);
		gbc_lblFilter.anchor = GridBagConstraints.EAST;
		gbc_lblFilter.gridx = 0;
		gbc_lblFilter.gridy = 0;
		add(lblFilter, gbc_lblFilter);

		GridBagConstraints gbc_txtSearch = new GridBagConstraints();
		gbc_txtSearch.insets = new Insets(0, 0, 0, 5);
		gbc_txtSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSearch.gridx = 1;
		gbc_txtSearch.gridy = 0;
		add(getTxtSearch(), gbc_txtSearch);

		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.anchor = GridBagConstraints.WEST;
		gbc_btnUpdate.gridx = 2;
		gbc_btnUpdate.gridy = 0;
		add(getBtnUpdate(), gbc_btnUpdate);

		panel.updateModel(txtSearch.getText());
		panel.setSearch(txtSearch);
	}

	private JTextField getTxtSearch() {
		if (txtSearch == null) {
			txtSearch = new JTextField();
			txtSearch.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						panel.updateModel(txtSearch.getText());
					}
				}
			});
			txtSearch.setColumns(10);
		}
		return txtSearch;
	}

	private final Action update = new AbstractAction() {
		private static final long serialVersionUID = -5577384296515697814L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (txtSearch.getText() != null) {
				panel.updateModel(txtSearch.getText());
			}
		}
	};

	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton("Update");
			btnUpdate.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"), "update");
			btnUpdate.getActionMap().put("update", update);
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (txtSearch.getText() != null) {
						panel.updateModel(txtSearch.getText());
					}
				}
			});
		}
		return btnUpdate;
	}
}
