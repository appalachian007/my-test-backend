package com.oracle.test.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "task")

@NamedQuery(name = "com.oracle.test.Task.findAll", query = "SELECT t FROM Task t where t.status is true order by t.id desc")

public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private LocalDate taskDate;
	@Type(type = "yes_no")
	private Boolean status;

	public Task() {
		super();
	}

	public Task(Long id) {
		super();
		this.id = id;
	}

	public Task(String description, LocalDate taskDate) {
		super();
		this.description = description;
		this.taskDate = taskDate;
	}

	public Task(Long id, String description, LocalDate taskDate, Boolean status) {
		super();
		this.id = id;
		this.description = description;
		this.taskDate = taskDate;
		this.status = status;
	}

	public Task(String description, LocalDate taskDate, Boolean status) {
		super();
		this.description = description;
		this.taskDate = taskDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getTaskDate() {
		return taskDate;
	}

	public Boolean getStatus() {

		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTaskDate(LocalDate taskDate) {
		this.taskDate = taskDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, taskDate, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(taskDate, other.taskDate) && Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", description=" + description + ", taskDate=" + taskDate + ", status=" + status
				+ "]";
	}

}
