package util.flagviewer;

import java.awt.Graphics;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.wrappers.RegionOffset;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.Tile.Flag;

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
	 * Derives a neighbor from this <code>Node</code> and sets it as the parent.
	 * This method creates a new <code>Node</code>.
	 *
	 * @param d
	 *            The <code>Direction</code> of the neighbor
	 * @return The derived <code>Node</code>
	 */
	public Node deriveNeighbor(final Direction d) {
		return deriveNeighbor(d.getX(), d.getY());
	}

	/**
	 * Derives a neighbor from this <code>Node</code> and sets it as the parent.
	 * This method creates a new <code>Node</code>.
	 *
	 * @param dx
	 *            Change in x
	 * @param dy
	 *            Change in y
	 * @return The derived <code>Node</code>
	 */
	private Node deriveNeighbor(final int dx, final int dy) {
		return new Node(x + dx, y + dy, plane);
	}

	public boolean isNotWalkable(final Direction d, final int[][] flags) {
		if (isBlocked(flags)
				|| x < 0 || x >= flags.length
				|| y < 0 || y >= flags.length)
			return true;

		final Node parent = this.deriveNeighbor(d.getOpposite());
		final int flag = this.getFlag(flags);

		if (d.isDiagonal()) {
			// [ ][|][ ]
			// [-][+][-]
			// [↗][|][ ]
			// Possible walls when moving diagonally shown by +/-/|
			for (final Direction component : d.getComponents()) {
				// [ ][|][x]
				// [ ][ ][-]      ↗ ==> ↑ → (components)
				// [↗][ ][ ]
				// Case 1: Component walls, (x) target
				final int compOppFlag = component.getOppFlag();
				if ((flag & compOppFlag) != 0)
					return true;

				// [ ][ ][x]
				// [-][ ][ ]
				// [↗][|][ ]
				// Case 2: Component walls, (x) target
				final int compFlag = component.getFlag();
				if ((parent.getFlag(flags) & compFlag) != 0)
					return true;

				// [+][ ]
				// [↗][+]
				// Case 3: Blocked neighbors, (+) blocked
				final Node derived = parent.deriveNeighbor(component);
				if (derived.isBlocked(flags))
					return true;
			}

			// [ ][ ][x]
			// [ ][+][ ]
			// [↗][ ][ ]
			// Case 4: Diagonal component wall, (+) corner wall
			int mask = d.getOppFlag();
			return (flag & mask) != 0;
		} else { // else is used for clarity; i.e. if d is cardinal.
			return (flag & d.getOppFlag()) != 0;
		}
	}

	public boolean isBlocked(final int[][] flags) {
		final int flag = getFlag(flags);
		final int OBJECT_MASK = Flag.OBJECT_BLOCK | Flag.OBJECT_TILE;
		return ((flag & Flag.BLOCKED) != 0 && (flag & OBJECT_MASK) != 0)
				|| (flag & Flag.DECORATION_BLOCK) != 0 || (flag & 0x200000) != 0;
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
