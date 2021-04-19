package com.ntu.graphs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    public String upload(MultipartFile file){
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("File format is：" + suffixName);
            // 设置文件存储路径
            String filePath = new File("").getAbsolutePath();
            String path = filePath +"\\"+fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return "Successfully Uploaded!";
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return "Fail to upload.";
    }
}
