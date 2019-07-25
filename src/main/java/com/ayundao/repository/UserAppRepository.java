package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserApp;

/**
 * @ClassName: UserAppRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/20 16:55
 * @Description: 仓库 - 用户授权
 * @Version: V1.0
 */
public interface UserAppRepository extends BaseRepository<UserApp, String> {

    /**
     * 根据openid查询实体
     * @param openId
     * @return
     */
    UserApp findByOpenId(String openId);
}
