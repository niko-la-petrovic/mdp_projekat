package mdp.register.terminals.dtos;

import java.io.Serializable;
import java.math.BigInteger;

public class GetCustomsPassageDto implements Serializable {
	private static final long serialVersionUID = -6731236082221131994L;

	private BigInteger id;
	private boolean isOpen;
	private boolean isEntry;
	private GetCustomsPassageStepDto[] passageSteps;

	public GetCustomsPassageDto() {
	}

	public GetCustomsPassageDto(BigInteger id, boolean isOpen, boolean isEntry,
			GetCustomsPassageStepDto[] passageSteps) {
		super();
		this.id = id;
		this.isOpen = isOpen;
		this.isEntry = isEntry;
		this.passageSteps = passageSteps;
	}

	public BigInteger getId() {
		return id;
	}

	public GetCustomsPassageStepDto[] getPassageSteps() {
		return passageSteps;
	}

	public boolean isEntry() {
		return isEntry;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setEntry(boolean isEntry) {
		this.isEntry = isEntry;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public void setPassageSteps(GetCustomsPassageStepDto[] passageSteps) {
		this.passageSteps = passageSteps;
	}

}
