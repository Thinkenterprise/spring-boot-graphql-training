## Exercise Chapter 5 GraphQL Web API Application  

## Include GraphQL starters 

Task 1: Include the correct starters via the pom.xml so that they can provide the GraphQL Web API to the GraphQL client via synchronous HTTP access.

```
<dependencies>
	<dependency>
		//TODO
	</dependency>
	<dependency>
		//TODO
	</dependency>
</dependencies>
```

<details>
	<summary>Complete snippet</summary>.
	
```
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-graphql</artifactId>
	</dependency>
</dependencies>
```
</details>

## Configure GraphQL Endpoint  
Task 2: Configure the GraphQL Web API application via Spring properties so that the GraphQL Web API can be accessed via the URL localhost:8080/airline. 

```
spring:
  graphql:
    //TODO
    graphiql:
      enabled: true
      //TODO
```

<details>
	<summary> Complete snippet</summary>.
	
```
spring:
  graphql:
    path: /airline
    graphiql:
      enabled: true
      path: /airline/graphiql
```
</details>

## Configure GraphQL schema location  
Task 3: Configure the GraphQL API application via Spring properties to use the schema file airline.graphqls under resource/graphql. 

```
spring:
  graphql:
    schema:
      //TODO
      printer:
        enabled: true
```

<details>
	<summary> Complete snippet</summary>.
	
```
spring:
  graphql:
    schema:
      locations: classpath:graphql/
      printer:
        enabled: true
```
</details>

## Start GraphiQL 
Task 4: Start the GraphQL Web API application and call GraphiQL from the URL localhost:8080/airline/graphiql in the browser.

```
localhost:8080/airline/graphiql 
```

## Test GraphQL API application 

**Attention minor correction**. The book uses a query that does not work. The query given here works. 


Task 5: Submit the following GraphQL query. 

```
query helloWorld {
  helloWorld
}
```
