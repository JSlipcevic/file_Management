package com.file.uploadingAndDownloading.service;

import com.file.uploadingAndDownloading.configuration.FileConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileStorageService {

    private final FileConfig fileConfig;

    public FileStorageService(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    public void storeFile(MultipartFile file, String fileName) {
        if (file.isEmpty()) {
            return;
        }

        String separated = file.getContentType().split("/", 2)[1];
        String fileNameWithContentType = fileName + "." + separated;

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileConfig.getUploadDir() + fileNameWithContentType);
            Files.write(path, bytes);
            log.info("File is successfully uploaded " + fileNameWithContentType);
        } catch (Exception e) {
            log.error("Failed to upload " + fileNameWithContentType + " => " + e.getMessage());
        }
    }
}
