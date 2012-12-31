package util.flagviewer;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import scripts.roflgod.framework.Script;

class FlagGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private static FlagGUI INSTANCE = new FlagGUI();

	public static FlagGUI get() {
		return INSTANCE;
	}

	private JPanel contentPane;
	private final JLabel lblFlag = new JLabel("Flag:");
	private final JLabel lblFlagValue = new JLabel("");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();

	void updateData(int flag) {
		EventQueue.invokeLater(new Updater(flag));
	}

	JLabel getFlagValue() {
		return lblFlagValue;
	}

	JTable getTable() {
		return table;
	}

	private FlagGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Script.stop();
				dispose();
			}
		});

		setResizable(false);
		setTitle("Flag Viewer");
		setBounds(100, 100, 770, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_lblFlag = new GridBagConstraints();
		gbc_lblFlag.anchor = GridBagConstraints.WEST;
		gbc_lblFlag.insets = new Insets(0, 0, 5, 5);
		gbc_lblFlag.gridx = 0;
		gbc_lblFlag.gridy = 0;
		contentPane.add(lblFlag, gbc_lblFlag);

		GridBagConstraints gbc_lblFlagValue = new GridBagConstraints();
		gbc_lblFlagValue.anchor = GridBagConstraints.WEST;
		gbc_lblFlagValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblFlagValue.gridx = 1;
		gbc_lblFlagValue.gridy = 0;
		contentPane.add(lblFlagValue, gbc_lblFlagValue);

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, gbc_scrollPane);
		table.setModel(new DefaultTableModel(Fields.DATA, Fields.COLUMN_HEADS));
		table.getColumnModel().getColumn(0).setMinWidth(200);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setMinWidth(70);
		table.getColumnModel().getColumn(1).setMaxWidth(70);
		table.getColumnModel().getColumn(2).setMinWidth(200);
		table.getColumnModel().getColumn(2).setMaxWidth(200);
		table.getColumnModel().getColumn(3).setMinWidth(80);
		table.getColumnModel().getColumn(3).setMaxWidth(80);
		table.getColumnModel().getColumn(4).setMinWidth(200);
		table.getColumnModel().getColumn(4).setMaxWidth(200);

		scrollPane.setViewportView(table);
	}

}
