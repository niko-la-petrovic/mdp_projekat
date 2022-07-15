package mdp.models;

import java.math.BigInteger;

public class CustomsExit extends CustomsPassage {
	public CustomsExit(BigInteger id, boolean isOpen) {
		super(id, isOpen, false);
	}
}
