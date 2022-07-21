package mdp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomsPassageStep {
	private static final long serialVersionUID = 4843414817898076312L;

	private boolean isCustomsCheck;

	public CustomsPassageStep() {
	}

	public CustomsPassageStep(boolean isCustomsCheck) {
		super();
		this.isCustomsCheck = isCustomsCheck;
	}

	public void setCustomsCheck(boolean isCustomsCheck) {
		this.isCustomsCheck = isCustomsCheck;
	}

	public boolean isCustomsCheck() {
		return isCustomsCheck;
	}

	@JsonIgnore
	public boolean isPoliceCheck() {
		return !isCustomsCheck;
	}

}
