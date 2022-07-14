package com.file.uploadingAndDownloading.service;

import com.file.uploadingAndDownloading.entity.FileEntity;
import com.file.uploadingAndDownloading.entity.UserEntity;
import com.file.uploadingAndDownloading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class FilePermissionService {

    private final UserRepository userRepository;
    private final FileService fileService;

    public void addPermissionToNewUsers(Long idFile, Set<Long> newUsersToShare) {
        FileEntity file = fileService.getFile(idFile);
        setUserPermission(file, newUsersToShare);
    }

    public void setUserPermission(FileEntity file, Set<Long> newUsersToShare) {
        List<UserEntity> newUsers = userRepository.getUsers(newUsersToShare);
        if (newUsers.isEmpty()) {
            return;
        }
        checkIfAllUsersExist(newUsers, newUsersToShare);
        checkIfUserOwnsFile(file, newUsersToShare);
        Set<UserEntity> existingUsers = new HashSet<>(file.getUserPermission());
        existingUsers.addAll(newUsers);
        List<UserEntity> combinedList = new ArrayList<>(existingUsers);
        file.setUserPermission(combinedList);
        fileService.saveFile(file);
    }

    public void recallUsersPermission(Long idFile, Set<Long> recallUsers) {
        userRepository.recallUsersPermission(idFile, recallUsers);
    }

    public void recallAllPermissions(Long idFile) {
        userRepository.recallAllUsersDownloadPermissionForSomeFile(idFile);
    }

    private void checkIfUserOwnsFile(FileEntity file, Set<Long> newUsersToShare) {
        newUsersToShare.stream()
                .filter(newUser -> file.getUser().getId().equals(newUser))
                .findAny()
                .ifPresent((user)-> { throw new IllegalArgumentException("Because you are owner of file you already have a permission to download. Permission is not necessary!");});
    }

    private void checkIfAllUsersExist(List<UserEntity> users, Set<Long> newUsersToShare) {
        if (users.size() != newUsersToShare.size()) {
            throw new IllegalArgumentException("Some of users is not exist. Please check your users one more time!");
        }
    }


}
