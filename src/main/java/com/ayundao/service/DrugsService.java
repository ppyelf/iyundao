package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: DrugsService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/23 11:50
 * @Description: 服务 - 药品预警
 * @Version: V1.0
 */
public interface DrugsService {

    /**
     * 导入药品预警
     * @param file
     * @param jsonResult
     * @return
     */
    JsonResult upload(MultipartFile file, JsonResult jsonResult);

    /**
     * 获取列表
     * @return
     * @param time
     * @param code
     * @param num
     * @param size
     * @param jsonResult
     */
    JsonResult findList(String time, String code, int num, int size, JsonResult jsonResult);
}
