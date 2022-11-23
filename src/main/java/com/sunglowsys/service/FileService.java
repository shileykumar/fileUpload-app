package com.sunglowsys.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String upload(String path, MultipartFile file) throws IOException;
}
