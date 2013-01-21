package util.flagviewer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

@Manifest(name = "Flag Viewer", authors = "Roflgod", version = 0.01, description = "")
public class FlagViewer extends ActiveScript implements PaintListener, MouseListener {
	static Tile tile;
	static Node node;

	static List<Tile> surrounding = Collections.synchronizedList(new ArrayList<Tile>());
	static int area = 0;

	static boolean follow;

	static Direction direction = Direction.NORTH;

	static void modifyArea(final int amt) {
		if (area >= 0)
			area += amt;
	}

	static void toggleFollowPlayer() {
		follow = !follow;
	}

	@Override
	public void onStart() {
		new Script(this);
		FlagGUI.get().setVisible(true);
	}

	@Override
	public void onStop() {
		FlagGUI.get().dispose();
	}

	@Override
	public int loop() {
		if (Game.getClientState() != Game.INDEX_MAP_LOADED
				|| Players.getLocal() == null)
			return 500;
		final Tile player = Players.getLocal().getLocation();
		if (follow && (tile == null || !tile.equals(player))) {
			updateTile(player);
		}
		return 50;
	}

	@Override
	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		Paint.drawObject(g);
		Paint.drawSurrounding(g);
	}

	private static void updateTile(final Tile newTile) {
		tile = newTile;
		node = Node.fromTile(tile);
		final int[][] flags = Walking.getCollisionFlags(tile.getPlane());
		final int flag = node.getFlag(flags);
		FlagGUI.get().updateData(flag);
		updateMasks(flag);
		getSurrounding();
	}

	static void updateMasks(int flag) {
		if (tile != null) {
			long bin = 0b1;
			for (int i = 0; i <= 31; i++) {
				Fields.DATA[i][3] = Long.toHexString(flag & bin);
				Fields.DATA[i][4] = Long.toBinaryString(flag & bin);
				bin *= 2;
			}
			Fields.DATA[31][3] = Long.toHexString(flag & Flag.BLOCKED.getMask());
			Fields.DATA[31][4] = Long.toBinaryString(flag & Flag.BLOCKED.getMask());
		}
	}

	static void getSurrounding() {
		if (tile != null) {
			surrounding.clear();
			for (int x = -area; x <= area; x++) {
				for (int y = -area; y <= area; y++) {
					surrounding.add(tile.derive(x, y));
				}
			}
		}
	}

	@Override public void mouseClicked(MouseEvent e) {
		if (!follow && e.getButton() == MouseEvent.BUTTON1) {
			for (int i = -20; i <= 20; i++) {
				for (int i2 = -20; i2 <= 20; i2++) {
					final Tile t = Players.getLocal().getLocation().derive(i, i2);
					if (t.contains(e.getPoint())) {
						if (!t.equals(tile)) {
							updateTile(t);
						}
					}
				}
			}
		}
	}

	@Override public void mousePressed(MouseEvent e) { }
	@Override public void mouseReleased(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }

}
