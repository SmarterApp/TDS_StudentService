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
    "studentGroupName"
})
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "StudentGroupName")
    protected List<String> studentGroupName;

    /**
     * Gets the value of the studentGroupName property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the studentGroupName property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <p>
     * <pre>
     * getStudentGroupName ().add (newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     */
    public List<String> getStudentGroupName() {
        if (studentGroupName == null) {
            studentGroupName = new ArrayList<String>();
        }
        return this.studentGroupName;
    }

}
