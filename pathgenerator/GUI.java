package util.pathgenerator;

import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.bot.Context;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnSetEnd;
	private JButton btnGenPath;
	private JButton btnSavePath;
	private JButton btnOpenFolder;

	GUI() {
		setResizable(false);
		setTitle("SavePath");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Context.resolve().getScriptHandler().stop();
			}
		});

		setBounds(100, 100, 200, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		btnSetEnd = new JButton("Set end tile");
		btnSetEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PathGenerator.endPos = Players.getLocal().getLocation();
			}
		});
		GridBagConstraints gbc_btnSetEnd = new GridBagConstraints();
		gbc_btnSetEnd.insets = new Insets(0, 0, 5, 0);
		gbc_btnSetEnd.fill = GridBagConstraints.BOTH;
		gbc_btnSetEnd.gridx = 0;
		gbc_btnSetEnd.gridy = 0;
		contentPane.add(btnSetEnd, gbc_btnSetEnd);

		btnGenPath = new JButton("Generate path");
		btnGenPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PathGenerator.tiles != null) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							PathWindow.get().setVisible(true);
						}
					});
				}
			}
		});
		GridBagConstraints gbc_btnGenPath = new GridBagConstraints();
		gbc_btnGenPath.insets = new Insets(0, 0, 5, 0);
		gbc_btnGenPath.fill = GridBagConstraints.BOTH;
		gbc_btnGenPath.gridx = 0;
		gbc_btnGenPath.gridy = 1;
		contentPane.add(btnGenPath, gbc_btnGenPath);

		btnSavePath = new JButton("Save path");
		btnSavePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PathGenerator.tiles != null) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							NamePrompt.get().setVisible(true);
						}
					});
				}
			}
		});
		GridBagConstraints gbc_btnSavePath = new GridBagConstraints();
		gbc_btnSavePath.insets = new Insets(0, 0, 5, 0);
		gbc_btnSavePath.fill = GridBagConstraints.BOTH;
		gbc_btnSavePath.gridx = 0;
		gbc_btnSavePath.gridy = 2;
		contentPane.add(btnSavePath, gbc_btnSavePath);

		btnOpenFolder = new JButton("Open folder");
		btnOpenFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec("explorer.exe " + Environment.getStorageDirectory());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnOpenFolder = new GridBagConstraints();
		gbc_btnOpenFolder.fill = GridBagConstraints.BOTH;
		gbc_btnOpenFolder.gridx = 0;
		gbc_btnOpenFolder.gridy = 3;
		contentPane.add(btnOpenFolder, gbc_btnOpenFolder);
	}

}