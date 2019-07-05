package com.ayundao.service;

import java.util.Map;
/**
 * @ClassName: UserInfoPowerService
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/12 9:00
 * @Description: 服务层 -- 用户详情 -力量图谱
 * @Version: V1.0
 */
public interface UserInfoPowerService {
    /**
     * 查询妇代会详情男女比例
     * @return
     */
    Map<String,Object> countBySexFdh();

    /**
     * 查询妇代会详情学历比例
     * @return
     */
    Map<String,Object> countByEducationFdh();

    /**
     * 查询妇代会年龄比例
     * @return
     */
    Map<String,Object> countByIdcardFdh();

    /**
     * 查询妇代会详情科室比例
     * @return
     */
    Map<String,Object> countByDepartmentFdh();

    /**
     * 查询妇代会详情工龄比例
     * @return
     */
    Map<String,Object> countByPartyAgeFdh();

    /**
     * 查询妇代会详情职称比例
     * @return
     */
    Map<String,Object> countByTitleFdh();

    /**
     * 妇代会籍贯比例
     * @return
     */
    Map<String,Object> countByPlaceFdh();

    /**
     * 妇代会身份比例
     * @return
     */
    Map<String,Object> countByIdentityFdh();

    /**
     * 查询工会详情男女比例
     * @return
     */
    Map<String,Object> countBySexGh();

    /**
     * 查询工会详情学历比例
     * @return
     */
    Map<String,Object> countByEducationGh();

    /**
     * 查询工会年龄比例
     * @return
     */
    Map<String,Object> countByIdcardGh();

    /**
     * 查询工会详情科室比例
     * @return
     */
    Map<String,Object> countByDepartmentGh();

    /**
     * 查询工会详情工龄比例
     * @return
     */
    Map<String,Object> countByPartyAgeGh();

    /**
     * 查询工会详情职称比例
     * @return
     */
    Map<String,Object> countByTitleGh();

    /**
     * 工会籍贯比例
     * @return
     */
    Map<String,Object> countByPlaceGh();

    /**
     * 工会身份比例
     * @return
     */
    Map<String,Object> countByIdentityGh();

    /**
     * 查询团委详情男女比例
     * @return
     */
    Map<String,Object> countBySexTw();

    /**
     * 查询团委详情学历比例
     * @return
     */
    Map<String,Object> countByEducationTw();

    /**
     * 查询团委年龄比例
     * @return
     */
    Map<String,Object> countByIdcardTw();

    /**
     * 查询团委详情科室比例
     * @return
     */
    Map<String,Object> countByDepartmentTw();

    /**
     * 查询团委详情工龄比例
     * @return
     */
    Map<String,Object> countByPartyAgeTw();

    /**
     * 查询团委详情职称比例
     * @return
     */
    Map<String,Object> countByTitleTw();

    /**
     * 团委籍贯比例
     * @return
     */
    Map<String,Object> countByPlaceTw();

    /**
     * 团委身份比例
     * @return
     */
    Map<String,Object> countByIdentityTw();

    /**
     * 查询离退休老干部详情男女比例
     * @return
     */
    Map<String,Object> countBySexLtxlgb();

    /**
     * 查询离退休老干部详情学历比例
     * @return
     */
    Map<String,Object> countByEducationLtxlgb();

    /**
     * 查询离退休老干部年龄比例
     * @return
     */
    Map<String,Object> countByIdcardLtxlgb();

    /**
     * 查询离退休老干部详情科室比例
     * @return
     */
    Map<String,Object> countByDepartmentLtxlgb();

    /**
     * 查询离退休老干部详情离退休时间比例
     * @return
     */
    Map<String,Object> countByPartyAgeLtxlgb();

    /**
     * 查询离退休老干部详情职称比例
     * @return
     */
    Map<String,Object> countByTitleLtxlgb();

    /**
     * 离退休老干部籍贯比例
     * @return
     */
    Map<String,Object> countByPlaceLtxlgb();

    /**
     * 离退休老干部身份比例
     * @return
     */
    Map<String,Object> countByIdentityLtxlgb();

    /**
     * 查询高知群体详情男女比例
     * @return
     */
    Map<String,Object> countBySexGzqt();

    /**
     * 查询高知群体详情学历比例
     * @return
     */
    Map<String,Object> countByEducationGzqt();

    /**
     * 查询高知群体年龄比例
     * @return
     */
    Map<String,Object> countByIdcardGzqt();

    /**
     * 查询高知群体详情科室比例
     * @return
     */
    Map<String,Object> countByDepartmentGzqt();

//    /**
//     * 查询高知群体详情工龄比例
//     * @return
//     */
//    Map<String,Object> countByPartyAgeGzqt();

    /**
     * 查询高知群体详情职称比例
     * @return
     */
    Map<String,Object> countByTitleGzqt();

    /**
     * 高知群体籍贯比例
     * @return
     */
    Map<String,Object> countByPlaceGzqt();

    /**
     * 高知群体身份比例
     * @return
     */
    Map<String,Object> countByIdentityGzqt();

    /**
     * 查询民主党派详情男女比例
     * @return
     */
    Map<String,Object> countBySexMzdp();

    /**
     * 查询民主党派详情学历比例
     * @return
     */
    Map<String,Object> countByEducationMzdp();

    /**
     * 查询民主党派年龄比例
     * @return
     */
    Map<String,Object> countByIdcardMzdp();

    /**
     * 查询民主党派详情科室比例
     * @return
     */
    Map<String,Object> countByDepartmentMzdp();

    /**
     * 查询民主党派详情工龄比例
     * @return
     */
    Map<String,Object> countByPartyAgeMzdp();

    /**
     * 查询民主党派详情职称比例
     * @return
     */
    Map<String,Object> countByTitleMzdp();

    /**
     * 民主党派籍贯比例
     * @return
     */
    Map<String,Object> countByPlaceMzdp();

    /**
     * 民主党派身份比例
     * @return
     */
    Map<String,Object> countByIdentityMzdp();



}
