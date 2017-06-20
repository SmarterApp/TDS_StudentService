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

package tds.student.model;

public class RtsStudentInfo {
    private long id;
    private String loginSSID;
    private String stateCode;
    private String clientName;
    private byte[] studentPackage;

    public RtsStudentInfo(final long id,
                          final String loginSSID,
                          final String stateCode,
                          final String clientName,
                          final byte[] studentPackage) {
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
