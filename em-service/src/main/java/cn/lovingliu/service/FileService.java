package cn.lovingliu.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadImage(MultipartFile file);
    boolean deleteImage(String fileName);
}
