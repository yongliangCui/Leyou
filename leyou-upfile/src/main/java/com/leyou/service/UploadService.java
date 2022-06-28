package com.leyou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service("UploadService")
public class UploadService {
    private static final List<String> content_type = Arrays.asList("image/gif");
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);
    public String uploadImage(MultipartFile file) throws IOException {
//      校验文件格式
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        System.out.println(contentType);
        if (!content_type.contains(contentType)){
            LOGGER.info("文件类型："+ fileName+"不合法");
            return null;
        }
//      校验文件内容
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null){
            LOGGER.info("文件内容不合法");
            return null;
        }
//      保存文件到服务器
        file.transferTo(new File("D:\\cyl\\Temp\\" + fileName));
        return "200";
    }
}
