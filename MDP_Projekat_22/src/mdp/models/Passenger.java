package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;

public class Passenger implements Serializable {
	private static final long serialVersionUID = 8351705876687017161L;
	private BigInteger id;

	public Passenger() {
		super();
	}

	public Passenger(BigInteger id) {
		super();
		this.id = id;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(
		BigInteger id) {
		this.id = id;
	}

}
