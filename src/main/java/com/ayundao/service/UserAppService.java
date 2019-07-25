package com.ayundao.service;

import com.ayundao.entity.User;
import com.ayundao.entity.UserApp;

/**
 * @ClassName: UserAppService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/20 16:54
 * @Description: 服务 - 用户授权
 * @Version: V1.0
 */
public interface UserAppService {

    /**
     * 根据OPENID查询实体
     * @param openId
     * @return
     */
    UserApp findByOpenId(String openId);

    /**
     * 保存实体
     * @param userApp
     */
    UserApp save(UserApp userApp);

    /**
     * 创建实体
     * @param open_id
     * @param avatarUrl
     * @param nickName
     * @param city
     * @param province
     * @param weApp
     * @param user
     * @return
     */
    UserApp create(String open_id, String avatarUrl, String nickName, String city, String province, UserApp.APP_TYPE weApp, User user);
}
