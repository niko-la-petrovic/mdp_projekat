package mdp.models;

import java.io.Serializable;

public interface ICustomsPassageStep extends Serializable {
	boolean isCustomsCheck();

	boolean isPoliceCheck();
}
