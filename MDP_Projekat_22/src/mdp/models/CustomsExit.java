package mdp.models;

import java.math.BigInteger;

public class CustomsExit extends CustomsPassage {
	private static final long serialVersionUID = 6737490822521215250L;

	public CustomsExit(BigInteger id, boolean isOpen) {
		super(id, isOpen, false, customsPassageSteps());
	}
}
