package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserFileRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/5 15:08
 * @Description: 仓库 - 用户资源
 * @Version: V1.0
 */
@Repository
public interface UserFileRepository extends BaseRepository<UserFile, String> {

    //获取code的最大值
    @Query(value = "SELECT IFNULL(max(uf.`CODE`), 0) from t_user_file uf", nativeQuery = true)
    int getLastCode();

    //获取用户已分享和个人资料列表
    @Query("select uf from UserFile uf where uf.user.id = ?1")
    List<UserFile> findByUserIdAndStatusIsNotWaiting(String id);

    //查询待审核的资源列表
    List<UserFile> findByStatus(UserFile.STATUS waiting);
}
