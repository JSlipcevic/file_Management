package com.file.uploadingAndDownloading.util;

import com.file.uploadingAndDownloading.dto.User;
import com.file.uploadingAndDownloading.entity.UserEntity;

public final class UserMapper {
    public static User toDtoUser(UserEntity user) {
        return new User(user.getUserName(), user.getEmail(), user.getCountry(), user.getCity(), user.getGender(), user.getCreatedAt(),
                user.getUpdatedAt(), user.getUserPackage());
    }
}
