package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.PageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: PageServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 1:26
 * @Description: 实现 - 页面
 * @Version: V1.0
 */
@Service
@Transactional
public class PageServiceImpl implements PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private FieldRoleRepository fieldRoleRepository;

    @Autowired
    private UserGroupRelationRepository userGroupRelationRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public List<Page> getPageByUserAndMenu(List<Menu> menu) {
        return pageRepository.getPageByUserAndMenu(menu);
    }

    @Override
    public Page getPageByMenu(Menu menu) {
        return pageRepository.getPageByMenuId(menu.getId());
    }

    @Override
    public List<Field> getFieldsByUserAndPage(User user, Page page) {
        List<UserGroup> userGroups = userGroupRelationRepository.findByUserIdForUserGroup(user.getId());
        List<Role> roles = userRoleRepository.findByUserId(user.getId());
        List<Field> fields = fieldRoleRepository.findByPageAndUserGroupAndRole(
                CollectionUtils.isEmpty(userGroups) ? null : userGroups,
                CollectionUtils.isEmpty(roles) ? null : roles);
        if (CollectionUtils.isNotEmpty(fields)) {
            List<Field> list = new ArrayList<>();
            for (Field f : fields) {
                if (f.getPage().getId().equals(page.getId())) {
                    list.add(f);
                } 
            }
            return list;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Page> getAllForList() {
        return pageRepository.getAllForList();
    }

    @Override
    public Page find(String id) {
        return pageRepository.find(id);
    }

    @Override
    @Transactional
    public Page save(Page page, Menu menu, Page father) {
        if (father != null) {
            page.setFather(father);
        }
        page.setMenu(menu);
        page = pageRepository.save(page);
        return page;
    }

    @Override
    public List<Page> findPageByMenuId(String id) {
        return pageRepository.findPageByMenuId(id);
    }

    @Override
    @Transactional
    public void delete(String id) {
        pageRepository.deleteById(id);
    }

    @Override
    public List<Page> findSonsByFatherId(String id) {
        return pageRepository.findSonsByFatherId(id);
    }

    @Override
    public List<Page> findByIds(String[] pageIds) {
        List<Page> pages = pageRepository.findByIds(pageIds);
        return CollectionUtils.isEmpty(pages)
                ? new ArrayList<>()
                : pages;
    }

    @Override
    public List<Page> getPageByFatherIsNull() {
        return pageRepository.getPageByFatherIsNull();
    }
}
