package com.file.uploadingAndDownloading;

import com.file.uploadingAndDownloading.configuration.FileConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(FileConfig.class)
public class UploadingAndDownloadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadingAndDownloadingApplication.class, args);
	}
}
