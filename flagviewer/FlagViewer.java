package util.flagviewer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import scripts.roflgod.framework.Script;

@Manifest(name = "Flag Viewer", authors = "Roflgod", version = 0.01)
public class FlagViewer extends ActiveScript implements PaintListener, MouseListener {

	private Tile selected;
	private Node selectedNode;

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
		return 1000;
	}

	@Override public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		drawObject(g);
	}

	public void drawObject(Graphics2D g) {
		final FontMetrics fm = g.getFontMetrics();

		if (selected != null) {
			g.setColor(Color.white);
			selected.draw(g);

			g.setColor(Color.blue);
			final SceneObject o = SceneEntities.getAt(selected);
			if (o != null) {
				o.getModel().draw(g);
			}

			g.setColor(Color.white);
			final int[][] flags = Walking.getCollisionFlags(selected.getPlane());
			final int flag = selectedNode.getFlag(flags);
			final Point p = selected.getPoint(0.5d, 0.5d, 0);
			final String s = Integer.toString(flag);
			g.drawString(s, (int) (p.x - (fm.stringWidth(s) / 2.0)), p.y);
		}
	}

	private void updateMasks(int flag) {
		if (selected != null) {
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

	@Override public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			for (int i = -20; i <= 20; i++) {
				for (int i2 = -20; i2 <= 20; i2++) {
					final Tile tile = Players.getLocal().getLocation().derive(i, i2);
					if (tile.contains(e.getPoint())) {
						if (!tile.equals(selected)) {
							this.selected = tile;
							this.selectedNode = Node.fromTile(tile);
							final int[][] flags = Walking.getCollisionFlags(selected.getPlane());
							final int flag = selectedNode.getFlag(flags);
							FlagGUI.get().updateData(flag);
							updateMasks(flag);
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
