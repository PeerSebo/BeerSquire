package src.nodes;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Distance;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;

import src.BeerSquire;
import src.Node;

public class BuyingNode extends Node {

	private final int BARTENDER = 1313;
	public Position barTile = new Position(3045, 3256);

	public BuyingNode(BeerSquire main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return !Inventory.isFull();
	}

	@Override
	public int execute() {
		if (barTile != null && Distance.between(Players.getLocal().getPosition(), barTile) >= 8) {
			Movement.walkToRandomized(barTile);
			Time.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
		}
		if (Dialog.isOpen()) {
			if (Dialog.isViewingChatOptions()) {
				Dialog.process("Could I buy a beer please?");
				Time.sleepUntil(() -> Dialog.isProcessing(), 250);
			}
			if (Dialog.canContinue()) {
				Dialog.processContinue();
				Time.sleepUntil(() -> Dialog.isProcessing(), 250);
			}
		} else if (Npcs.getNearest(BARTENDER) != null) {
			Npcs.getNearest(BARTENDER).interact("Talk-to");
			Time.sleepUntil(() -> Dialog.isOpen(), 5000);
		}
		return 500;
	}
}