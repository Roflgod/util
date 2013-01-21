package util.widgetexplorer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JFrame;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

@Manifest(name = "Widget Explorer", version = 0.1, authors = "Roflgod", description = "")
public class WidgetExplorer extends ActiveScript implements PaintListener {
	private final JFrame explorer;

	private WidgetChild selected;

	public void setSelectedWidget(final WidgetChild child) {
		selected = child;
	}

	public WidgetExplorer() {
		explorer = new GUI(this);
	}

	@Override
	public void onStart() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				explorer.setVisible(true);
			}
		});
	}

	@Override
	public void onStop() {
		explorer.dispose();
	}

	@Override
	public int loop() {
		return 0;
	}

	@Override
	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.cyan);
		if (selected != null) {
			Shape bounds = selected.getBoundingRectangle();
			g.draw(bounds);
		}
	}

}