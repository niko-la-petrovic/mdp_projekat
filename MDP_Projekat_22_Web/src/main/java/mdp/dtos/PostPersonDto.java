package mdp.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

public class PostPersonDto implements Serializable {
	private static final long serialVersionUID = -5470396871233067737L;

	private BigInteger personId;
	private String name;
	private String lastName;
	private String ssn;
	private String birthDate;
	private boolean isMale;

	public PostPersonDto() {
	}

	public PostPersonDto(BigInteger personId, String name, String lastName, String ssn, String birthDate,
			boolean isMale) {
		super();
		this.personId = personId;
		this.name = name;
		this.lastName = lastName;
		this.ssn = ssn;
		this.birthDate = birthDate;
		this.isMale = isMale;
	}

	public String getBirthDate() {
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

	public boolean getIsMale() {
		return isMale;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setIsMale(boolean isMale) {
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
