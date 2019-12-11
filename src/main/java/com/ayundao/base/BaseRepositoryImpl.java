package com.ayundao.base;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.BaseRepository;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * @ClassName: BaseRepositoryImpl
 * @project: IYunDao
 * @author: 念
 * @Date: 2019/6/5 11:37
 * @Description: 基类
 * @Version: V1.2
 */
@Repository
public class BaseRepositoryImpl<T extends BaseEntity<String>, ID> implements BaseRepository<T,ID> {

    /**
     * 属性分隔符
     */
    private static final String ATTRIBUTE_SEPARATOR = ".";

    private static final String ATTRIBUTE_TRANSFER = "\\u002E";

    @PersistenceContext
    private EntityManager em;

    private JpaEntityInformation<T, ID> entityInformation;

    private PersistenceProvider provider;
    @Nullable
    private CrudMethodMetadata metadata;

    private Class<T> entityClass;

    public BaseRepositoryImpl() {
    }

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        Assert.notNull(entityInformation);
        Assert.notNull(entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
        this.entityClass = entityInformation.getJavaType();
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl(Class<T> entityClass, EntityManager entityManager) {
        Assert.notNull(entityClass);
        Assert.notNull(entityManager);
        this.em = entityManager;
    }

    private Class<T> getEntityClass() {
        return entityInformation.getJavaType();
    }

    /**
     * 获取Path
     *
     * @param path
     *            Path
     * @param attributeName
     *            属性名称
     * @return Path
     */
    @SuppressWarnings("unchecked")
    private <X> Path<X> getPath(Path<?> path, String attributeName) {
        if (path == null || StringUtils.isEmpty(attributeName)) {
            return (Path<X>) path;
        }
        return path.get(attributeName);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public void delete(T entity) {
        Assert.notNull(entity);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> S save(S s) {
        Assert.notNull(s);
        s = (S) ClassUtils.parseEntity(s);
        s.setLastModifiedDate(new Date());
        if (entityInformation.isNew(s)) {
            s.setCreatedDate(new Date());
            em.persist(s);
            return s;
        } else {
            return em.merge(s);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities);
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        List<T> result = findAll();
        for (T t : result) {
            delete(t);
        }
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        query.from(entityClass);
        TypedQuery<T> typedQuery = em.createQuery(query);
        List<T> list = typedQuery.getResultList();
        return CollectionUtils.isEmpty(list)
                ? new ArrayList<>()
                : list;
    }

    @Override
    public List<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public org.springframework.data.domain.Page<T> findAll(org.springframework.data.domain.Pageable pageable) {
        return null;
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean exists(ID id) {
        return existsById(id);
    }

    @Override
    public void deleteById(ID id) {
        T t = find(id);
        if (t != null) {
            delete(t);
        }
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities);
        List<S> list = new ArrayList<>();
        for (S entity : entities) {
            list.add(save(entity));
        }
        return list;
    }

    @Override
    public void flush() {
        em.flush();
    }

    public void clear() {
        em.clear();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(ID id) {
        T t = find(id);
        return t == null ? false : true;
    }

    @Override
    public T find(ID id) {
        if (id == null) {
            return null;
        }
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findByIds(Iterable<ID> ids) {
        if (ids == null) {
            return new ArrayList<>();
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        Path<Object> path = root.get("id");
        CriteriaBuilder.In<Object> in = builder.in(path);
        for (ID id : ids) {
            in.value(id);
        }
        Predicate condition = builder.and(in);
        query.where(condition);
        TypedQuery<T> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public List<T> findByIds(String[] ids) {
        if (ids == null) {
            return new ArrayList<>();
        }

        if (ids.length == 0) {
            return new ArrayList<>();
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        Path<Object> path = root.get("id");
        CriteriaBuilder.In<Object> in = builder.in(path);
        for (String id : ids) {
            in.value(id);
        }
        Predicate condition = builder.and(in);
        query.where(condition);
        TypedQuery<T> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Page<T> findPage(Pageable pageable) {
        Assert.notNull(pageable);

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(entityClass);
        Root<T> root = findRoot(query, query.getResultType());

        List<Predicate> predicates = new LinkedList<>();
        String[] searchKey = pageable.getSearchKey();
        String[] searchValue = pageable.getSearchValue();
        if (searchKey != null && searchValue != null && searchKey.length > 0 && searchValue.length > 0) {
            for (int i = 0; i < searchKey.length; i++) {
                String key = searchKey[i];
                String val = searchValue[i];
                if (key.contains(ATTRIBUTE_SEPARATOR)) {
                    predicates.add(getJoin(builder, root, key, val));
                } else {
                    predicates.add(builder.like(getPath(root, key), "%" + val + "%"));
                }
            }
        }
        Predicate predicate = query.getRestriction() == null
                ? builder.conjunction()
                : query.getRestriction();
        for (Predicate p : predicates) {
            predicate = builder.and(p);
        }
        List<javax.persistence.criteria.Order> orderList = toOrders(root, pageable.getOrders());
        if (pageable.getOrder() != null) {
            orderList.add(pageable.getOrder().getDirection().equals(Order.Direction.asc)
                    ? builder.asc(getPath(root, pageable.getOrder().getProperty()))
                    : builder.desc(getPath(root, pageable.getOrder().getProperty())));
        }
        if (CollectionUtils.isEmpty(orderList)) {
            orderList.add(builder.asc(getPath(root, "createdDate")));
        }

        query.where(predicate);
        query.orderBy(orderList);
        long total = em.createQuery(query).getResultList().size();
        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        return new Page<T>(typedQuery.getResultList(), total, pageable);
    }

    @Override
    public Page<T> selectPage(String sql, String countSql, Pageable pageable) {
        sql = sql.contains("limit")
                ? sql.substring(0, sql.lastIndexOf("limit"))
                : sql;
        Query query =  em.createNativeQuery(sql);
        query.setFirstResult(pageable.getPageNumber());
        query.setMaxResults(pageable.getPageSize());
        long total = Long.parseLong(em.createNativeQuery(countSql).getSingleResult()+"");
        return new Page<T>(query.getResultList(), total, pageable);
    }

    /**
     * 抓取左外
     * @param root
     * @param key
     * @param val
     */
    private Predicate getJoin(CriteriaBuilder builder, Root<T> root, String key, String val) {
        String[] k = key.split("\\u002E");
        return builder.like(root.join(k[0]).get(k[1]).as(String.class), "%" + val + "%");
    }

    /**
     * 转换为Order
     *
     * @param root
     *            Root
     * @param orders
     *            排序
     * @return Order
     */
    protected List<javax.persistence.criteria.Order> toOrders(Root<T> root, List<Order> orders) {
        List<javax.persistence.criteria.Order> list = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        if (root == null || CollectionUtils.isEmpty(orders)) {
            return list;
        }

        for (Order order : orders) {
            if (order == null) {
                continue;
            }
            String property = order.getProperty();
            Order.Direction direction = order.getDirection() != null
                    ? order.getDirection()
                    : Order.Direction.asc;
            Path<?> path = getPath(root, property);
            if (path == null || direction == null) {
                continue;
            }
            orderSwitch(direction, list, builder, path);
        }
        return list;
    }

    private void orderSwitch(Order.Direction direction, List<javax.persistence.criteria.Order> list, CriteriaBuilder cb, Path<?> path) {
        switch (direction) {
            case asc :
                list.add(cb.asc(path));
                break;
            case desc :
                list.add(cb.desc(path));
                break;
        }
    }

    /**
     * 查找Root
     *
     * @param query
     *            查询条件
     * @param clazz
     *            类型
     * @return Root
     */
    private Root<T> findRoot(CriteriaQuery<?> query, Class<?> clazz) {
        Assert.notNull(query);
        Assert.notNull(clazz);

        for (Root<?> root : query.getRoots()) {
            if (clazz.equals(root.getJavaType())) {
                return (Root<T>) root.as(clazz);
            }
        }
        return (Root<T>) query.from(clazz);
    }

    private T find(String name, String value, boolean ignoreCase, LockModeType lockModeType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = findRoot(cq, entityClass);
        cq.select(root);
        cq.where();
        if (ignoreCase) {
            cq.where(cb.equal(cb.upper(root.get(name)), StringUtils.lowerCase(value)));
        }else {
            cq.where(cb.equal(root.get(name), value));
        }
        TypedQuery<T> query = em.createQuery(cq);
        if (lockModeType != null) {
            query.setLockMode(lockModeType);
        }
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        S s = save(entity);
        em.flush();
        return s;
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public T getOne(ID id) {
        return null;
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends T> org.springframework.data.domain.Page<S> findAll(Example<S> example, org.springframework.data.domain.Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
        this.metadata = crudMethodMetadata;
    }

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return Optional.empty();
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return null;
    }

    @Override
    public org.springframework.data.domain.Page<T> findAll(Specification<T> spec, org.springframework.data.domain.Pageable pageable) {
        return null;
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return null;
    }

    @Override
    public long count(Specification<T> spec) {
        return 0;
    }
}
