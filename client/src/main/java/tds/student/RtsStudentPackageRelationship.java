package tds.student;

/**
 * Student package relationships
 */
public class RtsStudentPackageRelationship {
    private String type;
    private String value;
    private String entityKey;
    private String id;

    /**
     * @param type type of relationship
     * @param value relationship value
     * @param entityKey relationship entityKey
     */
    public RtsStudentPackageRelationship(String id, String type, String value, String entityKey) {
        this.type = type;
        this.value = value;
        this.entityKey = entityKey;
        this.id = id;
    }

    /**
     * Exists for frameworks
     */
    private RtsStudentPackageRelationship(){}

    /**
     * @return type of relationship
     */
    public String getType() {
        return type;
    }

    /**
     * @return relationship value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return relationship entityKey
     */
    public String getEntityKey() {
        return entityKey;
    }

    /**
     * @return the tds id for the relationship
     */
    public String getId() {
        return id;
    }
}
