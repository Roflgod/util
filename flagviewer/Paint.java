package util.flagviewer;

import static util.flagviewer.FlagViewer.node;
import static util.flagviewer.FlagViewer.surrounding;
import static util.flagviewer.FlagViewer.tile;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import scripts.roflgod.pathfinding.wrappers.Node;

class Paint {
	static void drawSurrounding(Graphics2D g) {
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

	static void drawFlags(Graphics2D g) {
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

	static void drawObject(Graphics2D g) {
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
}
