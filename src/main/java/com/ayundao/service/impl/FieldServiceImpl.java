package com.ayundao.service.impl;

import com.ayundao.entity.Field;
import com.ayundao.repository.FieldRepository;
import com.ayundao.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: FieldServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 2:40
 * @Description: 实现 - 字段
 * @Version: V1.0
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    @Override
    public List<Field> findByPageId(String pageId) {
        return fieldRepository.findByPageId(pageId);
    }
}
