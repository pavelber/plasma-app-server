//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.31 at 09:49:45 AM VET 
//


package response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element ref="{}source"/>
 *         &lt;element ref="{}target"/>
 *         &lt;element ref="{}wavelength"/>
 *         &lt;element ref="{}einstein"/>
 *         &lt;element ref="{}oscStrength"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "source",
    "target",
    "wavelength",
    "einstein",
    "oscStrength"
})
@XmlRootElement(name = "rtJump")
public class RtJump {

    protected int source;
    protected int target;
    protected double wavelength;
    protected double einstein;
    protected double oscStrength;

    /**
     * Gets the value of the source property.
     * 
     */
    public int getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     */
    public void setSource(int value) {
        this.source = value;
    }

    /**
     * Gets the value of the target property.
     * 
     */
    public int getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     */
    public void setTarget(int value) {
        this.target = value;
    }

    /**
     * Gets the value of the wavelength property.
     * 
     */
    public double getWavelength() {
        return wavelength;
    }

    /**
     * Sets the value of the wavelength property.
     * 
     */
    public void setWavelength(double value) {
        this.wavelength = value;
    }

    /**
     * Gets the value of the einstein property.
     * 
     */
    public double getEinstein() {
        return einstein;
    }

    /**
     * Sets the value of the einstein property.
     * 
     */
    public void setEinstein(double value) {
        this.einstein = value;
    }

    /**
     * Gets the value of the oscStrength property.
     * 
     */
    public double getOscStrength() {
        return oscStrength;
    }

    /**
     * Sets the value of the oscStrength property.
     * 
     */
    public void setOscStrength(double value) {
        this.oscStrength = value;
    }

}