
package mdp.register.terminals.client;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="searchTerminalReturn" type="{http://dtos.terminals.register.mdp}GetTerminalDto"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "searchTerminalReturn"
})
@XmlRootElement(name = "searchTerminalResponse")
public class SearchTerminalResponse {

    @XmlElement(required = true)
    protected GetTerminalDto searchTerminalReturn;

    /**
     * Gets the value of the searchTerminalReturn property.
     * 
     * @return
     *     possible object is
     *     {@link GetTerminalDto }
     *     
     */
    public GetTerminalDto getSearchTerminalReturn() {
        return searchTerminalReturn;
    }

    /**
     * Sets the value of the searchTerminalReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetTerminalDto }
     *     
     */
    public void setSearchTerminalReturn(GetTerminalDto value) {
        this.searchTerminalReturn = value;
    }

}
