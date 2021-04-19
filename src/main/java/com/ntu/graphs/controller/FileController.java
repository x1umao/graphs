package com.ntu.graphs.controller;

import com.ntu.graphs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        System.out.println(file);
        if (file.isEmpty()) {
            return "Empty file!";
        }else{
            return fileService.upload(file);
        }
    }
}
