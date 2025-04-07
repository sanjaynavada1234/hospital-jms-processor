# hospital-jms-processor
A Spring Boot-based messaging system for a Hospital Management application using JMS and ActiveMQ. Implements queue-based communication with support for dead-letter queue handling and message selector

This project is structured following the SOLID principles of object-oriented design to ensure maintainability, scalability, and clean architecture:
The system heavily leverages interfaces to decouple components (e.g., listeners and services), making it easy to extend and test individual layers independently.

How to Run the Project Locally (Using Maven):

Follow the steps below to set up and run the Hospital JMS Processor project locally using Maven.

Pre-requisites:
Make sure the following are installed on your machine:
- Java 17+
- Maven 3.8+
- Git

Steps to set up the project:

- Open Command Prompt and navigate to any folder and run the below commands
- git clone https://github.com/sanjaynavada1234/hospital-jms-processor.git
- cd hospital-jms-processor
- mvn clean install
- mvn spring-boot:run

Test The API's:

- Use Postman or any other tool

Create Group: POST /groups/create
Delete Group: DELETE /groups/delete

POST request: http://localhost:8080/groups/create

Sample JSON payload:

{
  "groupId": "G123",
  "parentGroupId": "PG456"
}

On successful delivery of the Create and Delete messages, a response should be available in POST Man
Validate the application logs to verify if the listener processed the message successfully

DELETE request: http://localhost:8080/groups/delete

Sample JSON payload:

{
    "groupId": "H1234",
    "parentGroupId" : "H123"
}
