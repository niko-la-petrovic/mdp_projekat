package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class CustomsTerminal implements Serializable {
	private static final long serialVersionUID = -1292723516097991850L;

	private CustomsPassage[] passages;
	private BigInteger id;
	private String name;

	public CustomsTerminal() {
	}

	public CustomsTerminal(CustomsPassage[] passages, BigInteger id, String name) {
		super();
		this.passages = passages;
		this.id = id;
		this.name = name;
	}

	public BigInteger getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public CustomsPassage[] getPassages() {
		return passages;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassages(CustomsPassage[] passages) {
		this.passages = passages;
	}

}
