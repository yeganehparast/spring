## The task

Create a Spring Boot service implementation which exposes the following REST endpoints 

- Retrieve a customer for a given customer id 
- Retrieve an account for a given account id
- Retrieve a transaction for a given transaction id
- Retrieve a list of transactions for a given account id in reverse time order.

Access to all endpoints should only be permitted if the request has a header with the name "my-token" and the value is contained within the whitelist file provided.

CSV files have been provided which contain example data and this data should be served by your REST APIs.  You can use whatever dependencies you like but please use Maven or Gradle build configuration.

