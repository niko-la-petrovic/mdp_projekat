/**
 * GetCustomsPassageDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.register.terminals.client;

public class GetCustomsPassageDto  implements java.io.Serializable {
    private boolean entry;

    private java.math.BigInteger id;

    private boolean open;

    private mdp.register.terminals.client.GetCustomsPassageStepDto[] passageSteps;

    public GetCustomsPassageDto() {
    }

    public GetCustomsPassageDto(
           boolean entry,
           java.math.BigInteger id,
           boolean open,
           mdp.register.terminals.client.GetCustomsPassageStepDto[] passageSteps) {
           this.entry = entry;
           this.id = id;
           this.open = open;
           this.passageSteps = passageSteps;
    }


    /**
     * Gets the entry value for this GetCustomsPassageDto.
     * 
     * @return entry
     */
    public boolean isEntry() {
        return entry;
    }


    /**
     * Sets the entry value for this GetCustomsPassageDto.
     * 
     * @param entry
     */
    public void setEntry(boolean entry) {
        this.entry = entry;
    }


    /**
     * Gets the id value for this GetCustomsPassageDto.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this GetCustomsPassageDto.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the open value for this GetCustomsPassageDto.
     * 
     * @return open
     */
    public boolean isOpen() {
        return open;
    }


    /**
     * Sets the open value for this GetCustomsPassageDto.
     * 
     * @param open
     */
    public void setOpen(boolean open) {
        this.open = open;
    }


    /**
     * Gets the passageSteps value for this GetCustomsPassageDto.
     * 
     * @return passageSteps
     */
    public mdp.register.terminals.client.GetCustomsPassageStepDto[] getPassageSteps() {
        return passageSteps;
    }


    /**
     * Sets the passageSteps value for this GetCustomsPassageDto.
     * 
     * @param passageSteps
     */
    public void setPassageSteps(mdp.register.terminals.client.GetCustomsPassageStepDto[] passageSteps) {
        this.passageSteps = passageSteps;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCustomsPassageDto)) return false;
        GetCustomsPassageDto other = (GetCustomsPassageDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.entry == other.isEntry() &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            this.open == other.isOpen() &&
            ((this.passageSteps==null && other.getPassageSteps()==null) || 
             (this.passageSteps!=null &&
              java.util.Arrays.equals(this.passageSteps, other.getPassageSteps())));
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
        _hashCode += (isEntry() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        _hashCode += (isOpen() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPassageSteps() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPassageSteps());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPassageSteps(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCustomsPassageDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "entry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("open");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "open"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passageSteps");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "passageSteps"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageStepDto"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://terminals.register.mdp", "item"));
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
