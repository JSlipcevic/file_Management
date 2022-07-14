package com.file.uploadingAndDownloading.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class MyFileNotUploadException extends RuntimeException {

    public MyFileNotUploadException(String message) {
        super(message);
    }

    public MyFileNotUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
