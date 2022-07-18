package com.file.uploadingAndDownloading.repository;

import com.file.uploadingAndDownloading.entity.FileEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    @Query("SELECT f FROM FileEntity f WHERE ttl < current_date")
    List<FileEntity> findAllOutdatedFiles();

    Optional<FileEntity> findByFileName(String fileName);

    @Query("SELECT count(f) FROM FileEntity f WHERE user.id = :id")
    Long userFilesCount(@Param("id") Long id);

    @Query("SELECT f FROM FileEntity f WHERE user.id = :id")
    List<FileEntity> findAllFilesBySpecificUser(@Param("id") Long id);

    @Query(value = "SELECT \n" +
            "\tid, file_name, description, document_type, document_size, upload_date, \n" +
            "\ttime_to_live, updated_at, downloaded_at, download_counter, user_id \n" +
            "FROM file fe WHERE user_id = :userId\n" +
            "UNION SELECT id, file_name, description, document_type, document_size, upload_date, \n" +
            "\ttime_to_live, updated_at, downloaded_at, download_counter, f.user_id as user_id\n" +
            "FROM download_permission d INNER JOIN file f ON d.file_id = id WHERE d.user_id = :userId", nativeQuery = true)
    List<FileEntity> findAllFilesBySpecificUser(@Param("userId") Long id, Pageable pageable);


}
