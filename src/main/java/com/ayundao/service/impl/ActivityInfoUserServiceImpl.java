package com.ayundao.service.impl;

import com.ayundao.entity.Activity;
import com.ayundao.entity.ActivityInfoUser;
import com.ayundao.entity.User;
import com.ayundao.repository.ActivityInfoUserRepository;
import com.ayundao.service.ActivityInfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName: ActivityInfoUserServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class ActivityInfoUserServiceImpl implements ActivityInfoUserService {

    @Autowired
    ActivityInfoUserRepository activityInfoUserRepository;

    @Override
    public ActivityInfoUser save(Activity activity, User user) {
        ActivityInfoUser activityInfoUser = new ActivityInfoUser();
        activityInfoUser.setCreatedDate(new Date());
        activityInfoUser.setLastModifiedDate(new Date());
        activityInfoUser.setActivity(activity);
        activityInfoUser.setUser(user);
        return  activityInfoUserRepository.save(activityInfoUser);
    }
}
