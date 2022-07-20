
package mdp.register.terminals.client;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateTerminalDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateTerminalDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="entryPassageCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="exitPassageCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
@XmlType(name = "CreateTerminalDto", namespace = "http://dtos.terminals.register.mdp", propOrder = {
    "entryPassageCount",
    "exitPassageCount",
    "terminalName"
})
public class CreateTerminalDto {

    protected int entryPassageCount;
    protected int exitPassageCount;
    @XmlElement(required = true, nillable = true)
    protected String terminalName;

    /**
     * Gets the value of the entryPassageCount property.
     * 
     */
    public int getEntryPassageCount() {
        return entryPassageCount;
    }

    /**
     * Sets the value of the entryPassageCount property.
     * 
     */
    public void setEntryPassageCount(int value) {
        this.entryPassageCount = value;
    }

    /**
     * Gets the value of the exitPassageCount property.
     * 
     */
    public int getExitPassageCount() {
        return exitPassageCount;
    }

    /**
     * Sets the value of the exitPassageCount property.
     * 
     */
    public void setExitPassageCount(int value) {
        this.exitPassageCount = value;
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
