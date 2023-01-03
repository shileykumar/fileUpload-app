package com.sunglowsys.controller;

import com.sunglowsys.payload.FileResponse;
import com.sunglowsys.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Value("${file.size}")
    private long fileSize;
    private String fileName;

    private List<String> extensionArray = Arrays.asList( "png", "jpg","jpeg", "gif");
    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadImage(@RequestParam(value = "image") MultipartFile image) {
        try {
            if (!extensionArray.contains(checkExtension(image.getOriginalFilename()))) {
                return new ResponseEntity<>(new FileResponse(image.getOriginalFilename(), "Invalid file extension!"),HttpStatus.NOT_ACCEPTABLE);
            }
            if (image.getSize() > fileSize) {
                return new ResponseEntity<>(new FileResponse(image.getOriginalFilename(), "Please upload file less then "+ (fileSize + 1) +"KB size"), HttpStatus.NOT_ACCEPTABLE);
            }
            fileName = fileService.upload(path,image);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(fileName, "File not uploaded due to error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new FileResponse(fileName, "File uploaded!"), HttpStatus.OK);
    }

    private String checkExtension(String name) {
        String[] splitArray = name.split("\\.");
        String extension = splitArray[splitArray.length - 1];
        return extension;
    }
}
