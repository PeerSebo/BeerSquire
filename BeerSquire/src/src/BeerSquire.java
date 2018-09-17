package src;


import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

import src.nodes.BankingNode;
import src.nodes.BuyingNode;

@ScriptMeta(desc = "Buys beers from the bar in Port Sarim.", developer = "Sebo", name = "Beer Squire", version = 1.0D)
public class BeerSquire extends Script {

	private Node[] nodes;

	public void onStart() {
		nodes = new Node[] { new BankingNode(this), new BuyingNode(this) };
		if (!Movement.isRunEnabled()) {
			Movement.toggleRun(true);
		}
		Log.info("Beer Squire has been hired! Please bot responsibly.");
	}

	public void onStop() {
		Log.info("Thank you for hiring Beer Squire!");
	}

	public int loop() {
		for (Node node : nodes) {
			if (node.validate()) {
				return node.execute();
			}
		}
		Log.info("The Beer Squire got incredibly drunk and messed up.");
		Log.info("Please leave a report on the topic detailing what had happened.");
		return 500;
	}
}