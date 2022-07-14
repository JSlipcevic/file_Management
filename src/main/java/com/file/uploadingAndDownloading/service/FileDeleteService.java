package com.file.uploadingAndDownloading.service;

import com.file.uploadingAndDownloading.configuration.FileConfig;
import com.file.uploadingAndDownloading.entity.FileEntity;
import com.file.uploadingAndDownloading.exception.MyFileNotFoundException;
import com.file.uploadingAndDownloading.repository.FileRepository;
import com.file.uploadingAndDownloading.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class FileDeleteService {

    private final FileRepository fileRepository;
    private final FileConfig fileConfig;

    public void deleteFile(String fileName) {
        Optional<FileEntity> file = fileRepository.findByFileName(fileName);

        if (file.isEmpty()) {
            throw new MyFileNotFoundException("File name " + fileName + " is not exists. Please check the file name and try again.");
        }
        deleteFileFromDirectoryAndRepository(file.get());
    }

    public void deleteFileFromDirectoryAndRepository (FileEntity file){
        FileUtil.deleteFileByNameFromDirectory(fileConfig.getUploadDir(), FileUtil.fileNameWithExtension(file));
        deleteFileFromRepository(file.getId());
    }

    public void deleteUserUploadedFiles(Long userId) {
        List<FileEntity> files = fileRepository.findAllFilesBySpecificUser(userId);
        if (!files.isEmpty()) {
            for (FileEntity file : files) {
                deleteFileFromDirectoryAndRepository(file);
            }
        }
    }

    @Scheduled(fixedRate = 5000) // ovo moran provjerit odjednom nece radi
    public void deleteOutdatedFiles() {
        List<FileEntity> allFiles = fileRepository.findAllOutdatedFiles();

        for (FileEntity file : allFiles) {
            deleteFileFromDirectoryAndRepository(file);
        }
    }

    private void deleteFileFromRepository(Long idFile) {
        try {
            fileRepository.deleteById(idFile);
        } catch (Exception e) {
            log.error("File ID: " + idFile + " is not exist.", e);
        }
    }
}
