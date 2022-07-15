package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;

// TODO CRUD
public class CustomsTerminal implements Serializable {
	private static final long serialVersionUID = -1292723516097991850L;

	private CustomsEntry[] entries;
	private CustomsExit[] exits;
	private BigInteger id;
	private String name;

	public CustomsTerminal() {
	}

	public CustomsTerminal(CustomsEntry[] entries, CustomsExit[] exits, BigInteger id, String name) {
		super();
		this.entries = entries;
		this.exits = exits;
		this.id = id;
		this.name = name;
	}

	public CustomsEntry[] getEntries() {
		return entries;
	}

	public CustomsExit[] getExits() {
		return exits;
	}

	public BigInteger getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setEntries(CustomsEntry[] entries) {
		this.entries = entries;
	}

	public void setExits(CustomsExit[] exits) {
		this.exits = exits;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
