package cn.lovingliu.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String uploadImage(MultipartFile file);
    List<String> uploadImages(MultipartFile[] files);
    boolean deleteImage(String fileName);
}
