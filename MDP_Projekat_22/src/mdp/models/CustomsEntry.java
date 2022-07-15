package mdp.models;

import java.math.BigInteger;

public class CustomsEntry extends CustomsPassage {
	public CustomsEntry(BigInteger id, boolean isOpen) {
		super(id, isOpen, true);
	}
}
