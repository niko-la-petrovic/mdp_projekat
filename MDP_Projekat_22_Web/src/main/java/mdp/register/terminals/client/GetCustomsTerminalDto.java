/**
 * GetCustomsTerminalDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.register.terminals.client;

public class GetCustomsTerminalDto  implements java.io.Serializable {
    private mdp.register.terminals.client.GetCustomsPassageDto[] entries;

    private mdp.register.terminals.client.GetCustomsPassageDto[] exits;

    private java.math.BigInteger id;

    private java.lang.String name;

    public GetCustomsTerminalDto() {
    }

    public GetCustomsTerminalDto(
           mdp.register.terminals.client.GetCustomsPassageDto[] entries,
           mdp.register.terminals.client.GetCustomsPassageDto[] exits,
           java.math.BigInteger id,
           java.lang.String name) {
           this.entries = entries;
           this.exits = exits;
           this.id = id;
           this.name = name;
    }


    /**
     * Gets the entries value for this GetCustomsTerminalDto.
     * 
     * @return entries
     */
    public mdp.register.terminals.client.GetCustomsPassageDto[] getEntries() {
        return entries;
    }


    /**
     * Sets the entries value for this GetCustomsTerminalDto.
     * 
     * @param entries
     */
    public void setEntries(mdp.register.terminals.client.GetCustomsPassageDto[] entries) {
        this.entries = entries;
    }


    /**
     * Gets the exits value for this GetCustomsTerminalDto.
     * 
     * @return exits
     */
    public mdp.register.terminals.client.GetCustomsPassageDto[] getExits() {
        return exits;
    }


    /**
     * Sets the exits value for this GetCustomsTerminalDto.
     * 
     * @param exits
     */
    public void setExits(mdp.register.terminals.client.GetCustomsPassageDto[] exits) {
        this.exits = exits;
    }


    /**
     * Gets the id value for this GetCustomsTerminalDto.
     * 
     * @return id
     */
    public java.math.BigInteger getId() {
        return id;
    }


    /**
     * Sets the id value for this GetCustomsTerminalDto.
     * 
     * @param id
     */
    public void setId(java.math.BigInteger id) {
        this.id = id;
    }


    /**
     * Gets the name value for this GetCustomsTerminalDto.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this GetCustomsTerminalDto.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCustomsTerminalDto)) return false;
        GetCustomsTerminalDto other = (GetCustomsTerminalDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.entries==null && other.getEntries()==null) || 
             (this.entries!=null &&
              java.util.Arrays.equals(this.entries, other.getEntries()))) &&
            ((this.exits==null && other.getExits()==null) || 
             (this.exits!=null &&
              java.util.Arrays.equals(this.exits, other.getExits()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName())));
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
        if (getEntries() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEntries());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEntries(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExits() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExits());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExits(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCustomsTerminalDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "entries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageDto"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://terminals.register.mdp", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "exits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageDto"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://terminals.register.mdp", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "name"));
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
