
package mdp.register.terminals.client;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mdp.register.terminals.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Fault_QNAME = new QName("http://terminals.register.mdp", "fault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mdp.register.terminals.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteTerminal }
     * 
     */
    public DeleteTerminal createDeleteTerminal() {
        return new DeleteTerminal();
    }

    /**
     * Create an instance of {@link DeleteTerminalResponse }
     * 
     */
    public DeleteTerminalResponse createDeleteTerminalResponse() {
        return new DeleteTerminalResponse();
    }

    /**
     * Create an instance of {@link TerminalNotFoundException }
     * 
     */
    public TerminalNotFoundException createTerminalNotFoundException() {
        return new TerminalNotFoundException();
    }

    /**
     * Create an instance of {@link GetTerminal }
     * 
     */
    public GetTerminal createGetTerminal() {
        return new GetTerminal();
    }

    /**
     * Create an instance of {@link GetTerminalResponse }
     * 
     */
    public GetTerminalResponse createGetTerminalResponse() {
        return new GetTerminalResponse();
    }

    /**
     * Create an instance of {@link GetTerminalDto }
     * 
     */
    public GetTerminalDto createGetTerminalDto() {
        return new GetTerminalDto();
    }

    /**
     * Create an instance of {@link GetTerminals }
     * 
     */
    public GetTerminals createGetTerminals() {
        return new GetTerminals();
    }

    /**
     * Create an instance of {@link GetTerminalsResponse }
     * 
     */
    public GetTerminalsResponse createGetTerminalsResponse() {
        return new GetTerminalsResponse();
    }

    /**
     * Create an instance of {@link CreateTerminal }
     * 
     */
    public CreateTerminal createCreateTerminal() {
        return new CreateTerminal();
    }

    /**
     * Create an instance of {@link CreateTerminalDto }
     * 
     */
    public CreateTerminalDto createCreateTerminalDto() {
        return new CreateTerminalDto();
    }

    /**
     * Create an instance of {@link CreateTerminalResponse }
     * 
     */
    public CreateTerminalResponse createCreateTerminalResponse() {
        return new CreateTerminalResponse();
    }

    /**
     * Create an instance of {@link SearchTerminal }
     * 
     */
    public SearchTerminal createSearchTerminal() {
        return new SearchTerminal();
    }

    /**
     * Create an instance of {@link SearchTerminalDto }
     * 
     */
    public SearchTerminalDto createSearchTerminalDto() {
        return new SearchTerminalDto();
    }

    /**
     * Create an instance of {@link SearchTerminalResponse }
     * 
     */
    public SearchTerminalResponse createSearchTerminalResponse() {
        return new SearchTerminalResponse();
    }

    /**
     * Create an instance of {@link UpdateTerminal }
     * 
     */
    public UpdateTerminal createUpdateTerminal() {
        return new UpdateTerminal();
    }

    /**
     * Create an instance of {@link UpdateTerminalDto }
     * 
     */
    public UpdateTerminalDto createUpdateTerminalDto() {
        return new UpdateTerminalDto();
    }

    /**
     * Create an instance of {@link UpdateTerminalResponse }
     * 
     */
    public UpdateTerminalResponse createUpdateTerminalResponse() {
        return new UpdateTerminalResponse();
    }

    /**
     * Create an instance of {@link GetSerializedCount }
     * 
     */
    public GetSerializedCount createGetSerializedCount() {
        return new GetSerializedCount();
    }

    /**
     * Create an instance of {@link GetSerializedCountResponse }
     * 
     */
    public GetSerializedCountResponse createGetSerializedCountResponse() {
        return new GetSerializedCountResponse();
    }

    /**
     * Create an instance of {@link SetSerializedCount }
     * 
     */
    public SetSerializedCount createSetSerializedCount() {
        return new SetSerializedCount();
    }

    /**
     * Create an instance of {@link SetSerializedCountResponse }
     * 
     */
    public SetSerializedCountResponse createSetSerializedCountResponse() {
        return new SetSerializedCountResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TerminalNotFoundException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TerminalNotFoundException }{@code >}
     */
    @XmlElementDecl(namespace = "http://terminals.register.mdp", name = "fault")
    public JAXBElement<TerminalNotFoundException> createFault(TerminalNotFoundException value) {
        return new JAXBElement<TerminalNotFoundException>(_Fault_QNAME, TerminalNotFoundException.class, null, value);
    }

}
