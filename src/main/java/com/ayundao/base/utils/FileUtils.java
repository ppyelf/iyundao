package com.ayundao.base.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
public class FileUtils {

    /**
     * 文件标识符
     */
    protected final static String FILE_IDENTIFIER = ".";

    /**
     * 上传文件
     * @param file  文件
     * @param o     实体对象
     * @param path  配置的根路径
     * @return  Map<String,String>
     *     suffix 后缀名
     *     name   文件名
     *     url    相对路径
     * </String,String>
     */
    public static Map<String, String> uploadFile(MultipartFile file, Object o, String path) {
        Map<String, String> map = new HashMap<>();
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String suffix = file.getOriginalFilename();
            map.put("suffix", suffix.substring(suffix.lastIndexOf(FILE_IDENTIFIER)).replace(".", ""));
            map.put("name", UUID.randomUUID().toString().replace("-", ""));

            String filePath = o.getClass().getSimpleName().toLowerCase() + "\\" + map.get("name") + "." + map.get("suffix");
            map.put("url", filePath);

            File newFile = new File(path+filePath);
            if (!newFile.exists()) {
                newFile.getParentFile().mkdirs();
            }
            newFile.createNewFile();
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 删除文件
     * @param path 文件路径
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        file.delete();
        return false;
    }
}
