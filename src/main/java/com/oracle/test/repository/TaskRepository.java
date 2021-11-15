package com.oracle.test.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;

import com.oracle.test.entity.Task;

import io.dropwizard.hibernate.AbstractDAO;

public class TaskRepository extends AbstractDAO<Task> {

	public TaskRepository(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<Task> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

	public Task create(Task task) {
		return persist(task);
	}
	
	public void delete(Task task) {
		currentSession().delete(task);
	}
	
	public void deleteTasks(List<Long> ids) {
		CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		CriteriaDelete<Task> cd = cb.createCriteriaDelete(Task.class);
		Root<Task> taskRoot = cd.from(Task.class);
		cd.where(taskRoot.get("id").in(ids));
		currentSession().createQuery(cd).executeUpdate();
	}
	
	public void update(Task task) {				
		currentSession().update(task);	
	}
	public void closeTasks(List<Long> ids) {
		CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		CriteriaUpdate<Task> cu = cb.createCriteriaUpdate(Task.class);
		Root<Task> taskRoot = cu.from(Task.class);
		cu.set(taskRoot.get("status"), false);
		cu.where(taskRoot.get("id").in(ids));
		currentSession().createQuery(cu).executeUpdate();
	}

	public List<Task> findActiveTasks() {
		return list(namedTypedQuery("com.oracle.test.Task.findAll"));
	}

}
