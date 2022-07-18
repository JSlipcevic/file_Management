package com.file.uploadingAndDownloading.service;

import com.file.uploadingAndDownloading.dto.File;
import com.file.uploadingAndDownloading.entity.FileEntity;
import com.file.uploadingAndDownloading.entity.UserEntity;
import com.file.uploadingAndDownloading.exception.MyFileNotUploadException;
import com.file.uploadingAndDownloading.model.FileSortingOrder;
import com.file.uploadingAndDownloading.repository.FileRepository;
import com.file.uploadingAndDownloading.util.FileMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.file.uploadingAndDownloading.util.FileUtil.numOfFilesInDirectory;
import static com.file.uploadingAndDownloading.util.FileUtil.storedFileDays;

@Service
public class FileUploadService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;
    private final UserService userService;
    private final FilePermissionService filePermissionService;

    public FileUploadService(FileRepository fileRepository, FileStorageService fileStorageService, UserService userService,
                             FilePermissionService filePermissionService) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
        this.userService = userService;
        this.filePermissionService = filePermissionService;
    }

    public File createAndUploadFile(String description, MultipartFile file, Long userId, Set<Long> usersToShare) {
        UserEntity user = userService.getUser(userId);
        if (fileRepository.userFilesCount(userId) >= numOfFilesInDirectory(user)) {
            throw new MyFileNotUploadException("Could not upload the file. You are reached max number of files. Please delete some file to get enough space.");
        }
        try {
            FileEntity newFile = new FileEntity();
            newFile.setDescription(description);
            newFile.setDocumentSize(file.getSize());
            newFile.setDocumentType(file.getContentType());
            newFile.setTtl(storedFileDays(user));
            newFile.setUser(user);
            filePermissionService.setUserPermission(newFile, usersToShare);
            fileRepository.save(newFile);
            fileStorageService.storeFile(file, newFile.getFileName());
            return FileMapper.toDtoFile(newFile);
        } catch (Exception e) {
            throw new MyFileNotUploadException("Could not upload file " + file.getOriginalFilename() + ". " + e);
        }
    }

    public List<File> getSortedFileByDownloadWithPagination(Long userId, Integer limit, Integer page, FileSortingOrder sort) {

        if (limit == 0) {
            throw new IllegalArgumentException("Number of element on the page can't be 0! Please write how many rows you want to see per page.");
        }
            Sort.Direction wayOfSort = Sort.Direction.fromString(sort.getOrder());
            Pageable sortedByNumOfDownload = PageRequest.of(page, limit, Sort.by(wayOfSort, "downloadCounter"));
            List<FileEntity> allFiles = fileRepository.findAllFilesBySpecificUser(userId, sortedByNumOfDownload);

            return allFiles.stream().map(FileMapper::toDtoFile).collect(Collectors.toList());
    }
}
