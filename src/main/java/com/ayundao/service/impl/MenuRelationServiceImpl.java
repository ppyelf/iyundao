package com.ayundao.service.impl;

import com.ayundao.entity.Menu;
import com.ayundao.entity.UserGroupRelation;
import com.ayundao.repository.MenuRelationRepository;
import com.ayundao.repository.UserGroupRelationRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.MenuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MenuRelationServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 16:39
 * @Description: 服务实现 - 菜单关系
 * @Version: V1.0
 */
@Service
public class MenuRelationServiceImpl implements MenuRelationService {

    @Autowired
    private MenuRelationRepository menuRelationRepository;

}
