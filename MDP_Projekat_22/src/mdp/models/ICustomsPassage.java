package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

// TODO id of terminal
public interface ICustomsPassage extends Serializable{
	boolean isOpen();

	BigInteger getId();

	boolean isEntry();

	boolean isExit();

	List<ICustomsPassageStep> getPassageSteps();
}
