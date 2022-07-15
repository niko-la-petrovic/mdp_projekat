package mdp.models;

import java.math.BigInteger;

public abstract class CustomsPassage implements ICustomsPassage {
	private static final long serialVersionUID = -2894127198803998101L;

	private BigInteger id;
	private boolean isOpen;
	private boolean isEntry;
	private ICustomsPassageStep[] passageSteps;

	public CustomsPassage() {
	}

	public CustomsPassage(BigInteger id, boolean isOpen, boolean isEntry, ICustomsPassageStep[] passageSteps) {
		super();
		this.id = id;
		this.isOpen = isOpen;
		this.isEntry = isEntry;
		this.passageSteps = passageSteps;
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

	@Override
	public ICustomsPassageStep[] getPassageSteps() {
		return passageSteps;
	}

	public static ICustomsPassageStep[] customsPassageSteps() {
		return new ICustomsPassageStep[] { new PoliceCheckStep(), new CustomsCheckStep() };
	}
}
