package com.file.uploadingAndDownloading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileMetadata {

    private MultipartFile file;
    private String description;
}
