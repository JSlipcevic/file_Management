package com.file.uploadingAndDownloading;

import com.file.uploadingAndDownloading.repository.FileRepository;
import com.file.uploadingAndDownloading.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestContext {

    @Autowired
    protected FileRepository fileRepository;

    @Autowired
    protected UserRepository userRepository;

    @BeforeEach
    public void beforeEach (){
        fileRepository.deleteAll();
        userRepository.deleteAll();
    }
}
