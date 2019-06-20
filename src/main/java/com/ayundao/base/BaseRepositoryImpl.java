package com.ayundao.base;

import org.apache.commons.lang.BooleanUtils;
import org.apache.lucene.analysis.bg.BulgarianAnalyzer;
import org.apache.shiro.util.Assert;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: BaseRepositoryImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 11:37
 * @Description: 基类
 * @Version: V1.0
 */
@Repository
@Transactional(readOnly = true)
public class BaseRepositoryImpl<T extends BaseEntity<String>, ID> implements BaseRepository<T,ID> {

    /**
     * 属性分隔符
     */
    private static final String ATTRIBUTE_SEPARATOR = ".";

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
    public <S extends T> S save(S s) {
        Assert.notNull(s);
        if (entityInformation.isNew(s)) {
            em.persist(s);
            return s;
        } else {
            return em.merge(s);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public void delete(T entity) {
        Assert.notNull(entity);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities);
        for (T entity : entities) {
            em.remove(entity);
        }
    }

    @Override
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
        for (S entity : list) {
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
    public List<T> findList() {
        return findList(new Pageable());
    }

    @Override
    public Page<T> findPage() {
        return findPage(new Pageable());
    }

    @Override
    public List<T> findList(Pageable pageable) {
        Assert.notNull(pageable);

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);

        Predicate predicate = query.getRestriction() != null
                ? query.getRestriction()
                : builder.conjunction();
        String searchProperty = pageable.getSearchProperty();
        String searchValue = pageable.getSearchValue();
        if (StringUtils.isNotEmpty(searchProperty) && StringUtils.isNotEmpty(searchValue)) {
            Path<String> searchPath = getPath(root, searchProperty);
            if (searchPath != null) {
                predicate = builder.and(predicate, builder.like(searchPath, "%" + searchValue + "%"));
            }
        }
        List<javax.persistence.criteria.Order> orderList = toOrders(root, pageable.getOrders());
        if (CollectionUtils.isEmpty(orderList)) {
            orderList.add(builder.asc(getPath(root, "createdDate")));
        }

        query.where(predicate);
        query.orderBy(orderList);
        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber());
        typedQuery.setMaxResults(pageable.getPageSize());
        return typedQuery.getResultList();
    }

    @Override
    public Page<T> findPage(Pageable pageable) {
        Assert.notNull(pageable);

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(entityClass);
        Root<T> root = findRoot(query, query.getResultType());

        Predicate predicate = query.getRestriction() == null
                ? builder.conjunction()
                : query.getRestriction();
        String searchProperty = pageable.getSearchProperty();
        String searchValue = pageable.getSearchValue();
        if (StringUtils.isNotEmpty(searchProperty) && StringUtils.isNotEmpty(searchValue)) {
            Path<String> searchPath = getPath(root, searchProperty);
            if (searchPath != null) {
                predicate = builder.and(predicate, builder.like(searchPath, "%" + searchValue + "%"));
            }
        }
        List<javax.persistence.criteria.Order> orderList = toOrders(root, pageable.getOrders());
        if (CollectionUtils.isEmpty(orderList)) {
            orderList.add(builder.asc(getPath(root, "createdDate")));
        }

        query.where(predicate);
        query.orderBy(orderList);
        long total = count(query, pageable);
        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber());
        typedQuery.setMaxResults(pageable.getPageSize());
        return new Page<T>(typedQuery.getResultList(), total, new Pageable());
    }

    private long count(CriteriaQuery<T> query, Pageable pageable) {
        Assert.notNull(query);

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        String searchProperty = pageable.getSearchProperty();
        String searchValue = pageable.getSearchValue();
        Predicate predicate = builder.conjunction();
        if (StringUtils.isNotEmpty(searchProperty) && StringUtils.isNotEmpty(searchValue)) {
            Path<String> searchPath = getPath(countRoot, searchProperty);
            if (searchPath != null) {
                predicate = builder.and(predicate, builder.like(searchPath, "%" + searchValue + "%"));
            }
        }
        countQuery.where(predicate);
        if (countQuery.isDistinct()) {
            countQuery.select(builder.countDistinct(countRoot));
        } else {
            countQuery.select(builder.count(countRoot));
        }
        return em.createQuery(countQuery).getSingleResult();
    }


	/**
	 * 拷贝Fetch
	 *
	 * @param from
	 *            源
	 * @param to
	 *            目标
	 */
	private void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) {
		Assert.notNull(from);
		Assert.notNull(to);

		for (Fetch<?, ?> fromFetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fromFetch.getAttribute().getName());
			copyFetches(fromFetch, toFetch);
		}
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
            Order.Direction direction = order.getDirection();
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
    @Transactional
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
