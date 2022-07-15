package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

// TODO CRUD
public class CustomsTerminal implements Serializable {
	private static final long serialVersionUID = -1292723516097991850L;

	private List<CustomsEntry> entries;
	private List<CustomsExit> exits;
	private BigInteger id;
	private String name;

	public CustomsTerminal(List<CustomsEntry> entries, List<CustomsExit> exits, BigInteger id, String name) {
		super();
		this.entries = entries;
		this.exits = exits;
		this.id = id;
		this.name = name;
	}

	public List<CustomsEntry> getEntries() {
		return entries;
	}

	public List<CustomsExit> getExits() {
		return exits;
	}

	public BigInteger getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setEntries(
		List<CustomsEntry> entries) {
		this.entries = entries;
	}

	public void setExits(
		List<CustomsExit> exits) {
		this.exits = exits;
	}

	public void setId(
		BigInteger id) {
		this.id = id;
	}

	public void setName(
		String name) {
		this.name = name;
	}

}
