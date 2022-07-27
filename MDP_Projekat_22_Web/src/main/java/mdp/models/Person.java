package mdp.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

public class Person implements Serializable {
	private static final long serialVersionUID = -1316550216335835005L;

	private BigInteger personId;
	private String name;
	private String lastName;
	private String ssn;
	private LocalDate birthDate;
	private boolean isMale;

	public Person(BigInteger personId, String name, String lastName, String ssn, LocalDate birthDate, boolean isMale) {
		super();
		this.personId = personId;
		this.name = name;
		this.lastName = lastName;
		this.ssn = ssn;
		this.birthDate = birthDate;
		this.isMale = isMale;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return name;
	}

	public BigInteger getPersonId() {
		return personId;
	}

	public String getSsn() {
		return ssn;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersonId(BigInteger personId) {
		this.personId = personId;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

}
