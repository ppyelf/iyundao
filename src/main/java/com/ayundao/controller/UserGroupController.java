package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserGroupController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 0:14
 * @Description: 控制层 - 用户组
 * @Version: V1.0
 */
@RestController
@RequestMapping("/ugroup")
public class UserGroupController extends BaseController {

    @Autowired
    private UserGroupService userGroupService;
}
