package com.ayundao.repository;

import com.ayundao.entity.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AttendanceRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 13:52
 * @Description: 仓库 - 出勤时间
 * @Version: V1.0
 */
@Repository
public interface AttendanceRepository extends CrudRepository<Attendance, String> {

    //根据活动ID查找集合信息
    @Query("select a from Attendance a where a.activity.id = ?1")
    List<Attendance> findByActivityId(String id);

    //根据IDS获取集合信息
    @Query("select a from Attendance a where a.id in (?1)")
    List<Attendance> findByIds(String[] attendanceIds);
}
