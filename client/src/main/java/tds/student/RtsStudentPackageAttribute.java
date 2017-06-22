/***************************************************************************************************
 * Copyright 2017 Regents of the University of California. Licensed under the Educational
 * Community License, Version 2.0 (the “license”); you may not use this file except in
 * compliance with the License. You may obtain a copy of the license at
 *
 * https://opensource.org/licenses/ECL-2.0
 *
 * Unless required under applicable law or agreed to in writing, software distributed under the
 * License is distributed in an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for specific language governing permissions
 * and limitations under the license.
 **************************************************************************************************/

package tds.student;

/**
 * Represents an attribute in the rts student package
 */
public class RtsStudentPackageAttribute {
    public static final String EXTERNAL_ID = "ExternalID";
    public static final String BLOCKED_SUBJECT = "BLOCKEDSUBJECT";
    public static final String ENTITY_NAME = "--ENTITYNAME--";
    public static final String ACCOMMODATIONS = "--ACCOMMODATIONS--";
    public static final String ELIGIBLE_ASSESSMENTS = "--ELIGIBLETESTS--";

    private String value;
    private String name;

    /**
     * @param name  name of the attribute
     * @param value value for the attribute
     */
    public RtsStudentPackageAttribute(final String name, final String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * For frameworks
     */
    private RtsStudentPackageAttribute() {
    }

    /**
     * @return value for the attribute
     */
    public String getValue() {
        return value;
    }

    /**
     * @return name of the attribute
     */
    public String getName() {
        return name;
    }
}
