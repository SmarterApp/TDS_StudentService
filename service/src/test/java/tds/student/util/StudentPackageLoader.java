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

package tds.student.util;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

import tds.dll.common.rtspackage.student.StudentPackageWriter;

public class StudentPackageLoader {
    public static byte[] getStudentPackageBytesFromClassPath(final Class clazz, final String resourceName) {
        try (InputStream inputStream = clazz.getClassLoader().getResourceAsStream(resourceName)) {
            String studentPackage = IOUtils.toString(inputStream);
            StudentPackageWriter writer = new StudentPackageWriter();
            writer.writeObject(studentPackage);
            return IOUtils.toByteArray(writer.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to get student package %s", resourceName), e);
        }
    }
}
