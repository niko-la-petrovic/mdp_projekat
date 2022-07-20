
package mdp.register.terminals.client;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="getSerializedCountReturn" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "getSerializedCountReturn"
})
@XmlRootElement(name = "getSerializedCountResponse")
public class GetSerializedCountResponse {

    protected int getSerializedCountReturn;

    /**
     * Gets the value of the getSerializedCountReturn property.
     * 
     */
    public int getGetSerializedCountReturn() {
        return getSerializedCountReturn;
    }

    /**
     * Sets the value of the getSerializedCountReturn property.
     * 
     */
    public void setGetSerializedCountReturn(int value) {
        this.getSerializedCountReturn = value;
    }

}
