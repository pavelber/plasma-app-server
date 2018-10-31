//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.31 at 09:49:45 AM VET 
//


package response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}levNum"/>
 *         &lt;element ref="{}levConfig"/>
 *         &lt;element ref="{}g"/>
 *         &lt;element ref="{}levEnergy"/>
 *         &lt;element ref="{}exJump" maxOccurs="unbounded"/>
 *         &lt;element ref="{}ciJump" maxOccurs="unbounded"/>
 *         &lt;element ref="{}piJump" maxOccurs="unbounded"/>
 *         &lt;element ref="{}rtJump" maxOccurs="unbounded"/>
 *         &lt;element ref="{}aiJump" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="levId" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "levNum",
    "levConfig",
    "g",
    "levEnergy",
    "exJump",
    "ciJump",
    "piJump",
    "rtJump",
    "aiJump"
})
@XmlRootElement(name = "level")
public class Level {

    protected int levNum;
    @XmlElement(required = true)
    protected String levConfig;
    @XmlSchemaType(name = "unsignedByte")
    protected short g;
    protected double levEnergy;
    @XmlElement(required = true)
    protected List<ExJump> exJump;
    @XmlElement(required = true)
    protected List<CiJump> ciJump;
    @XmlElement(required = true)
    protected List<PiJump> piJump;
    @XmlElement(required = true)
    protected List<RtJump> rtJump;
    @XmlElement(required = true)
    protected List<AiJump> aiJump;
    @XmlAttribute(required = true)
    protected int levId;

    /**
     * Gets the value of the levNum property.
     * 
     */
    public int getLevNum() {
        return levNum;
    }

    /**
     * Sets the value of the levNum property.
     * 
     */
    public void setLevNum(int value) {
        this.levNum = value;
    }

    /**
     * Gets the value of the levConfig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevConfig() {
        return levConfig;
    }

    /**
     * Sets the value of the levConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevConfig(String value) {
        this.levConfig = value;
    }

    /**
     * Gets the value of the g property.
     * 
     */
    public short getG() {
        return g;
    }

    /**
     * Sets the value of the g property.
     * 
     */
    public void setG(short value) {
        this.g = value;
    }

    /**
     * Gets the value of the levEnergy property.
     * 
     */
    public double getLevEnergy() {
        return levEnergy;
    }

    /**
     * Sets the value of the levEnergy property.
     * 
     */
    public void setLevEnergy(double value) {
        this.levEnergy = value;
    }

    /**
     * Gets the value of the exJump property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exJump property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExJump().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExJump }
     * 
     * 
     */
    public List<ExJump> getExJump() {
        if (exJump == null) {
            exJump = new ArrayList<ExJump>();
        }
        return this.exJump;
    }

    /**
     * Gets the value of the ciJump property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ciJump property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCiJump().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CiJump }
     * 
     * 
     */
    public List<CiJump> getCiJump() {
        if (ciJump == null) {
            ciJump = new ArrayList<CiJump>();
        }
        return this.ciJump;
    }

    /**
     * Gets the value of the piJump property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the piJump property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPiJump().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PiJump }
     * 
     * 
     */
    public List<PiJump> getPiJump() {
        if (piJump == null) {
            piJump = new ArrayList<PiJump>();
        }
        return this.piJump;
    }

    /**
     * Gets the value of the rtJump property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rtJump property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRtJump().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RtJump }
     * 
     * 
     */
    public List<RtJump> getRtJump() {
        if (rtJump == null) {
            rtJump = new ArrayList<RtJump>();
        }
        return this.rtJump;
    }

    /**
     * Gets the value of the aiJump property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aiJump property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAiJump().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AiJump }
     * 
     * 
     */
    public List<AiJump> getAiJump() {
        if (aiJump == null) {
            aiJump = new ArrayList<AiJump>();
        }
        return this.aiJump;
    }

    /**
     * Gets the value of the levId property.
     * 
     */
    public int getLevId() {
        return levId;
    }

    /**
     * Sets the value of the levId property.
     * 
     */
    public void setLevId(int value) {
        this.levId = value;
    }

}
