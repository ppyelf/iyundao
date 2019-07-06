package com.ayundao.service;

import com.ayundao.entity.Activity;
import com.ayundao.entity.ActivityInfoUser;
import com.ayundao.entity.User;

/**
 * @ClassName: ActivityInfoUserService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
public interface ActivityInfoUserService {

    //添加活动报名人员
    ActivityInfoUser save(Activity activity, User user);
}
