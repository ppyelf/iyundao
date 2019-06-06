package com.ayundao.base;

import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @ClassName: BaseRepositoryImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 11:37
 * @Description: 基类 - 仓库实现
 * @Version: V1.0
 */
@Repository
@Transactional(readOnly = true)
public class BaseRepositoryImpl<T, ID> implements BaseReposiory<T, ID> {

    private @Nullable CrudMethodMetadata metadata;

    public <S extends T> S save(S s) {
        return null;
    }

    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    public <S extends T> S find(ID id) {
        return null;
    }

    public boolean existsById(ID id) {
        return false;
    }

    public Iterable<T> findAll() {
        return null;
    }

    public Iterable<T> findAllById(Iterable<ID> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void deleteById(ID id) {

    }

    public void delete(T t) {

    }

    public void deleteAll(Iterable<? extends T> iterable) {

    }

    public void deleteAll() {

    }
}
