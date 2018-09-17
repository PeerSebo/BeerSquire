package src.nodes;

import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Distance;
import org.rspeer.runetek.api.component.DepositBox;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;

import src.BeerSquire;
import src.Node;

public class BankingNode extends Node {

	private final int BANK_DEPOSIT_BOX = 26254;
	private final int BEER = 1917;
	private final int COINS = 995;
	private Position bankTile;

	public BankingNode(BeerSquire main) {
		super(main);
	}

	@Override
	public boolean validate() {
		return Inventory.isFull();
	}

	@Override
	public int execute() {
		SceneObject box = SceneObjects.getNearest(BANK_DEPOSIT_BOX);
		bankTile = box.getPosition();
		if (box != null) {
			if (bankTile != null && Distance.between(Players.getLocal().getPosition(), bankTile) >= 8) {
				Movement.walkToRandomized(bankTile);
				Time.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
			}
			if (!DepositBox.isOpen()) {
				box.interact("Deposit");
				Time.sleepUntil(() -> DepositBox.isOpen(), 3000);
			} else if (DepositBox.isOpen() && !Players.getLocal().isMoving()) {
				DepositBox.depositAll(BEER);
				Time.sleepUntil(() -> !Inventory.containsAnyExcept(COINS), 3000);
				DepositBox.close();
				Time.sleepUntil(() -> !DepositBox.isOpen(), 500);
			}
		}
		return 500;
	}
}