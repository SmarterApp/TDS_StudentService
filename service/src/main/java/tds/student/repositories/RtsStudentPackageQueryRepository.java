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

package tds.student.repositories;

import java.util.Optional;

import tds.student.model.RtsStudentInfo;

/**
 * Handles the data access interactions with the Student Package (r_studentpackage table).
 */
public interface RtsStudentPackageQueryRepository {
    /**
     * Finds the Student package
     *
     * @param clientName client name for the student
     * @param studentId  student id for the package
     * @return byte array containing the serialized gzipped data if found otherwise empty
     */
    Optional<byte[]> findRtsStudentPackage(final String clientName, final long studentId);

    /**
     * Finds the {@link tds.student.model.RtsStudentInfo} for the client name and id
     *
     * @param clientName client name for the student
     * @param studentId  the student id
     * @return {@link tds.student.model.RtsStudentInfo}
     */
    Optional<RtsStudentInfo> findStudentInfo(final String clientName, final long studentId);
}
