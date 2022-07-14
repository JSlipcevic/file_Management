package com.file.uploadingAndDownloading.util;

import com.file.uploadingAndDownloading.entity.FileEntity;
import com.file.uploadingAndDownloading.entity.UserEntity;
import com.file.uploadingAndDownloading.model.UserPackage;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.Period;

@Slf4j
public class FileUtil {

    public static void deleteFileByNameFromDirectory(String directoryPath, String fileNameWithExtension) {
        java.io.File targetFile = new java.io.File(directoryPath + fileNameWithExtension);
        if (targetFile.delete()) {
            log.info(fileNameWithExtension + " is deleted from directory");
        }
    }

    public static String fileNameWithExtension(FileEntity file) {
        String fileExtension = file.getDocumentType().split("/", 2)[1];
        return file.getFileName() + "." + fileExtension;
    }

    public static Instant storedFileDays(UserEntity user) {
        if (user.getUserPackage().equals(UserPackage.BASIC.getName())) {
            return Instant.now().plus(Period.ofDays(UserPackage.BASIC.getDays()));
        }

        if (user.getUserPackage().equals(UserPackage.MEDIUM.getName())) {
            return Instant.now().plus(Period.ofDays(UserPackage.MEDIUM.getDays()));
        }

        if (user.getUserPackage().equals(UserPackage.ADVANCED.getName())) {
            return Instant.now().plus(Period.ofDays(UserPackage.ADVANCED.getDays()));
        }

        throw new IllegalArgumentException("It is necessary to choose some of the offered packages");
    }

    public static Integer numOfFilesInDirectory(UserEntity user) {
        if (user.getUserPackage().equals(UserPackage.BASIC.getName())) {
            return UserPackage.BASIC.getNumOfFiles();
        }

        if (user.getUserPackage().equals(UserPackage.MEDIUM.getName())) {
            return UserPackage.MEDIUM.getNumOfFiles();
        }

        if (user.getUserPackage().equals(UserPackage.ADVANCED.getName())) {
            return UserPackage.ADVANCED.getNumOfFiles();
        }

        throw new IllegalArgumentException("It is necessary to choose some of the offered packages");
    }
}
