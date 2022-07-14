package com.file.uploadingAndDownloading.model;

public enum Gender {

    MALE("M"),
    FEMALE("F");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
