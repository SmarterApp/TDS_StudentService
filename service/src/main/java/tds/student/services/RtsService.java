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

package tds.student.services;

import java.util.List;
import java.util.Optional;

import tds.student.RtsStudentPackageAttribute;
import tds.student.Student;

/**
 * Handles interactions with session rts packages
 */
public interface RtsService {
    /**
     * Retrieves the student package and finds the attribute
     *
     * @param clientName     the client name
     * @param studentId      the student id associated with the package
     * @param attributeNames the attribute names to be used for lookup within the package
     * @return list of {@link tds.student.RtsStudentPackageAttribute} otherwise empty
     */
    List<RtsStudentPackageAttribute> findRtsStudentPackageAttributes(final String clientName, final long studentId, final String[] attributeNames);

    /**
     * Retrieves a fully populated student
     *
     * @param clientName the client name associated with the student
     * @param studentId  the student id
     * @return the fully populated student
     */
    Optional<Student> findStudent(final String clientName, final long studentId);
}
