package com.ayundao.base;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @ClassName: BaseRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 11:30
 * @Description: 基类 - 仓库
 * @Version: V1.0
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepositoryImplementation<T, ID> {

    T find(ID id);

    List<T> findByIds(Iterable<ID> ids);

    List<T> findByIds(String[] ids);

    List<T> findList();

    Page<T> findPage();

    List<T> findList(Pageable pageable);

    Page<T> findPage(Pageable pageable);

    <S extends T> S save(S entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    <S extends T> S saveAndFlush(S entity);

    void delete(T s);

    void deleteAll(Iterable<? extends T> entities);

    long count();

    boolean exists(ID id);
}
