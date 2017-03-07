package tds.student;

/**
 * Represents an attribute in the rts student package
 */
public class RtsStudentPackageAttribute {
    public static final String EXTERNAL_ID = "ExternalID";
    public static final String ENTITY_NAME = "--ENTITYNAME--";
    public static final String ACCOMMODATIONS = "--ACCOMMODATIONS--";

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
