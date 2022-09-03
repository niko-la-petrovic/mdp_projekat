/**
 * CreateTerminalDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.register.terminals.dtos;

public class CreateTerminalDto  implements java.io.Serializable {
    private int entryPassageCount;

    private int exitPassageCount;

    private java.lang.String terminalName;

    public CreateTerminalDto() {
    }

    public CreateTerminalDto(
           int entryPassageCount,
           int exitPassageCount,
           java.lang.String terminalName) {
           this.entryPassageCount = entryPassageCount;
           this.exitPassageCount = exitPassageCount;
           this.terminalName = terminalName;
    }


    /**
     * Gets the entryPassageCount value for this CreateTerminalDto.
     * 
     * @return entryPassageCount
     */
    public int getEntryPassageCount() {
        return entryPassageCount;
    }


    /**
     * Sets the entryPassageCount value for this CreateTerminalDto.
     * 
     * @param entryPassageCount
     */
    public void setEntryPassageCount(int entryPassageCount) {
        this.entryPassageCount = entryPassageCount;
    }


    /**
     * Gets the exitPassageCount value for this CreateTerminalDto.
     * 
     * @return exitPassageCount
     */
    public int getExitPassageCount() {
        return exitPassageCount;
    }


    /**
     * Sets the exitPassageCount value for this CreateTerminalDto.
     * 
     * @param exitPassageCount
     */
    public void setExitPassageCount(int exitPassageCount) {
        this.exitPassageCount = exitPassageCount;
    }


    /**
     * Gets the terminalName value for this CreateTerminalDto.
     * 
     * @return terminalName
     */
    public java.lang.String getTerminalName() {
        return terminalName;
    }


    /**
     * Sets the terminalName value for this CreateTerminalDto.
     * 
     * @param terminalName
     */
    public void setTerminalName(java.lang.String terminalName) {
        this.terminalName = terminalName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateTerminalDto)) return false;
        CreateTerminalDto other = (CreateTerminalDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.entryPassageCount == other.getEntryPassageCount() &&
            this.exitPassageCount == other.getExitPassageCount() &&
            ((this.terminalName==null && other.getTerminalName()==null) || 
             (this.terminalName!=null &&
              this.terminalName.equals(other.getTerminalName())));
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
        if (getTerminalName() != null) {
            _hashCode += getTerminalName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateTerminalDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "CreateTerminalDto"));
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
        elemField.setFieldName("terminalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "terminalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
