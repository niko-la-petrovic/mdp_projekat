/**
 * SearchTerminalDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.dtos;

public class SearchTerminalDto  implements java.io.Serializable {
    private boolean customsPassage;

    private java.math.BigInteger passageId;

    private java.lang.String terminalName;

    public SearchTerminalDto() {
    }

    public SearchTerminalDto(
           boolean customsPassage,
           java.math.BigInteger passageId,
           java.lang.String terminalName) {
           this.customsPassage = customsPassage;
           this.passageId = passageId;
           this.terminalName = terminalName;
    }


    /**
     * Gets the customsPassage value for this SearchTerminalDto.
     * 
     * @return customsPassage
     */
    public boolean isCustomsPassage() {
        return customsPassage;
    }


    /**
     * Sets the customsPassage value for this SearchTerminalDto.
     * 
     * @param customsPassage
     */
    public void setCustomsPassage(boolean customsPassage) {
        this.customsPassage = customsPassage;
    }


    /**
     * Gets the passageId value for this SearchTerminalDto.
     * 
     * @return passageId
     */
    public java.math.BigInteger getPassageId() {
        return passageId;
    }


    /**
     * Sets the passageId value for this SearchTerminalDto.
     * 
     * @param passageId
     */
    public void setPassageId(java.math.BigInteger passageId) {
        this.passageId = passageId;
    }


    /**
     * Gets the terminalName value for this SearchTerminalDto.
     * 
     * @return terminalName
     */
    public java.lang.String getTerminalName() {
        return terminalName;
    }


    /**
     * Sets the terminalName value for this SearchTerminalDto.
     * 
     * @param terminalName
     */
    public void setTerminalName(java.lang.String terminalName) {
        this.terminalName = terminalName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchTerminalDto)) return false;
        SearchTerminalDto other = (SearchTerminalDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.customsPassage == other.isCustomsPassage() &&
            ((this.passageId==null && other.getPassageId()==null) || 
             (this.passageId!=null &&
              this.passageId.equals(other.getPassageId()))) &&
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
        _hashCode += (isCustomsPassage() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPassageId() != null) {
            _hashCode += getPassageId().hashCode();
        }
        if (getTerminalName() != null) {
            _hashCode += getTerminalName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchTerminalDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dtos.mdp", "SearchTerminalDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsPassage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.mdp", "customsPassage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passageId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.mdp", "passageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.mdp", "terminalName"));
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
