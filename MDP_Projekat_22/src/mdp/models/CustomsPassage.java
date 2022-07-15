package mdp.models;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public abstract class CustomsPassage implements ICustomsPassage {
	private static final long serialVersionUID = -2894127198803998101L;

	private BigInteger id;
	private boolean isOpen;
	private boolean isEntry;
	private List<ICustomsPassageStep> passageSteps;

	public CustomsPassage(BigInteger id, boolean isOpen, boolean isEntry, List<ICustomsPassageStep> passageSteps) {
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
	public List<ICustomsPassageStep> getPassageSteps() {
		return passageSteps;
	}

	public static List<ICustomsPassageStep> customsPassageSteps() {
		return Arrays.asList(new PoliceCheckStep(), new CustomsCheckStep());
	}
}
