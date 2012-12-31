package util.flagviewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.bot.handlers.ScriptHandler;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

class Script {
	private static ActiveScript script;
	private static String status = "Idle.";

	public static ActiveScript getInstance() {
		return script;
	}

	private static ScriptHandler getScriptHandler() {
		return Context.get().getScriptHandler();
	}

	public static String getStatus() {
		return status;
	}

	public static boolean setStatus(final String newStatus) {
		status = newStatus;
		return true;
	}

	public static void stop() {
		getScriptHandler().stop();
	}

	public static void submit(final Job... jobs) {
		for (final Job job : jobs) {
			script.getContainer().submit(job);
		}
	}

	private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;
	private Client client = Context.client();

	public Script(final ActiveScript script) {
		Script.script = script;
	}

	public Tree jobContainer() {
		return jobContainer;
	}

	public void logout(String itemName, int... itemIds) {
		if (Bank.isOpen() && Bank.getItemCount(itemIds) <= 0
				&& Bank.getItemCount(false) > 0) {
			if (Bank.isOpen())
				Bank.close();
			getInstance().log.info("No more " + itemName + " in bank.");
			Game.logout(true);
			stop();
		}
	}

	public synchronized void provide(final Node... jobs) {
		for (final Node job : jobs) {
			if (!jobsCollection.contains(job)) {
				jobsCollection.add(job);
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}

	public synchronized void revoke(final Node... jobs) {
	    for (final Node job : jobs) {
	        if (jobsCollection.contains(job)) {
	            jobsCollection.remove(job);
	        }
	    }
	    jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}

	public void reload() {
		if (client != Context.client()) {
			WidgetCache.purge();
			Context.get().getEventManager().addListener(script);
			client = Context.client();
		}
	}

	public void run() {
		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				script.getContainer().submit(job);
				job.join();
			}
		}
	}

	public void waitForLogin() {
		while (Game.getClientState() != Game.INDEX_MAP_LOADED)
			Task.sleep(500);
	}
}
