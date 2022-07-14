package com.file.uploadingAndDownloading.service;

import com.file.uploadingAndDownloading.dto.File;
import com.file.uploadingAndDownloading.entity.FileEntity;
import com.file.uploadingAndDownloading.repository.FileRepository;
import com.file.uploadingAndDownloading.util.FileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public void saveFile(FileEntity file) {
        fileRepository.save(file);
    }

    public FileEntity getFile(Long idFile) {
        return fileRepository.findById(idFile).orElseThrow(() ->
                new EntityNotFoundException("File ID is not found. ID: " + idFile));
    }

    public File getFileDto(Long idFile) {
        return FileMapper.toDtoFile(getFile(idFile));
    }

    public File editFileDescription(Long idFile, File newFile) {
        FileEntity file = getFile(idFile);

        if (newFile.getDescription() != null) {
            file.setDescription(newFile.getDescription());
        }
        saveFile(file);
        return FileMapper.toDtoFile(file);
    }
}
