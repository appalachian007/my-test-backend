package com.oracle.test.respository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.oracle.test.entity.Task;
import com.oracle.test.repository.TaskRepository;

import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
class TaskRepositoryTest {

    public DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
        .addEntityClass(Task.class)
        .build();

    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() throws Exception {
    	taskRepository = new TaskRepository(daoTestRule.getSessionFactory());
    }
    
    @Test
    void createTaskTest() throws ParseException {
        final Task task = daoTestRule.inTransaction(() -> taskRepository.create(new Task("description", LocalDate.now(), true)));
        assertThat(task.getId()).isPositive();
        assertThat(task.getDescription()).isEqualTo("description");
        assertTrue(task.getStatus());
        assertThat(task.getTaskDate().getYear()).isEqualTo(LocalDate.now().getYear());
        assertThat(taskRepository.findById(task.getId())).isEqualTo(Optional.of(task));
    }
    
    @Test
    void findActiveTasksTest() {
        daoTestRule.inTransaction(() -> {
        	taskRepository.create(new Task("description1", LocalDate.now(), true));
        	taskRepository.create(new Task("description2", LocalDate.now(), false));
        	taskRepository.create(new Task("description3", LocalDate.now(), true));
        });

        final List<Task> tasks = taskRepository.findActiveTasks();
        assertThat(tasks).extracting("description").containsOnly("description1", "description3");
        assertThat(tasks).extracting("status").containsOnly(true);
        assertThat(tasks).extracting("taskDate").containsOnly(LocalDate.now());
    }
    
    @Test
    void closeTasksTest() {
    	List<Long> ids = new ArrayList<Long>();
        daoTestRule.inTransaction(() -> {
        	Task task1 = taskRepository.create(new Task("description1", LocalDate.now(), true));
        	Task task2 = taskRepository.create(new Task("description2", LocalDate.now(), true));
        	Task task3 = taskRepository.create(new Task("description3", LocalDate.now(), true));
        	ids.add(task1.getId());
        	ids.add(task2.getId());
        	ids.add(task3.getId());
        	Assertions.assertDoesNotThrow(() ->taskRepository.closeTasks(ids));
        });     
        
    }
    
    @Test
    void deleteTasksTest() {
    	List<Long> ids = new ArrayList<Long>();
        daoTestRule.inTransaction(() -> {
        	Task task1 = taskRepository.create(new Task("description1", LocalDate.now(), true));
        	Task task2 = taskRepository.create(new Task("description2", LocalDate.now(), true));
        	Task task3 = taskRepository.create(new Task("description3", LocalDate.now(), true));
        	ids.add(task1.getId());
        	ids.add(task2.getId());
        	ids.add(task3.getId());
        	Assertions.assertDoesNotThrow(() ->taskRepository.deleteTasks(ids));
        });     
        
    }
}