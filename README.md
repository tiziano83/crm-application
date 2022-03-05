
# CRM Service API

This is a REST API project that simulate a simple CRM Application


## Getting Started
This project requires JAVA JDK11 and Maven.

Clone this project in your favourite IDE, build and run!

This project is a Spring Boot application and use in-memory H2 DB, but you can use your favourite DBMS(for this option you have to import your db driver dependency in pom.xml and change jdbc connection string in application.properties file).

Authentication method is Basic Auth.

#### At project startup two default users are created:
| username | password     | role |
|----------| -------------|------|
| admin    |admin         |ADMIN |
|  user    |user          |ROLE  |






## API Reference


please refer to the link below:

https://documenter.getpostman.com/view/2290411/UVkvJYBz

## Appendix

Looking at CustomWebSecurityConfigurerAdapter class you can find, under configure method, a series of Spring native function to prevent XSS,CORS and CSRF attack.

This project is also SQL injection-safe, because are used only JPA repositories without manual query and/or parameter concatenation.


