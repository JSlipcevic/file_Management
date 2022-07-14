package com.file.uploadingAndDownloading.service;

import com.file.uploadingAndDownloading.dto.DetailedUser;
import com.file.uploadingAndDownloading.dto.File;
import com.file.uploadingAndDownloading.dto.User;
import com.file.uploadingAndDownloading.entity.FileEntity;
import com.file.uploadingAndDownloading.entity.UserEntity;
import com.file.uploadingAndDownloading.repository.FileRepository;
import com.file.uploadingAndDownloading.repository.UserRepository;
import com.file.uploadingAndDownloading.util.FileMapper;
import com.file.uploadingAndDownloading.util.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final FileDeleteService fileDeleteService;

    public User createUser(DetailedUser detailedUser) {
        UserEntity newUser = new UserEntity(detailedUser);
        userRepository.save(newUser);
        return UserMapper.toDtoUser(newUser);
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User ID is not found. ID: " + id));
    }

    public User getUserDto(Long id) {
        return UserMapper.toDtoUser(getUser(id));
    }

    public User updateUser(Long idUser, DetailedUser newDetailedUser) { //todo: model mapper
            UserEntity user = getUser(idUser);
            if (newDetailedUser.getFirstName() != null) {
                user.setFirstName(newDetailedUser.getFirstName());
            }
            if (newDetailedUser.getLastName() != null) {
                user.setLastName(newDetailedUser.getLastName());
            }
            if (newDetailedUser.getUserName() != null) {
                user.setUserName(newDetailedUser.getUserName());
            }
            if (newDetailedUser.getPassword() != null) {
                user.setPassword(newDetailedUser.getPassword());
            }
            if (newDetailedUser.getEmail() != null) {
                user.setEmail(newDetailedUser.getEmail());
            }
            if (newDetailedUser.getCountry() != null) {
                user.setCountry(newDetailedUser.getCountry());
            }
            if (newDetailedUser.getCity() != null) {
                user.setCity(newDetailedUser.getCity());
            }
            if (newDetailedUser.getUserPackage() != null) {
                user.setUserPackage(newDetailedUser.getUserPackage().getName());
            }

            userRepository.save(user);
            return UserMapper.toDtoUser(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteUserPermissions(userId);
        fileDeleteService.deleteUserUploadedFiles(userId);
        deleteUserFromRepository(userId);
    }



    public List<File> getFilesFromUser(Long userId) {
        //ZA PEKU.... ima vec jedna slicna metoda (sortedFileByDownloadWithPagination - sortira sve file-ove od nekog user-a prema broju download-a)
        // pa baci oko ocu ovu izbrisat ili ne (ja bi je maka)
        List<FileEntity> allUserFiles = fileRepository.findAllFilesBySpecificUser(userId);
        return allUserFiles.stream().map(FileMapper::toDtoFile).collect(Collectors.toList());
    }

    private void deleteUserFromRepository(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            log.error("User ID: " + userId + " is not exist.", e);
        }
    }
}
