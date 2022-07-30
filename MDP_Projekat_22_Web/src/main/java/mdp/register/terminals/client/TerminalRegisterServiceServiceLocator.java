/**
 * TerminalRegisterServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.register.terminals.client;

public class TerminalRegisterServiceServiceLocator extends org.apache.axis.client.Service implements mdp.register.terminals.client.TerminalRegisterServiceService {

    public TerminalRegisterServiceServiceLocator() {
    }


    public TerminalRegisterServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TerminalRegisterServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TerminalRegisterService
    private java.lang.String TerminalRegisterService_address = "http://localhost:8080/MDP_Projekat_22_Web/services/TerminalRegisterService";

    public java.lang.String getTerminalRegisterServiceAddress() {
        return TerminalRegisterService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TerminalRegisterServiceWSDDServiceName = "TerminalRegisterService";

    public java.lang.String getTerminalRegisterServiceWSDDServiceName() {
        return TerminalRegisterServiceWSDDServiceName;
    }

    public void setTerminalRegisterServiceWSDDServiceName(java.lang.String name) {
        TerminalRegisterServiceWSDDServiceName = name;
    }

    public mdp.register.terminals.client.TerminalRegisterService getTerminalRegisterService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TerminalRegisterService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTerminalRegisterService(endpoint);
    }

    public mdp.register.terminals.client.TerminalRegisterService getTerminalRegisterService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mdp.register.terminals.client.TerminalRegisterServiceSoapBindingStub _stub = new mdp.register.terminals.client.TerminalRegisterServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getTerminalRegisterServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTerminalRegisterServiceEndpointAddress(java.lang.String address) {
        TerminalRegisterService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mdp.register.terminals.client.TerminalRegisterService.class.isAssignableFrom(serviceEndpointInterface)) {
                mdp.register.terminals.client.TerminalRegisterServiceSoapBindingStub _stub = new mdp.register.terminals.client.TerminalRegisterServiceSoapBindingStub(new java.net.URL(TerminalRegisterService_address), this);
                _stub.setPortName(getTerminalRegisterServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TerminalRegisterService".equals(inputPortName)) {
            return getTerminalRegisterService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://terminals.register.mdp", "TerminalRegisterServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://terminals.register.mdp", "TerminalRegisterService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TerminalRegisterService".equals(portName)) {
            setTerminalRegisterServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
