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
