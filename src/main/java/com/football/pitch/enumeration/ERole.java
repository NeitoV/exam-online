package com.football.pitch.enumeration;

public enum ERole {
    ADMIN("Role_Admin"),
    CUSTOMER("Role_Customer"),
    MANAGER("Role_Manager");

    public static long roleAdmin = 1;
    public static long roleCustomer = 2;
    public static long roleManager = 3;

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
