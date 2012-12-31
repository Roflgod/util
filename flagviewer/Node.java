package util.flagviewer;

import java.awt.Graphics;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.wrappers.RegionOffset;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Adapted from my Jump Point Search implementation with unneeded parts removed.
 *
 * @author Roflgod
 */
class Node {
	private int x, y, plane;

	public Node(final int x, final int y, final int plane) {
		this.x = x;
		this.y = y;
		this.plane = plane;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPlane() {
		return plane;
	}

	public int getFlag(final int[][] flags) {
		return flags[x][y];
	}

	/**
	 * Does not include the parent field because that is not relevant to
	 * <code>Node</code> equality.
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof Node) {
			final Node node = (Node) o;
			return x == node.x && y == node.y && plane == node.plane;
		}
		return false;
	}

	public static Node fromTile(final Tile t) {
		final RegionOffset off = t.getRegionOffset();
		final Tile collisionOff = Walking.getCollisionOffset(off.getPlane());
		return new Node(off.getX() - collisionOff.getX(), off.getY() - collisionOff.getY(), t.getPlane());
	}

	public Tile toTile() {
		final Tile collisionOff = Walking.getCollisionOffset(plane);
		return new Tile(x + collisionOff.getX() + Game.getBaseX(), y + collisionOff.getY() + Game.getBaseY(), plane);
	}

	public void draw(final Graphics render) {
		final Tile tile = toTile();
		if (tile != null)
			tile.draw(render);
	}
}
