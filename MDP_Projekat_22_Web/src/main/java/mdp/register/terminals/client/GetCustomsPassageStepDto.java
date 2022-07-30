/**
 * GetCustomsPassageStepDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.register.terminals.client;

public class GetCustomsPassageStepDto  implements java.io.Serializable {
    private boolean customsCheck;

    public GetCustomsPassageStepDto() {
    }

    public GetCustomsPassageStepDto(
           boolean customsCheck) {
           this.customsCheck = customsCheck;
    }


    /**
     * Gets the customsCheck value for this GetCustomsPassageStepDto.
     * 
     * @return customsCheck
     */
    public boolean isCustomsCheck() {
        return customsCheck;
    }


    /**
     * Sets the customsCheck value for this GetCustomsPassageStepDto.
     * 
     * @param customsCheck
     */
    public void setCustomsCheck(boolean customsCheck) {
        this.customsCheck = customsCheck;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCustomsPassageStepDto)) return false;
        GetCustomsPassageStepDto other = (GetCustomsPassageStepDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.customsCheck == other.isCustomsCheck();
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
        _hashCode += (isCustomsCheck() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCustomsPassageStepDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageStepDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsCheck");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "customsCheck"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
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
