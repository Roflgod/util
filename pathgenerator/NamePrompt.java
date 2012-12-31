package util.pathgenerator;

import org.powerbot.game.api.methods.Environment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class NamePrompt extends JFrame {
	private static final NamePrompt INSTANCE = new NamePrompt();

	public static NamePrompt get() {
		return INSTANCE;
	}

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnOk;
	private JLabel lblEnterAName;
	private JTextField textField;

	private NamePrompt() {
		setResizable(false);
		setTitle("SavePath");
		setBounds(100, 100, 260, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		lblEnterAName = new JLabel("Enter a name:");
		GridBagConstraints gbc_lblEnterAName = new GridBagConstraints();
		gbc_lblEnterAName.anchor = GridBagConstraints.EAST;
		gbc_lblEnterAName.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterAName.gridx = 0;
		gbc_lblEnterAName.gridy = 0;
		contentPane.add(lblEnterAName, gbc_lblEnterAName);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (!textField.getText().isEmpty()) {
					String name = textField.getText();
					BufferedWriter out = null;
					try {
						try {
							File file = new File(Environment.getStorageDirectory(), name + ".txt");
							out = new BufferedWriter(new FileWriter(file));
							out.write(PathGenerator.generate());
						} finally {
							if (out != null)
								out.close();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridwidth = 2;
		gbc_btnOk.fill = GridBagConstraints.BOTH;
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 1;
		contentPane.add(btnOk, gbc_btnOk);
	}

	static void initialize() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					INSTANCE.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
