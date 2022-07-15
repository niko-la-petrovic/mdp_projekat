package mdp.models;

import java.math.BigInteger;

public interface ICustomsPassage {
	boolean isOpen();
	BigInteger getId();

	boolean isEntry();

	boolean isExit();
}
