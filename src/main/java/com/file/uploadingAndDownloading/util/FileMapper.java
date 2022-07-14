package com.file.uploadingAndDownloading.util;

import com.file.uploadingAndDownloading.dto.File;
import com.file.uploadingAndDownloading.entity.FileEntity;

public final class FileMapper {
    public static File toDtoFile(FileEntity file) {
        return new File(file.getFileName(), file.getDescription(), file.getDocumentType(), file.getDocumentSize(),
               file.getUploadDate(), file.getTtl(), file.getUpdatedFileAt(), file.getDownloadCounter());
    }
}
