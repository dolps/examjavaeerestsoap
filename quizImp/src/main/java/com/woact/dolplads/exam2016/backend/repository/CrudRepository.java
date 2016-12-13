package com.woact.dolplads.exam2016.backend.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 * Does the crud operations against the DB
 */
public abstract class CrudRepository<E, T> implements CRUD<E, T> {
    @PersistenceContext
    EntityManager entityManager;
    private Class<T> entityClass;

    CrudRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(@Valid @NotNull T entity) {
        entityManager.persist(entity);

        return entity;
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(update(entity));
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public T findById(E id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return queryForAll().getResultList();
    }

    private TypedQuery<T> queryForAll() {
        CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
        return entityManager.createQuery(criteriaQuery.select(criteriaQuery.from(entityClass)));
    }
}
