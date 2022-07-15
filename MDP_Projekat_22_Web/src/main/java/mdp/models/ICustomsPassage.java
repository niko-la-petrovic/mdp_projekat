package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;

// TODO id of terminal
public interface ICustomsPassage extends Serializable {
	boolean isOpen();

	BigInteger getId();

	boolean isEntry();

	boolean isExit();

	ICustomsPassageStep[] getPassageSteps();
}