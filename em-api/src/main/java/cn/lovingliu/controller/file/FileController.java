package cn.lovingliu.controller.file;

import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author：LovingLiu
 * @Description:
 * @Date：Created in 2020-02-11
 */
@Api(value = "文件处理",tags = "文件处理")
@RestController
@RequestMapping("file")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传单张图片",notes = "上传单张图片",httpMethod = "POST")
    @PostMapping("/uploadImage")
    public ServerResponse create(MultipartFile file){
        String fileName = fileService.uploadImage(file);
        return ServerResponse.createBySuccess(fileName);
    }
    @ApiOperation(value = "上传多张图片",notes = "上传多张图片",httpMethod = "POST")
    @PostMapping("/uploadImages")
    public ServerResponse uploadImages(MultipartFile[] files){
        List<String> fileNameList = fileService.uploadImages(files);
        return ServerResponse.createBySuccess(fileNameList);
    }
}
