package com.file.uploadingAndDownloading.entity;

import com.file.uploadingAndDownloading.dto.DetailedUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_generator", initialValue = 100, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    private String email;

    private String country;

    private String city;

    private String gender;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "package", nullable = false)
    private String userPackage;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<FileEntity> uploadedFiles = new ArrayList<>();

    @ManyToMany(mappedBy = "userPermission", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<FileEntity> sharedFiles = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public UserEntity(DetailedUser detailedUser) {
        this.firstName = detailedUser.getFirstName();
        this.lastName = detailedUser.getLastName();
        this.userName = detailedUser.getUserName();
        this.password = detailedUser.getPassword();
        this.email = detailedUser.getEmail();
        this.country = detailedUser.getCountry();
        this.city = detailedUser.getCity();
        this.gender = detailedUser.getGender().getName();
        this.userPackage = detailedUser.getUserPackage().getName();
    }

}
