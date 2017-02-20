package tds.student;

import java.util.List;

/**
 * Represents a student taking an exam
 */
public class Student {
    private long id;
    private String loginSSID;
    private String stateCode;
    private String clientName;
    private tds.dll.common.rtspackage.student.data.Student studentPackage;
    private List<RtsStudentPackageAttribute> attributes;
    private List<RtsStudentPackageRelationship> relationships;

    private Student(final Builder builder) {
        this.id = builder.id;
        this.loginSSID = builder.loginSSID;
        this.stateCode = builder.stateCode;
        this.clientName = builder.clientName;
        this.studentPackage = builder.studentPackage;
        this.attributes = builder.attributes;
        this.relationships = builder.relationships;
    }

    /**
     * Default constructor for frameworks
     */
    private Student() {
    }

    /**
     * @return numeric id for the student
     */
    public long getId() {
        return id;
    }

    /**
     * @return the external SSID in the ART system
     */
    public String getLoginSSID() {
        return loginSSID;
    }

    /**
     * @return state code for the student
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @return client name associated with the student
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the student package data from ART
     */
    public tds.dll.common.rtspackage.student.data.Student getStudentPackage() {
        return studentPackage;
    }

    /**
     * @return the attributes for a student
     */
    public List<RtsStudentPackageAttribute> getAttributes() {
        return attributes;
    }

    /**
     * @return the relationships for the student
     */
    public List<RtsStudentPackageRelationship> getRelationships() {
        return relationships;
    }

    public static final class Builder {
        private long id;
        private String loginSSID;
        private String stateCode;
        private String clientName;
        private tds.dll.common.rtspackage.student.data.Student studentPackage;
        private List<RtsStudentPackageAttribute> attributes;
        private List<RtsStudentPackageRelationship> relationships;

        public Builder(final long id, final String clientName) {
            this.id = id;
            this.clientName = clientName;
        }

        public Builder withLoginSSID(final String loginSSID) {
            this.loginSSID = loginSSID;
            return this;
        }

        public Builder withStateCode(final String stateCode) {
            this.stateCode = stateCode;
            return this;
        }

        public Builder withStudentPackage(final tds.dll.common.rtspackage.student.data.Student studentPackage) {
            this.studentPackage = studentPackage;
            return this;
        }

        public Builder withAttributes(final List<RtsStudentPackageAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder withRelationships(final List<RtsStudentPackageRelationship> relationships) {
            this.relationships = relationships;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
