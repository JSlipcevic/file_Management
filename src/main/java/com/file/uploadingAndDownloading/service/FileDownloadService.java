package com.file.uploadingAndDownloading.service;

import com.file.uploadingAndDownloading.configuration.FileConfig;
import com.file.uploadingAndDownloading.entity.FileEntity;
import com.file.uploadingAndDownloading.exception.MyFileNotFoundException;
import com.file.uploadingAndDownloading.repository.FileRepository;
import com.file.uploadingAndDownloading.repository.UserRepository;
import com.file.uploadingAndDownloading.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FileDownloadService {

    private final FileConfig fileConfig;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public ResponseEntity<Resource> downloadFile(String fileName, Long userId) throws NoPermissionException {
       FileEntity file = fileRepository.findByFileName(fileName)
                .orElseThrow(() -> { throw new MyFileNotFoundException("File name " + fileName + " is not exists. Please check the file name and try again.");});

        checkUserDownloadPermission(file, userId);

        String filePath = fileConfig.getUploadDir() + FileUtil.fileNameWithExtension(file);
        FileSystemResource resource = new FileSystemResource(filePath);
        if (!resource.exists()) {
            throw new MyFileNotFoundException("File " + fileName + " is not exists as a resource.");
        }

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        ContentDisposition disposition = ContentDisposition
                .attachment()
                .filename(Objects.requireNonNull(resource.getFilename()))
                .build();
        headers.setContentDisposition(disposition);
        file.handleDownload();
        fileRepository.save(file);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    private void checkUserDownloadPermission(FileEntity file, Long userId) throws NoPermissionException {
        boolean canUserDownload = userRepository.checkUserPermissionToDownload(file.getId(), userId);
        if (!canUserDownload && !file.getUser().getId().equals(userId)) {
            throw new NoPermissionException("User with id " + userId + " don't have permission to download file!");
        }
    }
}
