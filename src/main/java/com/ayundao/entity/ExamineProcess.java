package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;

/**
 * @ClassName: ExamineProcess
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/22 10:53
 * @Description: 实体 - 审核流程
 * @Version: V1.0
 */
@Entity
@Table(name = "t_examine_process")
public class ExamineProcess extends BaseEntity<String> {

    private final static long serialVersionUID = -1320482109348093218L;

    /**
     * 机构ID
     */
    @Column(name = "SUBJECTID", nullable = false, length = 50)
    private String subjectId;

    /**
     * 部门ID
     */
    @Column(name = "DEPARTID", nullable = false, length = 50)
    private String departId;

    /**
     * 小组ID
     */
    @Column(name = "GROUPID", nullable = false, length = 50)
    private String groupId;

    /**
     * 用户id
     */
    @Column(name = "USERID", nullable = false, length = 50)
    private String userId;

    /**
     * 所属审核
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXAMINEID", nullable = false)
    private Examine examine;

}
