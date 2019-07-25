package com.ayundao.service.impl;

import com.ayundao.entity.User;
import com.ayundao.entity.UserApp;
import com.ayundao.repository.UserAppRepository;
import com.ayundao.repository.UserRepository;
import com.ayundao.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName: UserAppServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/20 16:54
 * @Description: 实现 - 用户授权
 * @Version: V1.0
 */
@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserApp findByOpenId(String openId) {
        return userAppRepository.findByOpenId(openId);
    }

    @Override
    public UserApp save(UserApp userApp) {
        userApp = userAppRepository.save(userApp);
        return userApp;
    }

    @Override
    @Transactional(rollbackFor = {})
    public UserApp create(String open_id, String avatarUrl, String nickName, String city, String province, UserApp.APP_TYPE type, User user) {
        UserApp app = new UserApp();
        app.setCreatedDate(new Date());
        app.setLastModifiedDate(new Date());
        app.setLastLoginTime(new Date());
        app.setOpenId(open_id);
        app.setAvatarUrl(avatarUrl);
        app.setNickName(nickName);
        app.setCity(city);
        app.setProvince(province);
        app.setType(type);
        app.setUser(user);
        app = userAppRepository.save(app);
        return app;
    }
}
