package src;
public abstract class Node {

	protected final BeerSquire main;

	public Node(BeerSquire main) {
		this.main = main;
	}

	public abstract boolean validate();

	public abstract int execute();

}