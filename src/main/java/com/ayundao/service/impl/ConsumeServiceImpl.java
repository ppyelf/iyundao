package com.ayundao.service.impl;

import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.Consume;
import com.ayundao.repository.ConsumeRepository;
import com.ayundao.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ConsumeServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 15:32
 * @Description: 实现 - 耗材预警
 * @Version: V1.0
 */
@Service
@Transactional
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private ConsumeRepository consumeRepository;

    @Override
    public List<Consume> saveAllConsume(List<Consume> list) {
        List<Consume> consumes = new ArrayList<>();
        for (Consume consume : list) {
            consume.setCreatedDate(new Date());
            consume.setLastModifiedDate(new Date());
            consume = consumeRepository.save(consume);
            consumes.add(consume);
        }
        return consumes;
    }

    @Override
    public List<Consume> findOrderByCreatedTime(int type) {
        switch (type) {
            case  0:
            return consumeRepository.findListByCreatedTimeOrderByWarn(
                    TimeUtils.convertTime(TimeUtils.getDayBegin(), TimeUtils.yyyyMMddHHmmss),
                    TimeUtils.convertTime(TimeUtils.getDayEnd(), TimeUtils.yyyyMMddHHmmss)
            );
            case  1:
            return consumeRepository.findListByCreatedTimeOrderByWarn(
                    TimeUtils.convertTime(TimeUtils.getBeginDayOfWeek(), TimeUtils.yyyyMMddHHmmss),
                    TimeUtils.convertTime(TimeUtils.getEndDayOfWeek(), TimeUtils.yyyyMMddHHmmss)
            );
            case  2:
            return consumeRepository.findListByCreatedTimeOrderByWarn(
                    TimeUtils.convertTime(TimeUtils.getBeginDayOfMonth(), TimeUtils.yyyyMMddHHmmss),
                    TimeUtils.convertTime(TimeUtils.getEndDayOfMonth(), TimeUtils.yyyyMMddHHmmss)
            );
            case  3:
            return consumeRepository.findListByCreatedTimeOrderByWarn(
                    TimeUtils.convertTime(TimeUtils.getBeginDayOfYear(), TimeUtils.yyyyMMddHHmmss),
                    TimeUtils.convertTime(TimeUtils.getEndDayOfYear(), TimeUtils.yyyyMMddHHmmss)
            );
        }
        return null;
    }
}
