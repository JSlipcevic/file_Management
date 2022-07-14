package com.file.uploadingAndDownloading.dto;

import com.file.uploadingAndDownloading.model.UserPackage;
import com.file.uploadingAndDownloading.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailedUser {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String country;
    private String city;
    private Gender gender;
    private UserPackage userPackage;

    public DetailedUser () {}
}
