package com.file.uploadingAndDownloading.repository;

import com.file.uploadingAndDownloading.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.id IN :ids")
    List<UserEntity> getUsers(@Param("ids") Set<Long> ids);


    @Modifying
    @Transactional //ovo moran dobro skužit kao i modifying
    @Query(value = "DELETE FROM download_permission WHERE user_id = :id", nativeQuery = true)
    void deleteUserPermissions(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM download_permission WHERE file_id = :fileId AND user_id IN :ids", nativeQuery = true)
    void recallUsersPermission(@Param("fileId") Long fileId, @Param("ids") Set<Long> userIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM download_permission WHERE file_id = :fileId", nativeQuery = true)
    void recallAllUsersDownloadPermissionForSomeFile(@Param("fileId") Long fileId);

    @Query(value = "SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM download_permission d WHERE d.file_id = :fileId" +
            " AND d.user_id = :userId", nativeQuery = true)
    boolean checkUserPermissionToDownload(@Param("fileId") Long fileId, @Param("userId") Long userId);
}

