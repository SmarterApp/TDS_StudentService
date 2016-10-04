/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.student.legacy.rtspackage.student.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accommodation"
})
public class Accommodations implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Accommodation", required = true)
    protected List<Accommodation> accommodation;

    /**
     * Gets the value of the accommodation property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot.
     * Therefore any modification you make to the returned list will be present
     * inside the JAXB object. Except in special circumstances, avoid using the
     * <CODE>set</CODE> method for the accommodation property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <p>
     * <pre>
     * getAccommodation ().add (newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Accommodation }
     */
    public List<Accommodation> getAccommodation() {
        if (accommodation == null) {
            accommodation = new ArrayList<Accommodation>();
        }
        return this.accommodation;
    }

    /**
     * Used during Spring object instantiation for load test scenarios. Except in
     * special circumstances, you should not set the entire list.
     *
     * @param accommodationList
     */
    public void setAccommodation(List<Accommodation> accommodationList) {
        accommodation = accommodationList;
    }
}
