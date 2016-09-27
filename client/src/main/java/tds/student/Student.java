package tds.student;

/**
 * Represents a student taking an exam
 */
public class Student {
    private final long id;
    private final String loginSSID;
    private final String stateCode;
    private final String clientName;

    /**
     * @param numeric           id for the student
     * @param loginSSID external SSID in the ART system
     * @param stateCode         state code for the student
     * @param clientName        client name associated with the student
     */
    public Student(long id, String loginSSID, String stateCode, String clientName) {
        this.id = id;
        this.loginSSID = loginSSID;
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
}
