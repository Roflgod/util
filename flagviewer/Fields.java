package util.flagviewer;

class Fields {
	static final String[] COLUMN_HEADS = { "Mask name", "Mask (hex)", "Mask (binary)", "Value (hex)", "Value (binary)" };
	static Object[][] DATA = new Object[32][5];
	static {
		long bin = 0b1;
		for (int i = 0; i <= 30; i++) {
			final Flag flag = Flag.getFlag(bin);
			DATA[i][0] = Flag.getFlag(bin) == Flag.WATER_BLOCK ? "WATER_BLOCK?" : Flag.getFlag(bin) == null ? "?" : flag;
			DATA[i][1] = Long.toHexString(bin);
			DATA[i][2] = Long.toBinaryString(bin);
			bin *= 2;
		}
		DATA[31][0] = Flag.BLOCKED.toString();
		DATA[31][1] = Long.toHexString(Flag.BLOCKED.getMask());
		DATA[31][2] = Long.toBinaryString(Flag.BLOCKED.getMask());
	}

	static Object[][] DEFAULT_DATA = new Object[33][5];
}