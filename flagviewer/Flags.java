package util.flagviewer;

class Flags {
	static final int WALL_NORTHWEST = 0x1;
	static final int WALL_NORTH = 0x2;
	static final int WALL_NORTHEAST = 0x4;
	static final int WALL_EAST = 0x8;
	static final int WALL_SOUTHEAST = 0x10;
	static final int WALL_SOUTH = 0x20;
	static final int WALL_SOUTHWEST = 0x40;
	static final int WALL_WEST = 0x80;

	static final int OBJECT_TILE = 0x100;

	static final int WALL_BLOCK_NORTHWEST = 0x200;
	static final int WALL_BLOCK_NORTH = 0x400;
	static final int WALL_BLOCK_NORTHEAST = 0x800;
	static final int WALL_BLOCK_EAST = 0x1000;
	static final int WALL_BLOCK_SOUTHEAST = 0x2000;
	static final int WALL_BLOCK_SOUTH = 0x4000;
	static final int WALL_BLOCK_SOUTHWEST = 0x8000;
	static final int WALL_BLOCK_WEST = 0x10000;

	static final int OBJECT_BLOCK = 0x20000;
	static final int DECORATION_BLOCK = 0x40000;

	static final int WALL_ALLOW_RANGE_NORTHWEST = 0x400000;
	static final int WALL_ALLOW_RANGE_NORTH = 0x800000;
	static final int WALL_ALLOW_RANGE_NORTHEAST = 0x1000000;
	static final int WALL_ALLOW_RANGE_EAST = 0x2000000;
	static final int WALL_ALLOW_RANGE_SOUTHEAST = 0x4000000;
	static final int WALL_ALLOW_RANGE_SOUTH = 0x8000000;
	static final int WALL_ALLOW_RANGE_SOUTHWEST = 0x10000000;
	static final int WALL_ALLOW_RANGE_WEST = 0x20000000;

	static final int OBJECT_ALLOW_RANGE = 0x40000000;

	static final int BLOCKED = 0x1280100;
}

