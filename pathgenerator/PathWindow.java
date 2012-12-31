package util.pathgenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PathWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final PathWindow INSTANCE = new PathWindow();

	public static PathWindow get() {
		return INSTANCE;
	}

	private JPanel contentPane;
	private JTextPane textPane;
	private JButton btnUpdate;

	private PathWindow() {
		setBounds(100, 100, 418, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 50, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		textPane = new JTextPane();
		textPane.setText(PathGenerator.generate());
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 0;
		contentPane.add(textPane, gbc_textPane);

		btnUpdate = new JButton("Update Path");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.setText(PathGenerator.generate());
			}
		});
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.fill = GridBagConstraints.BOTH;
		gbc_btnUpdate.gridx = 0;
		gbc_btnUpdate.gridy = 1;
		contentPane.add(btnUpdate, gbc_btnUpdate);
	}

}