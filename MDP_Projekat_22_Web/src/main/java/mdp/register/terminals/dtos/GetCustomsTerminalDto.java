package mdp.register.terminals.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import mdp.models.CustomsPassage;

public class GetCustomsTerminalDto implements Serializable {
	private static final long serialVersionUID = 9089437379388391421L;

	private BigInteger id;
	private String name;
	private GetCustomsPassageDto[] entries;
	private GetCustomsPassageDto[] exits;

	public GetCustomsTerminalDto() {
	}

	public GetCustomsTerminalDto(BigInteger id, String name, GetCustomsPassageDto[] entries,
			GetCustomsPassageDto[] exits) {
		super();
		this.id = id;
		this.name = name;
		this.entries = entries;
		this.exits = exits;
	}

	public GetCustomsPassageDto[] getEntries() {
		return entries;
	}

	public GetCustomsPassageDto[] getExits() {
		return exits;
	}

	public BigInteger getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setEntries(GetCustomsPassageDto[] entries) {
		this.entries = entries;
	}

	public void setExits(GetCustomsPassageDto[] exits) {
		this.exits = exits;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
