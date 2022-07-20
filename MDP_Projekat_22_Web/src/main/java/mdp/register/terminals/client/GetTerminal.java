
package mdp.register.terminals.client;

import java.math.BigInteger;
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
 *         &lt;element name="passageId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="isCustomsStep" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="terminalName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "passageId",
    "isCustomsStep",
    "terminalName"
})
@XmlRootElement(name = "getTerminal")
public class GetTerminal {

    @XmlElement(required = true)
    protected BigInteger passageId;
    protected boolean isCustomsStep;
    @XmlElement(required = true)
    protected String terminalName;

    /**
     * Gets the value of the passageId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPassageId() {
        return passageId;
    }

    /**
     * Sets the value of the passageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPassageId(BigInteger value) {
        this.passageId = value;
    }

    /**
     * Gets the value of the isCustomsStep property.
     * 
     */
    public boolean isIsCustomsStep() {
        return isCustomsStep;
    }

    /**
     * Sets the value of the isCustomsStep property.
     * 
     */
    public void setIsCustomsStep(boolean value) {
        this.isCustomsStep = value;
    }

    /**
     * Gets the value of the terminalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminalName() {
        return terminalName;
    }

    /**
     * Sets the value of the terminalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminalName(String value) {
        this.terminalName = value;
    }

}
