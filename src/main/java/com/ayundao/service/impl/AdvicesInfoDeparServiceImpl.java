package com.ayundao.service.impl;

import com.ayundao.repository.AdvicesInfoDeparRepository;
import com.ayundao.service.AdvicesInfoDeparService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: AdvicesInfoDeparServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/5
 * @Description: 实现 - 消息与部门组织结构表
 * @Version: V1.0
 */
@Service
@Transactional
public class AdvicesInfoDeparServiceImpl implements AdvicesInfoDeparService{

    @Autowired
    AdvicesInfoDeparRepository advicesInfoDeparRepository;


}
