package com.ayundao.base;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @ClassName: BaseRepository
 * @project: IYunDao
 * @author: 念
 * @Date: 2019/6/5 11:30
 * @Description: 基类 - 仓库
 * @Version: V1.1
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepositoryImplementation<T, ID> {

    T find(ID id);

    List<T> findByIds(Iterable<ID> ids);

    List<T> findByIds(String[] ids);

    Page<T> findPage(Pageable pageable);

    Page<T> selectPage(String sql, String countSql, Pageable pageable);

    @Override
    <S extends T> S save(S entity);

    @Override
    <S extends T> List<S> saveAll(Iterable<S> entities);

    @Override
    <S extends T> S saveAndFlush(S entity);

    @Override
    void delete(T s);

    @Override
    void deleteAll(Iterable<? extends T> entities);

    /**
     * 统计
     * @return
     */
    @Override
    long count();

    boolean exists(ID id);
}
