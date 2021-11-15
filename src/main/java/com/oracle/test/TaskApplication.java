package com.oracle.test;

import com.oracle.test.entity.Task;
import com.oracle.test.repository.TaskRepository;
import com.oracle.test.resources.TaskResource;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TaskApplication extends Application<TaskConfiguration> {

	public static void main(String[] args) throws Exception {
			new TaskApplication().run(args);
	}
	@Override
    public String getName() {
        return "task";
    }
	private final HibernateBundle<TaskConfiguration> hibernateBundle = new HibernateBundle<TaskConfiguration>(Task.class) {
	    @Override
	    public DataSourceFactory getDataSourceFactory(TaskConfiguration configuration) {
	        return configuration.getDataSourceFactory();
	    }

	};
    @Override
    public void initialize(Bootstrap<TaskConfiguration> bootstrap) {
    	bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(TaskConfiguration configuration,
                    Environment environment) {
    	
    	final TaskRepository taskRepository = new TaskRepository(hibernateBundle.getSessionFactory());
    	final TaskResource resource = new TaskResource(taskRepository);
    	environment.jersey().register(resource);
    }

}
