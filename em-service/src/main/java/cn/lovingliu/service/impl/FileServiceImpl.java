package cn.lovingliu.service.impl;

import cn.lovingliu.enums.ExceptionCodeEnum;
import cn.lovingliu.exception.EMException;
import cn.lovingliu.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final String[] IMAGETYPES = {"gif", "png", "jpg", "jpeg"};

    @Value("${file.upload-path}")
    private String UPLOADPATH;

    public String uploadImage(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        boolean ifContain = Arrays.asList(IMAGETYPES).contains(fileType);
        if(!ifContain){
            throw new EMException(ExceptionCodeEnum.UPLOAD_FILE_TYPE_ERROR);
        }
        String fileName = uploadFile(UPLOADPATH,file);
        return fileName;
    }

    public boolean deleteImage(String fileName){
        return deleteFile(fileName);
    }

    private boolean deleteFile(String fileName){
        File uploadPath = new File(UPLOADPATH);
        // 判断文件是否存在
        boolean falg = false;
        falg = uploadPath.exists();
        if (falg) {
            logger.debug("{}文件存在",UPLOADPATH);
            if (true == uploadPath.isDirectory()) {
                File file = new File(UPLOADPATH + fileName);
                if (true==file.isFile()) {
                    boolean deleteSuccess = false;
                    deleteSuccess = file.delete();
                    if (deleteSuccess) {
                        logger.debug("删除无效图片文件 {} 成功", UPLOADPATH + fileName);
                    }else {
                        logger.debug("删除无效图片文件 {} 失败", UPLOADPATH + fileName);
                    }
                    return deleteSuccess;
                }else {
                    logger.debug("{}是无效图片文件",UPLOADPATH + fileName);
                    return false;
                }
            }else {
                logger.debug("{}文件存在",UPLOADPATH);
                return false;
            }
        } else {
            logger.debug("未找到{}文件夹",UPLOADPATH);
            return false;
        }
    }
    private String uploadFile(String uploadPath,MultipartFile file){
        InputStream inputStream = null;
        OutputStream os = null;
        String path = null;
        String originalFilename = file.getOriginalFilename();
        String fileName = new Date().getTime()+"_"+originalFilename;
        try{
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(uploadPath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            inputStream = file.getInputStream();
            path = tempFile.getPath() + File.separator + fileName;
            os = new FileOutputStream(path);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

}
