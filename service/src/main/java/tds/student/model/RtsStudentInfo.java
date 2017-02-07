package tds.student.model;

public class RtsStudentInfo {
    private long id;
    private String loginSSID;
    private String stateCode;
    private String clientName;
    private byte[] studentPackage;

    public RtsStudentInfo(long id, String loginSSID, String stateCode, String clientName, byte[] studentPackage) {
        this.id = id;
        this.loginSSID = loginSSID;
        this.stateCode = stateCode;
        this.clientName = clientName;
        this.studentPackage = studentPackage;
    }

    public long getId() {
        return id;
    }

    public String getLoginSSID() {
        return loginSSID;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getClientName() {
        return clientName;
    }

    public byte[] getStudentPackage() {
        return studentPackage;
    }
}
