package com.oracle.test.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import com.oracle.test.entity.Task;
import com.oracle.test.repository.TaskRepository;
import com.oracle.test.resources.TaskResource;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;

@ExtendWith(DropwizardExtensionsSupport.class)
class TaskResourceTest {

	private static final TaskRepository taskRepository = mock(TaskRepository.class);

	public final ResourceExtension RESOURCES = ResourceExtension.builder().addResource(new TaskResource(taskRepository))
			.build();
	private ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);

	private Task task;

	@BeforeEach
	void setUp() {
		task = new Task(1l, "this is a test", LocalDate.now(), true);

	}

	@AfterEach
	void tearDown() {
		reset(taskRepository);
	}

	@Test
	void addTaskTest() {
		when(taskRepository.create(any(Task.class))).thenReturn(task);
		final Response response = RESOURCES.target("/tasks/task").request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(task, MediaType.APPLICATION_JSON_TYPE));

		Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
		verify(taskRepository).create(taskCaptor.capture());
		Assertions.assertThat(taskCaptor.getValue()).isEqualTo(task);
	}

	@Test
	void deleteTasksTest() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1l);
		doNothing().when(taskRepository).deleteTasks(ids);
		final Response response = RESOURCES.target("/tasks").queryParam("id", 1l).request().build("DELETE").invoke();
		verify(taskRepository).deleteTasks(ids);
		Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);

	}

	@Test
	void closeTasksTest() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1l);
		doNothing().when(taskRepository).closeTasks(ids);
		final Response response = RESOURCES.target("/tasks").request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.entity(ids, MediaType.APPLICATION_JSON_TYPE));
		verify(taskRepository).closeTasks(ids);
		Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);

	}

}
