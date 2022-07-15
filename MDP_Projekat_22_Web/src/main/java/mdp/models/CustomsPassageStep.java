package mdp.models;

public abstract class CustomsPassageStep implements ICustomsPassageStep {
	private static final long serialVersionUID = 4843414817898076312L;

	protected boolean isCustomsCheck;

	public CustomsPassageStep() {
	}

	public CustomsPassageStep(boolean isCustomsCheck) {
		super();
		this.isCustomsCheck = isCustomsCheck;
	}

	@Override
	public boolean isCustomsCheck() {
		return isCustomsCheck;
	}

	@Override
	public boolean isPoliceCheck() {
		return !isCustomsCheck;
	}

}
