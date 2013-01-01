package util.flagviewer;

/**
 * Direction implementation.
 *
 * @author Roflgod
 *
 */
enum Direction {
	NORTHWEST(-1, 1, Flags.WALL_BLOCK_NORTHWEST),
	NORTH(0, 1, Flags.WALL_BLOCK_NORTH),
	NORTHEAST(1, 1, Flags.WALL_BLOCK_NORTHEAST),
	EAST(1, 0, Flags.WALL_BLOCK_EAST),
	SOUTHEAST(1, -1, Flags.WALL_BLOCK_SOUTHEAST),
	SOUTH(0, -1, Flags.WALL_BLOCK_SOUTH),
	SOUTHWEST(-1, -1, Flags.WALL_BLOCK_SOUTHWEST),
	WEST(-1, 0, Flags.WALL_BLOCK_WEST);

	private final int dx;
	private final int dy;
	/**
	 * <p>
	 * The flag is is opposite in direction to check if it is possible to go in
	 * that direction.
	 * </p>
	 * <p>
	 * Eg. You are going east and you hit a block. To see if you can continue in
	 * that direction, you must check if the block has a wall to the west.
	 * </p>
	 * <p>
	 * -->[&nbsp;&nbsp;&nbsp;]
	 * </p>
	 */
	private final int flag;

	private Direction(final int dx, final int dy, final int opposingFlag) {
		this.dx = dx;
		this.dy = dy;
		this.flag = opposingFlag;
	}

	public int getX() {
		return dx;
	}

	public int getY() {
		return dy;
	}

	public int getFlag() {
		return flag;
	}

	public int getOppFlag() {
		return getOpposite().getFlag();
	}

	public static Direction fromOppFlag(int oppFlag) {
		for (Direction d : Direction.values()) {
			if (d.getOppFlag() == oppFlag)
				return d;
		}
		return null;
	}

	/**
	 * <p>
	 * The <code>Direction</code> values mapped to delta (change) values plus 1.
	 * The 1 is added to force the values to be positive.
	 * </p>
	 * <p>
	 * Position (0, 0) is null because it is the same <code>Node</code> and is
	 * not in any direction.
	 * </p>
	 */
	private static final Direction[][] DIRECTIONS = {
		{ SOUTHWEST, WEST, NORTHWEST },
		{ SOUTH, null, NORTH },
		{ SOUTHEAST, EAST, NORTHEAST },
	};

	/**
	 * @param x
	 *            The current node.
	 * @param n
	 *            The neighbor.
	 * @return The direction from x to n.
	 */
	public static Direction get(final Node x, final Node n) {
		return get(n.getX() - x.getX(), n.getY() - x.getY());
	}


	public static Direction get(final Direction comp1, final Direction comp2) {
		final int dx = comp1.getX() != 0 ? comp1.getX() : comp2.getX();
		final int dy = comp1.getY() != 0 ? comp1.getY() : comp2.getY();
		return get(dx, dy);
	}

	/**
	 * Uses the change in x (delta x, dx) as the index of the 1<sup>st</sup>
	 * dimension of the array and the change in y (delta y, dy) as the index of
	 * the 2<sup>nd</sup> dimension of the array. See the
	 * <code>DIRECTIONS</code> array for why 1 is added to the delta values.
	 *
	 * @param dx
	 *            change in x (delta x)
	 * @param dy
	 *            change in y (delta y)
	 * @return The direction from (0,0) to (dx, dy)
	 */
	private static Direction get(final int dx, final int dy) {
		return DIRECTIONS[dx + 1][dy + 1];
	}

	/**
	 * @return The set of cardinal (horizontal or vertical) directions
	 */
	public static Direction[] getCardinal() {
		return new Direction[] { NORTH, EAST, SOUTH, WEST };
	}

	public static Direction[] getHorizontal() {
		return new Direction[] { EAST, WEST };
	}

	public static Direction[] getVertical() {
		return new Direction[] { NORTH, SOUTH };
	}

	/**
	 * @return The set of ordinal (diagonal) directions
	 */
	public static Direction[] getOrdinal() {
		return new Direction[] { NORTHWEST, NORTHEAST, SOUTHEAST, SOUTHWEST };
	}

	public Direction getOpposite() {
		return get(-dx, -dy);
	}

	/**
	 * <p>
	 * This method returns the array in the form { y, x } to conform to the way
	 * the direction is spoken.
	 * </p>
	 * <p>
	 * Eg. NORTHWEST -> { NORTH, WEST }
	 * </p>
	 *
	 * @return The x and y components in the form { y, x } if this direction is
	 *         an ordinal direction; otherwise, the original direction if it is
	 *         cardinal.
	 */
	public Direction[] getComponents() {
		return isDiagonal() ? new Direction[] { get(0, dy), get(dx, 0) }
				: new Direction[] { this };
	}

	public Direction getVerticalComponent() {
		return isVertical() ? getComponents()[0] : null;
	}

	public Direction getHorizontalComponent() {
		return isHorizontal() ? getComponents()[1] : null;
	}

	/**
	 * @return The set of ordinal directions that have this direction as a
	 *         component
	 * @throws IncompatibleDirectionException
	 */
	public Direction[] getResultants() {
		try {
			return getResultantsE();
		} catch (IncompatibleDirectionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Direction[] getResultantsE() throws IncompatibleDirectionException {
		if (isDiagonal())
			throw new IncompatibleDirectionException();
		if (isVertical())
			return new Direction[] { get(-1, dy), get(1, dy) };
		else // if horizontal
			return new Direction[] { get(dx, 1), get(dx, -1) };
	}

	/**
	 * @return Whether the direction is a diagonal (ordinal).
	 */
	public boolean isDiagonal() {
		return dx != 0 && dy != 0;
	}

	public boolean isVertical() {
		return dx == 0 && dy != 0;
	}

	public boolean isHorizontal() {
		return dx != 0 && dy == 0;
	}

	public static String toString(final Direction[] components) {
		final int len = components.length;
		String s = "";
		if (len > 0)
			s += components[0];
		if (len > 1) {
			for (int i = 1; i < len; i++) {
				s += ", " + components[i];
			}
		}
		return s;
	}

	public class IncompatibleDirectionException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
