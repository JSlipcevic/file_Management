package com.file.uploadingAndDownloading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class File {
    private String fileName;
    private String description;
    private String documentType;
    private Long documentSize;
    private Instant uploadDate;
    private Instant ttl;
    private Instant updatedFileAt;
    private long downloadCounter;
}
