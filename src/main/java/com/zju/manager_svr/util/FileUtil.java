package com.zju.manager_svr.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.zju.manager_svr.exception.DeleteUploadException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    static final String UPLOAD_PATH = "upload/";

    public static void deleteUploadFile(String filename) throws DeleteUploadException {
        Path path = Paths.get(UPLOAD_PATH, filename);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("delete fail:", e);
            throw new DeleteUploadException(e);
        }
    }
}
