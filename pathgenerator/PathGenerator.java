package util.pathgenerator;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.LocalPath;

import java.awt.*;
import java.util.Map;

@Manifest(authors = { "Roflgod" }, name = "Path Generator", description = "", version = 0.1)
public class PathGenerator extends ActiveScript implements PaintListener {

	static Tile endPos;
	static Tile[] tiles;

	private final GUI gui = new GUI();

	@Override
	public void onStart() {
		if (Game.getClientState() == Game.INDEX_MAP_LOADING)
			endPos = Players.getLocal().getLocation();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				gui.setVisible(true);
			}
		});
	}

	@Override
	public void onStop() {
		if (PathWindow.get().isVisible())
			PathWindow.get().dispose();
		if (NamePrompt.get().isVisible())
			NamePrompt.get().dispose();
		gui.dispose();
	}

	@Override
	public int loop() {
		return 10000;
	}

	private final Map<Object, Object> ANTIALIASING = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	@Override
	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHints(ANTIALIASING);

		g.setColor(Color.WHITE);

		if (endPos != null) {
			LocalPath path = findPath(PathGenerator.endPos);
			if (path.getNext() != null) {
				path.init();
				tiles = path.getTilePath().toArray();
				for (Tile tile : tiles) {
					tile.draw(g);
				}
			}
		}
	}

	public static LocalPath findPath(final Tile tile) {
		LocalPath path = new LocalPath(tile);
		return path;
	}

	public static String generate() {
		StringBuilder s = new StringBuilder();
		s.append("public static final Tile[] ");
		s.append("path");
		s.append(" = {\n");
		for (int i = 0; i < tiles.length; i++) {
			if (i % 2 == 0)
				s.append("\t");
			s.append("new Tile");
			s.append(tiles[i].toString());
			if (i < tiles.length - 1)
				s.append(", ");
			if ((i - 1) % 2 == 0)
				s.append("\n");
		}
		s.append(" };");
		return s.toString();
	}

}