package tds.student;

/**
 * Represents an attribute in the rts student package
 */
public class RtsStudentPackageAttribute {
    private String value;
    private String name;

    /**
     * @param name  name of the attribute
     * @param value value for the attribute
     */
    public RtsStudentPackageAttribute(String name, String value) {
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
