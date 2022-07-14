package com.file.uploadingAndDownloading.dto;

import com.file.uploadingAndDownloading.model.UserPackage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class User {
    private String userName;
    private String email;
    private String country;
    private String city;
    private String gender;
    private Instant createdAt;
    private Instant updatedAt;
    private String userPackage;
}
