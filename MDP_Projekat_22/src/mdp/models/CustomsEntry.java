package mdp.models;

import java.math.BigInteger;

public class CustomsEntry extends CustomsPassage {
	private static final long serialVersionUID = 4435588741547168207L;

	public CustomsEntry(BigInteger id, boolean isOpen) {
		super(id, isOpen, true, CustomsPassage.customsPassageSteps());
	}
}
