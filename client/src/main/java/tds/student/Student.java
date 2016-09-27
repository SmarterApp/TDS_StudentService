package tds.student;

/**
 * Represents a student taking an exam
 */
public class Student {
    private final long id;
    private final String loginStudentSSID;
    private final String stateCode;
    private final String clientName;

    /**
     * @param numeric           id for the student
     * @param loginStudentSSID external SSID in the ART system
     * @param stateCode         state code for the student
     * @param clientName        client name associated with the student
     */
    public Student(long id, String loginStudentSSID, String stateCode, String clientName) {
        this.id = id;
        this.loginStudentSSID = loginStudentSSID;
        this.stateCode = stateCode;
        this.clientName = clientName;
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
    public String getLoginStudentSSID() {
        return loginStudentSSID;
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
}
