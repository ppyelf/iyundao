package com.ayundao.base.utils;

import com.ayundao.base.BaseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: FileUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 16:45
 * @Description: 工具类 - 文件
 * @Version: V1.0
 */
@Component
public class FileUtils {

    /**
     * 文件标识符
     */
    protected final static String FILE_IDENTIFIER = "\\.";

    private static String path = "classpath:/META-INF/resources/upload/";

    /**
     * 上传文件
     * @param file
     * @param o
     * @return
     */
    public static Map<String, String> uploadFile(MultipartFile file, Object o) {
        Map<String, String> map = new HashMap<>();
        if (file.isEmpty()) {
            return map;
        }
        try {
            String name = file.getOriginalFilename().split(FILE_IDENTIFIER)[1];
            String filePath = path + o.getClass().getSimpleName() + "/" + UUID.randomUUID().toString().replace("-", "") + name;

            byte[] b = file.getBytes();
            File newFile = new File(filePath);
            if (newFile.exists()) {
                newFile.mkdirs();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 写入文件
     */
    private boolean writeFile(byte[] bytes, String path) {

        return false;
    }
}
