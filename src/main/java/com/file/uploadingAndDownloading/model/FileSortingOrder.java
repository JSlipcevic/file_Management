package com.file.uploadingAndDownloading.model;

public enum FileSortingOrder {
    ASCENDING("asc"),
    DESCENDING("desc");

    private final String order;

    FileSortingOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
