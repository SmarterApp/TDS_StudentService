package tds.student;

public class RtsAttribute {
    private String value;
    private String name;

    /**
     * @param name  name of the attribute
     * @param value value for the attribute
     */
    public RtsAttribute(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * For frameworks
     */
    private RtsAttribute() {
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
