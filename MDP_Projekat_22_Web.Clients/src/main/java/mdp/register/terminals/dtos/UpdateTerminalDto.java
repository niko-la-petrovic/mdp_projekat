/**
 * UpdateTerminalDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.register.terminals.dtos;

public class UpdateTerminalDto implements java.io.Serializable {
	private int entryPassageCount;

	private int exitPassageCount;

	private java.lang.String name;

	private java.math.BigInteger terminalId;

	public UpdateTerminalDto() {
	}

	public UpdateTerminalDto(int entryPassageCount, int exitPassageCount, java.lang.String name,
			java.math.BigInteger terminalId) {
		this.entryPassageCount = entryPassageCount;
		this.exitPassageCount = exitPassageCount;
		this.name = name;
		this.terminalId = terminalId;
	}

	/**
	 * Gets the entryPassageCount value for this UpdateTerminalDto.
	 * 
	 * @return entryPassageCount
	 */
	public int getEntryPassageCount() {
		return entryPassageCount;
	}

	/**
	 * Sets the entryPassageCount value for this UpdateTerminalDto.
	 * 
	 * @param entryPassageCount
	 */
	public void setEntryPassageCount(int entryPassageCount) {
		this.entryPassageCount = entryPassageCount;
	}

	/**
	 * Gets the exitPassageCount value for this UpdateTerminalDto.
	 * 
	 * @return exitPassageCount
	 */
	public int getExitPassageCount() {
		return exitPassageCount;
	}

	/**
	 * Sets the exitPassageCount value for this UpdateTerminalDto.
	 * 
	 * @param exitPassageCount
	 */
	public void setExitPassageCount(int exitPassageCount) {
		this.exitPassageCount = exitPassageCount;
	}

	/**
	 * Gets the name value for this UpdateTerminalDto.
	 * 
	 * @return name
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Sets the name value for this UpdateTerminalDto.
	 * 
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * Gets the terminalId value for this UpdateTerminalDto.
	 * 
	 * @return terminalId
	 */
	public java.math.BigInteger getTerminalId() {
		return terminalId;
	}

	/**
	 * Sets the terminalId value for this UpdateTerminalDto.
	 * 
	 * @param terminalId
	 */
	public void setTerminalId(java.math.BigInteger terminalId) {
		this.terminalId = terminalId;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof UpdateTerminalDto))
			return false;
		UpdateTerminalDto other = (UpdateTerminalDto) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && this.entryPassageCount == other.getEntryPassageCount()
				&& this.exitPassageCount == other.getExitPassageCount()
				&& ((this.name == null && other.getName() == null)
						|| (this.name != null && this.name.equals(other.getName())))
				&& ((this.terminalId == null && other.getTerminalId() == null)
						|| (this.terminalId != null && this.terminalId.equals(other.getTerminalId())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		_hashCode += getEntryPassageCount();
		_hashCode += getExitPassageCount();
		if (getName() != null) {
			_hashCode += getName().hashCode();
		}
		if (getTerminalId() != null) {
			_hashCode += getTerminalId().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			UpdateTerminalDto.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "UpdateTerminalDto"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("entryPassageCount");
		elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "entryPassageCount"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("exitPassageCount");
		elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "exitPassageCount"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("name");
		elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "name"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("terminalId");
		elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "terminalId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
