package tds.student;

/**
 * Represents a student taking an exam
 */
public class Student {
    private long id;
    private String studentId;
    private String stateCode;
    private String clientName;

    /**
     * @return numeric id for the student
     */
    public long getId() {
        return id;
    }

    /**
     * @param id numeric id for the student
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the external SSID in the ART system
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the external SSID in the ART system
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * @return state code for the student
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode state code for the student
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * @return client name associated with the student
     */
    public String getClientName() {
        return clientName;
    }

    /**
      * @param clientName client name associated with the student
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
