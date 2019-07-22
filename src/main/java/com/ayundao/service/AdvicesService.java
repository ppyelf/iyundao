package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Advices;
import com.ayundao.entity.AdvicesInfoDepar;
import com.ayundao.entity.AdvicesInfoUser;
import com.ayundao.entity.User;

import java.util.List;

/**
 * @ClassName: AdvicesService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
public interface AdvicesService {

    List<Advices> findAll();


    /**
     * 根据id查找
     * @param id
     * @return
     */
    Advices findById(String id);

    /**
     *删除实体
     */
    void delete(Advices advices);

    /**
     * 根据部门组织id查询消息实体
     * @param id
     * @return
     */
    List<Advices> findAdvicesByDeptionId(String id);

    /**
     * 添加消息
     * @param advices
     * @param subjectIds
     * @param departIds
     * @param groupIds
     * @param userids
     * @return
     */
    Advices save(Advices advices, String[] subjectIds, String[] departIds, String[] groupIds, String[] userids);



    /**
     * 查看是否已经发送过消息
     * @param id
     * @return
     */
    List<AdvicesInfoUser> findsendistrue(String id);

    /**
     * 通过消息id查找关系
     * @param id
     * @return
     */
    List<AdvicesInfoDepar> findDeptionByAdvicesId(String id);

    /**
     * 发送消息
     * @param advicesInfoDepars
     */
    void saveAdvices(List<AdvicesInfoDepar> advicesInfoDepars);

    /**
     * 修改消息状态
     * @param id
     * @param state
     */
    void updatestate(String id, String state);

    /**
     * 查找所有的用户关联活动
     * @param user
     * @return
     */
    List<AdvicesInfoUser> findAllByUser(User user);
}
