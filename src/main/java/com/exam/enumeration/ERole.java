package com.exam.enumeration;

public enum ERole {
    ADMIN("Role_Admin"),
    LECTURER("Role_Lecturer"),
    STUDENT("Role_Student");

    public static long roleAdmin = 1;
    public static long roleLecturer = 2;
    public static long roleStudent = 3;

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
