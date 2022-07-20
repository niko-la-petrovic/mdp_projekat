
package mdp.register.terminals.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 4.0.0-M4
 * Generated source version: 3.0
 * 
 */
@WebServiceClient(name = "TerminalRegisterServiceService", targetNamespace = "http://terminals.register.mdp", wsdlLocation = "http://localhost:8080/MDP_Projekat_22_Web/services/TerminalRegisterService?wsdl")
public class TerminalRegisterServiceService
    extends Service
{

    private final static URL TERMINALREGISTERSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException TERMINALREGISTERSERVICESERVICE_EXCEPTION;
    private final static QName TERMINALREGISTERSERVICESERVICE_QNAME = new QName("http://terminals.register.mdp", "TerminalRegisterServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/MDP_Projekat_22_Web/services/TerminalRegisterService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TERMINALREGISTERSERVICESERVICE_WSDL_LOCATION = url;
        TERMINALREGISTERSERVICESERVICE_EXCEPTION = e;
    }

    public TerminalRegisterServiceService() {
        super(__getWsdlLocation(), TERMINALREGISTERSERVICESERVICE_QNAME);
    }

    public TerminalRegisterServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), TERMINALREGISTERSERVICESERVICE_QNAME, features);
    }

    public TerminalRegisterServiceService(URL wsdlLocation) {
        super(wsdlLocation, TERMINALREGISTERSERVICESERVICE_QNAME);
    }

    public TerminalRegisterServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TERMINALREGISTERSERVICESERVICE_QNAME, features);
    }

    public TerminalRegisterServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TerminalRegisterServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns TerminalRegisterService
     */
    @WebEndpoint(name = "TerminalRegisterService")
    public TerminalRegisterService getTerminalRegisterService() {
        return super.getPort(new QName("http://terminals.register.mdp", "TerminalRegisterService"), TerminalRegisterService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link jakarta.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TerminalRegisterService
     */
    @WebEndpoint(name = "TerminalRegisterService")
    public TerminalRegisterService getTerminalRegisterService(WebServiceFeature... features) {
        return super.getPort(new QName("http://terminals.register.mdp", "TerminalRegisterService"), TerminalRegisterService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TERMINALREGISTERSERVICESERVICE_EXCEPTION!= null) {
            throw TERMINALREGISTERSERVICESERVICE_EXCEPTION;
        }
        return TERMINALREGISTERSERVICESERVICE_WSDL_LOCATION;
    }

}
