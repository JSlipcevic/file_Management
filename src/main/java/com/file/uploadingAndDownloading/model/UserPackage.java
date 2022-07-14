package com.file.uploadingAndDownloading.model;

public enum UserPackage {

    BASIC("Basic", 10, 10),
    MEDIUM("Medium", 30, 25),
    ADVANCED("Advanced", 100, 50);

    private final String name;
    private final int days;
    private final int numOfFiles;

    UserPackage(String name, int days, int numOfFiles) {
        this.name = name;
        this.days = days;
        this.numOfFiles = numOfFiles;
    }

    public int getDays() {
        return days;
    }

    public int getNumOfFiles() {
        return numOfFiles;
    }

    public String getName() {
        return name;
    }
}
