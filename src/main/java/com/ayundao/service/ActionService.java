package com.ayundao.service;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Action;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: ActionService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/28 11:18
 * @Description: 服务 - 春风行动
 * @Version: V1.0
 */
public interface ActionService {

    /**
     * 查询分页
     * @return
     * @param pageable
     */
    Page<Action> findPage(Pageable pageable);

    /**
     * 下载春风行动模板
     * @param req
     * @param resp
     */
    void downloadExcel(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 导入春风行动
     * @param file
     * @return
     */
    JsonResult upload(MultipartFile file);

    /**
     * 转换用数据
     */
    JSONObject covert(Action action);

    /**
     * 导出春风行动
     * @param id
     * @param year
     * @param list
     * @param req
     * @param resp
     */
    JsonResult export(String id, String year, List<Action> list, HttpServletRequest req, HttpServletResponse resp);

    /**
     * 根据年份和机构ID导出春风行动
     * @param id
     * @param year
     * @return
     */
    List<Action> findBySubjectIdAndYear(String id, String year);
}
