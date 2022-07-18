package com.file.uploadingAndDownloading.api;

import com.file.uploadingAndDownloading.dto.File;
import com.file.uploadingAndDownloading.model.FileSortingOrder;
import com.file.uploadingAndDownloading.service.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NoPermissionException;
import java.util.HashSet;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class FileController {

    private final FileUploadService fileUploadService;
    private final FileDownloadService fileDownloadService;
    private final FileDeleteService fileDeleteService;
    private final FilePermissionService filePermissionService;
    private final FileService fileService;

    @PostMapping("/upload")
    public File uploadFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("description") String description,
                           @RequestParam("userId") Long userId,
                           @RequestParam("usersToShare") HashSet<Long> usersToShare) {
        return fileUploadService.createAndUploadFile(description, file, userId, usersToShare);
    }

    @GetMapping("/getFile/{fileId}")
    public File getFileData(@PathVariable("fileId") Long fileId) {
        return fileService.getFileDto(fileId);
    }

    @PutMapping("/newPermission/{fileId}")
    public void addPermissionToNewUsers(@PathVariable("fileId") Long fileId,
                                        @RequestParam("usersToShare") HashSet<Long> newUsersToShare) {
        filePermissionService.addPermissionToNewUsers(fileId, newUsersToShare);
    }

    @PutMapping("/recallPermissions/{fileId}")
    public void recallUserPermission(@PathVariable("fileId") Long fileId,
                                     @RequestParam("recallUsers") HashSet<Long> recallUsers) {
        filePermissionService.recallUsersPermission(fileId, recallUsers);
    }

    @PutMapping("/recallAllFileDownloadPermissions/{fileId}")
    public void recallAllPermissions(@PathVariable("fileId") Long fileId) {
        filePermissionService.recallAllPermissions(fileId);
    }

    @DeleteMapping("/delete/{fileName}")
    public void deleteFile(@PathVariable("fileName") String fileName) {
        fileDeleteService.deleteFile(fileName);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                                 @RequestParam("userId") Long userId) throws NoPermissionException {
        return fileDownloadService.downloadFile(fileName, userId);
    }

    @PatchMapping("/updateDescription/{fileId}")
    public File editDescription(@PathVariable("fileId") Long fileId, @RequestBody File newFileDescription) {
        return fileService.editFileDescription(fileId, newFileDescription);
    }

    @GetMapping("/all-files/{userId}")
    public List<File> getFilesByUser(@PathVariable("userId") Long userId,
                                     @RequestParam("limit") Integer limit,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("sort") FileSortingOrder sort) {
        return fileUploadService.getSortedFileByDownloadWithPagination(userId, limit, page, sort);
    }
}

