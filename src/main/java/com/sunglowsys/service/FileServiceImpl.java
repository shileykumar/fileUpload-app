package com.sunglowsys.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String upload(String path, MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();

        String filePath = path + File.separator + name;

        //to create folder if not created
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdir();
        }

        //copy file
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return name;
    }


}
