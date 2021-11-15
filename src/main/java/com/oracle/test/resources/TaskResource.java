package com.oracle.test.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.oracle.test.entity.Task;
import com.oracle.test.repository.TaskRepository;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.PathParam;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
	
	private final TaskRepository taskRepository;
	
	public TaskResource(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}

	@GET
	@Path("/task/{id}")
	@UnitOfWork
	public Task getTask(@PathParam(value = "id") Long id) {
		Optional<Task> optioinalTask = taskRepository.findById(id);
		if(optioinalTask.isPresent()) {
			return optioinalTask.get();
		}
		return null;
	}
	
	@DELETE
	@Path("/task/{id}")
	@UnitOfWork
	public Response deleteTask(@PathParam(value = "id") Long id) {
		
		try {
			taskRepository.delete(new Task(id));
			return Response.status(200).build();
		}catch(Exception e) {
			return Response.status(500).build();
		}
		
	}	
	
	@PUT
	@Path("/task/{id}")
	@UnitOfWork
	public Response updateTask(@PathParam(value = "id") Long id) {
		
		try {
			taskRepository.update(new Task(id));
			return Response.status(200).build();
		}catch(Exception e) {
			return Response.status(500).build();
		}
		
	}
	
	@POST
	@Path("/task")
	@UnitOfWork
	public Task addTask(Task task) {
		task.setStatus(true);
		return taskRepository.create(task);
	}
	
	@GET
	@UnitOfWork
	public List<Task> getAllTasks() {
		return taskRepository.findActiveTasks();
	}
	@DELETE
	@UnitOfWork
	public Response deleteTasks(@QueryParam("id") List<Long> ids) {
		try {
			taskRepository.deleteTasks(ids);
			return Response.status(200).build();
		}catch(Exception e) {
			//should log exception
			return Response.status(500).build();
		}
		
	}
	
	@PUT
	@UnitOfWork
	public Response closeTasks(List<Long> ids) {
		
		try {
			taskRepository.closeTasks(ids);
			return Response.status(200).build();
		}catch(Exception e) {
			//should log exception
			return Response.status(500).build();
		}
		
	}
	
}
