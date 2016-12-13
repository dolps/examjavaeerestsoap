package no.exam.dolplads.entities.service;

import no.exam.dolplads.entities.repository.CrudRepository;

import java.util.List;

/**
 * Created by dolplads on 01/12/2016.
 */
public class CrudEJB<E, T> {
    private CrudRepository<E, T> crudRepository;

    public CrudEJB() {
    }

    public CrudEJB(CrudRepository<E, T> crudRepository) {
        this.crudRepository = crudRepository;
    }

    public T create(T entity) {
        return crudRepository.save(entity);
    }

    public void remove(T entity) {
        crudRepository.remove(entity);
    }

    public T findById(E id) {
        return crudRepository.findById(id);
    }

    public T update(T entity) {
        return crudRepository.update(entity);
    }

    public List<T> findAll() {
        return crudRepository.findAll();
    }
}
