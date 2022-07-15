package mdp.models;

import java.math.BigInteger;

public abstract class CustomsPassage implements ICustomsPassage {
	private BigInteger id;
	private boolean isOpen;
	private boolean isEntry;

	public CustomsPassage(BigInteger id, boolean isOpen, boolean isEntry) {
		super();
		this.id = id;
		this.isOpen = isOpen;
		this.isEntry = isEntry;
	}

	@Override
	public BigInteger getId() {
		return id;
	}

	@Override
	public boolean isOpen() {
		return isOpen;
	}

	@Override
	public boolean isEntry() {
		return isEntry;
	}

	@Override
	public boolean isExit() {
		return !isEntry;
	}

}
