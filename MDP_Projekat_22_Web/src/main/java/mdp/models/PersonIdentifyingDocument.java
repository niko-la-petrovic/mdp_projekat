package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

public class PersonIdentifyingDocument implements Serializable {
	private static final long serialVersionUID = 2059933721222454814L;

	BigInteger personId;
	BigInteger documentId;
	LocalDate issuedDate;
	LocalDate expiryDate;

}
