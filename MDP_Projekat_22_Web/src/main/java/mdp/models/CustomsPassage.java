package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;

public class CustomsPassage implements Serializable {
	private static final long serialVersionUID = -2894127198803998101L;

	public static CustomsPassageStep[] customsPassageSteps() {
		return new CustomsPassageStep[] { new CustomsPassageStep(false), new CustomsPassageStep(true) };
	}

	private BigInteger id;
	private boolean isOpen;
	private boolean isEntry;

	private CustomsPassageStep[] passageSteps;

	public CustomsPassage() {
	}

	public CustomsPassage(BigInteger id, boolean isOpen, boolean isEntry, CustomsPassageStep[] passageSteps) {
		super();
		this.id = id;
		this.isOpen = isOpen;
		this.isEntry = isEntry;
		this.passageSteps = passageSteps;
	}

	public static CustomsPassage getCustomsEntry(BigInteger id, boolean isOpen) {
		return new CustomsPassage(id, isOpen, true, customsPassageSteps());
	}

	public static CustomsPassage getCustomsExit(BigInteger id, boolean isOpen) {
		return new CustomsPassage(id, isOpen, false, customsPassageSteps());
	}

	public BigInteger getId() {
		return id;
	}

	public CustomsPassageStep[] getPassageSteps() {
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

	public void setPassageSteps(CustomsPassageStep[] passageSteps) {
		this.passageSteps = passageSteps;
	}
}
