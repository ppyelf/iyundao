package com.ayundao.service;

import com.ayundao.entity.Button;
import com.ayundao.entity.Field;
import com.ayundao.entity.User;

import java.util.List;

/**
 * @ClassName: ButtonRoleService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 3:30
 * @Description: 服务 - 按钮关系
 * @Version: V1.0
 */
public interface ButtonRoleService {

    /**
     * 根据用户和字段获取按钮
     * @param user
     * @param field
     * @return
     */
    List<Button> findByUserAndField(User user, Field field);
}
