package util.flagviewer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import scripts.roflgod.framework.Script;
import scripts.roflgod.pathfinding.wrappers.Node;

@Manifest(name = "Flag Viewer", authors = "Roflgod", version = 0.01)
public class FlagViewer extends ActiveScript implements PaintListener, MouseListener {
	private static Tile tile;
	private static Node node;

	private static ArrayList<Tile> surrounding = new ArrayList<Tile>();
	private static int area = 0;

	private static boolean follow;

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
		final Tile player = Players.getLocal().getLocation();
		if (follow && (tile == null || !tile.equals(player))) {
			tile = player;
			node = Node.fromTile(tile);
			final int[][] flags = Walking.getCollisionFlags(tile.getPlane());

			getSurrounding();
			updateMasks(node.getFlag(flags));
			FlagGUI.get().updateData(node.getFlag(flags));
		}
		return 50;
	}

	@Override
	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		drawObject(g);
		drawSurrounding(g);
	}

	private void drawSurrounding(Graphics2D g) {
		final FontMetrics fm = g.getFontMetrics();
		final int[][] flags = Walking.getCollisionFlags(0);
		for (final Tile t : surrounding) {
			final Node n = Node.fromTile(t);
			if (n.isBlocked(flags)) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.white);
			}
			t.draw(g);
			final Point p = t.getPoint(0.5d, 0.5d, 0);
			if (n.getFlag(flags) != 0) {
				final String flag = n.getFlag(flags) + "";
				g.drawString(flag, (int) (p.x - (fm.stringWidth(flag) / 2.0)), p.y);
			}
		}
	}

	@SuppressWarnings("unused")
	private void drawFlags(Graphics2D g) {
		if (tile != null) {
			final FontMetrics fm = g.getFontMetrics();
			g.setColor(Color.white);
			final int[][] flags = Walking.getCollisionFlags(tile.getPlane());
			final int flag = node.getFlag(flags);
			final Point p = tile.getPoint(0.5d, 0.5d, 0);
			final String s = Integer.toString(flag);
			g.drawString(s, (int) (p.x - (fm.stringWidth(s) / 2.0)), p.y);
		}
	}

	private void drawObject(Graphics2D g) {
		if (tile != null) {
			g.setColor(Color.white);
			tile.draw(g);

			g.setColor(Color.blue);
			final SceneObject o = SceneEntities.getAt(tile);
			if (o != null) {
				o.getModel().draw(g);
			}
		}
	}

	private void updateMasks(int flag) {
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
		surrounding.clear();
		for (int x = -area; x <= area; x++) {
			for (int y = -area; y <= area; y++) {
				surrounding.add(tile.derive(x, y));
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
							tile = t;
							node = Node.fromTile(t);
							final int[][] flags = Walking.getCollisionFlags(tile.getPlane());
							final int flag = node.getFlag(flags);
							FlagGUI.get().updateData(flag);
							updateMasks(flag);
						}
					}
				}
			}
			getSurrounding();
		}
	}

	@Override public void mousePressed(MouseEvent e) { }
	@Override public void mouseReleased(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }

}
