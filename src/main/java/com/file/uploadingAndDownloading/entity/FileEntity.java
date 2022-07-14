package com.file.uploadingAndDownloading.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import javax.persistence.*;
import java.time.Instant;

@Table(name = "file")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_generator")
    @SequenceGenerator(name = "file_generator", sequenceName = "file_generator", initialValue = 100, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "file_name", updatable = false, nullable = false)
    private String fileName;

    @Column(name = "description")
    private String description;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "document_size", nullable = false)
    private Long documentSize;

    @Column(name = "upload_date", updatable = false, nullable = false)
    private Instant uploadDate;

    @Column(name = "time_to_live", nullable = false)
    private Instant ttl;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedFileAt;

    @Column(name = "downloaded_at")
    private Instant fileDownloadedAt;

    @Column(name = "download_counter")
    private long downloadCounter;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "download_permission",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> userPermission = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        if (Objects.isNull(fileName)) {
            setFileName(UUID.randomUUID().toString());
        }
        uploadDate = Instant.now();
        updatedFileAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedFileAt = Instant.now();
    }

    public void handleDownload() {
        setDownloadCounter(getDownloadCounter() + 1);
        setFileDownloadedAt(Instant.now());
    }
}
