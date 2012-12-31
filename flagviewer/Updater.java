package util.flagviewer;

import javax.swing.table.DefaultTableModel;

class Updater implements Runnable {

	private FlagGUI gui = FlagGUI.get();

	private int flag;

	public Updater(int flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		gui.getFlagValue().setText(Integer.toBinaryString(flag));
		gui.getTable().setModel(new DefaultTableModel(Fields.DATA, Fields.COLUMN_HEADS));
		gui.getTable().getColumnModel().getColumn(0).setMinWidth(200);
		gui.getTable().getColumnModel().getColumn(0).setMaxWidth(200);
		gui.getTable().getColumnModel().getColumn(1).setMinWidth(70);
		gui.getTable().getColumnModel().getColumn(1).setMaxWidth(70);
		gui.getTable().getColumnModel().getColumn(2).setMinWidth(200);
		gui.getTable().getColumnModel().getColumn(2).setMaxWidth(200);
		gui.getTable().getColumnModel().getColumn(3).setMinWidth(80);
		gui.getTable().getColumnModel().getColumn(3).setMaxWidth(80);
		gui.getTable().getColumnModel().getColumn(4).setMinWidth(200);
		gui.getTable().getColumnModel().getColumn(4).setMaxWidth(200);
	}

}
