**Solution**

The solution for the given task is based on reading the data files and loading the data files to the memory. It can be
done with different tools and APIs. My approach was based on using `Spring Batch`. Each file is read and written to the 
related data structure formed a step. I had 4 steps in this case which forms a Job carried out at the loading of the 
application. 

A TreeSet is used to store data in the memory. Each Dao implementation has a data field which stores data in the memory.

Swagger UI has been configured in the Spring Boot application to call the services efficiently.

It is assumed that the data for endpoints should have `'application/json'` content-type.

**Tests**
The implemented classes were _completely_ tested. It means that each layer has been tested with an appropriate test
approach e.g. mocking tests, integration tests, acceptance tests. 
