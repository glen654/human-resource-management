package lk.ijse.humanResourceManagement.controller;

import java.util.UUID;
public class EnrollmentIdGenerator {
    public static String generateEnrollmentId() {

        return UUID.randomUUID().toString().substring(0,20);
    }
    public static void main(String[] args) {
        String enrollmentId = generateEnrollmentId();
        System.out.println("Generated Enrollment ID: " + enrollmentId);
    }
}
