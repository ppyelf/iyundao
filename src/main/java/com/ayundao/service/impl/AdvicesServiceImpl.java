package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: AdvicesServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class AdvicesServiceImpl implements AdvicesService{

    @Autowired
    private AdvicesRepository advicesRepository;

    @Autowired
    private AdvicesInfoDeparRepository advicesInfoDeparRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private DepartService departService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AdvicesInfoUserRepository advicesInfoUserRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;



    @Override
    public List<Advices> findAll() {
        return advicesRepository.findAllForList();
    }

    @Override
    @Transactional
    public Advices save(Advices advices, String[] subjectIds, String[] departIds, String[] groupIds, String[] userids) {
        advices = advicesRepository.save(advices);
        AdvicesInfoDepar aid ;
        Subject subject;
        Depart depart;
        Groups groups;
        User user;
        //分别添加部门组织机构用户
            for (int i =0;i<subjectIds.length;i++){
                aid = new AdvicesInfoDepar();
                aid.setCreatedDate(new Date());
                aid.setLastModifiedDate(new Date());
                aid.setAdvices(advices);
                subject = subjectService.find(subjectIds[i]);
                aid.setSubject(subject);
                advicesInfoDeparRepository.save(aid);
            }
            for(int j = 0;j<departIds.length;j++){
                aid = new AdvicesInfoDepar();
                aid.setCreatedDate(new Date());
                aid.setLastModifiedDate(new Date());
                aid.setAdvices(advices);
                depart = departService.findById(departIds[j]);
                aid.setDepart(depart);
                advicesInfoDeparRepository.save(aid);
            }
            for (int k = 0;k<groupIds.length;k++){
                aid = new AdvicesInfoDepar();
                aid.setCreatedDate(new Date());
                aid.setLastModifiedDate(new Date());
                aid.setAdvices(advices);
                groups = groupsService.findById(groupIds[k]);
                aid.setGroups(groups);
                advicesInfoDeparRepository.save(aid);
            }
            for (int u = 0;u<userids.length;u++){
                aid = new AdvicesInfoDepar();
                aid.setCreatedDate(new Date());
                aid.setLastModifiedDate(new Date());
                aid.setAdvices(advices);
                user = userService.findById(userids[u]);
                aid.setUser(user);
                advicesInfoDeparRepository.save(aid);
            }



        return advices;
    }

    @Override
    public List<AdvicesInfoUser> findsendistrue(String id) {

        return   advicesInfoUserRepository.findByAdvicesId(id);
    }

    @Override
    public List<AdvicesInfoDepar> findDeptionByAdvicesId(String id) {

        return advicesInfoDeparRepository.findByAdvicesId(id);
    }

    @Override
    @Transactional
    public void saveAdvices(List<AdvicesInfoDepar> advicesInfoDepars) {
        AdvicesInfoUser aiu;
        //遍历每一个关系部门
        for (AdvicesInfoDepar advicesInfoDepar : advicesInfoDepars) {
            //如果是机构获取所有部门组织
            if (advicesInfoDepar.getSubject()!=null){
                List<User> users = userService.findBySubjectIdForPage(advicesInfoDepar.getSubject().getId());
                saveUser(advicesInfoDepar,users);
            }
                //根据id获得所有部门
//                List<Depart> departs = departService.findBySubjectId(advicesInfoDepar.getSubject().getId());
//                //如果不为空遍历
//                if (CollectionUtils.isNotEmpty(departs)){
//                    for (Depart depart : departs) {
//                        List<User> users = userService.findByGroupIdForPage(depart.getId());
//                        saveUser(advicesInfoDepar,users);
//                    }
//                }
//                List<Groups> groups = groupsService.findBySubjectId(advicesInfoDepar.getSubject().getId());
//                if (CollectionUtils.isNotEmpty(groups)){
//                    for (Groups group : groups) {
//                        List<User> users = userService.findByDepartIdForPage(group.getId());
//                        saveUser(advicesInfoDepar,users);
//                    }
//                }
            if (advicesInfoDepar.getDepart()!=null){
                    List<User> users = userService.findByDepartIdForPage(advicesInfoDepar.getDepart().getId());

            }
            if (advicesInfoDepar.getGroups()!=null){
                List<User> users =userService.findByGroupIdForPage(advicesInfoDepar.getGroups().getId());
                saveUser(advicesInfoDepar,users);
            }
            if (advicesInfoDepar.getUser()!=null){
                UserInfo uif = userInfoRepository.findByUserId(advicesInfoDepar.getUser().getId());
                aiu = new AdvicesInfoUser();
                aiu.setCreatedDate(new Date());
                aiu.setLastModifiedDate(new Date());
                aiu.setAdvices(advicesInfoDepar.getAdvices());
                aiu.setUser(advicesInfoDepar.getUser());
                aiu.setState("未接收");
                //这一块如果角色没有建立关系，或报错
                aiu.setPhone(uif.getPhone());
                advicesInfoUserRepository.save(aiu);
            }
        }

    }

    @Override
    public void updatestate(String id, String state) {
        advicesRepository.updatestate(id,state);
    }

    private void saveUser(AdvicesInfoDepar advicesInfoDepar, List<User> users) {
        for (User user : users) {
            UserInfo uif = userInfoRepository.findByUserId(user.getId());
            AdvicesInfoUser aiu = new AdvicesInfoUser();
            aiu.setCreatedDate(new Date());
            aiu.setLastModifiedDate(new Date());
            aiu.setAdvices(advicesInfoDepar.getAdvices());
            aiu.setUser(user);
            aiu.setState("未接收");
            aiu.setPhone(uif.getPhone());
            advicesInfoUserRepository.save(aiu);
        }
    }


    @Override
    public Advices findById(String id) {

        return advicesRepository.find(id);
    }

    @Override
    @Transactional
    public void delete(Advices advices) {
            User user = advices.getUser();
            user.setAdvices(null);
            userRepository.save(user);
            advicesRepository.delete(advices);
    }

    @Override
    @Transactional
    public List<Advices> findAdvicesByDeptionId(String id) {

        return advicesRepository.findAdvicesByDeptionId(id);
    }


}
