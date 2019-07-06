package com.ayundao.base.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    protected final static Log logger = LogFactory.getLog(FileUtils.class);
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
    public static Map<String,String> uploadUserFile(MultipartFile file, Object o, String path, String userCode) {
        Map<String, String> map = new HashMap<>();
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String suffix = file.getOriginalFilename();
            map.put("suffix", suffix.substring(suffix.lastIndexOf(FILE_IDENTIFIER)).replace(".", ""));
            map.put("name", UUID.randomUUID().toString().replace("-", ""));

            String filePath = o.getClass().getSimpleName().toLowerCase() + "\\" +userCode+ "\\" + map.get("name") + "." + map.get("suffix");
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

    /**
     * 下载文件
     */
    public static void download(String path, HttpServletRequest req, HttpServletResponse resp)  {
        File downloadFile = new File(path);

        ServletContext context = req.getServletContext();

        String mimeType = context.getMimeType(path);
        if (mimeType == null) {
            mimeType = "multipart/form-data";
        }

        resp.setContentType(mimeType);
        resp.setContentLength((int) downloadFile.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        resp.setHeader(headerKey, headerValue);

        try {
            InputStream myStream = new FileInputStream(path);
            IOUtils.copy(myStream, resp.getOutputStream());
            resp.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
