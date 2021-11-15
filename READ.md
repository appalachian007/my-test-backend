## How to Run

1) Make sure maven install, java sdk (assuming java 8 will work, I am using Java 16)
2) Install MySQL server or other relational dabase, create database user myuser with password mypass. This user should have the privileges of select, delete, update, create.
3) DDL as below,
CREATE TABLE `oracle`.`task` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  `taskDate` VARCHAR(45) NULL,
  `status` VARCHAR(45) NULL DEFAULT 'Y',
  PRIMARY KEY (`id`));
 4) In command line, use mvn package to compile the project, run the application as below,
 
 java -jar target/my-test.0.0.1.SNAPSHOOT.jar server task.yml
 
 to run it locally, please switch connection url in task.yml to be 
 
 5) Use postman to the test function, see the file in repository local.test.postman_collection.json
 
## API Specification

1)Get all active tasks while task status is active
URI: /tasks
method: Get
Request: none
Response: 
[
    {
        "id": 5,
        "description": "a test a test",
        "taskDate": [
            2021,
            12,
            12
        ],
        "status": true
    },
    {
        "id": 2,
        "description": "another one",
        "taskDate": [
            2021,
            12,
            23
        ],
        "status": true
    }
]

2)Close the selected tasks
URI:/tasks
method: Put
Request params: id, exempl: id=1&id=2;
Response: status 200

3)Close the selected tasks
URI:/tasks
method: Delete
Request: list of id, exemple: [1, 2];
Response: status 200

4)Create task
URI: /tasks/task
method: Post
Requst: json body, exemple:{"description": "my task", "taskDate":"2021-12-12"}
Response: json body, example
	{
		"id": 12,
		"description": 
		"my task", 
		"taskDate":"2021-12-12",
		"status": true
	}
	
Other API not listed here since not used.
 
## Unit test
 
 Only covered the implementation for repository and resource.
 
## Deploy to Local Docker

1) run mvn package
2) make sure database connection url which is switched to the one for docker. Need to research this for docker configuration
3) build docker image, sudo docker build -f DockerFile -t application:mytest
4) run application in docker, sudo docker run -p 8080:8080 application:mytest
5) in my local slice, MySQL server is installed in docker
6) make sure it is using port 8080 since client is consuming the service through port 8080.

## Coverage report

1) if sonaqube is install, you can use sameple command to check code quality as below. I save one screenshot for my Sonarqube report.
	mvn clean verify sonar:sonar \
  -Dsonar.projectKey=localtesting \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=546fe0024e5da0d005922d5fe0c10af94effc909
 
 2) unit test report is under target folder in surefire-reports folder. You need to run unit test to get this report.
	
 
 