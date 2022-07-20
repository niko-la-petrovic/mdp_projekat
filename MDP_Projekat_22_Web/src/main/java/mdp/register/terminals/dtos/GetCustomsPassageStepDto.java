package mdp.register.terminals.dtos;

import java.io.Serializable;

public class GetCustomsPassageStepDto implements Serializable {
	private static final long serialVersionUID = 2754050592576748193L;

	private boolean isCustomsCheck;

	public GetCustomsPassageStepDto() {
	}

	public GetCustomsPassageStepDto(boolean isCustomsCheck) {
		super();
		this.isCustomsCheck = isCustomsCheck;
	}

	public boolean isCustomsCheck() {
		return isCustomsCheck;
	}

	public void setCustomsCheck(boolean isCustomsCheck) {
		this.isCustomsCheck = isCustomsCheck;
	}
}
