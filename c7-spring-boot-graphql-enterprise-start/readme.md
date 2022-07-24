# Exercise chapter 7 GraphQL Enterprise  

## Using Repositories 
Task 1: In the GraphQL RouteController, use the existing repositories to access the database. 

Previously, **Mocks** were used as the data source in the GraphQL Controllers. Replace them with repositories. 
The following figure shows the use of the ``RouteRepositories`` for the ``RouteController`` and the ``routes`` method. 


```
@Controller
public class RouteController {
    
    @Autowired
    private RouteRepository routeRepository;
    
    @QueryMapping
    public List<Route> routes() {
        return routeRepository.findAll();
    }
       
```

# Exceptions
Task 2: An error handling shall be introduced for the GraphQL root field route. Whenever no matching route for the flight number is found, a GraphQL error with a matching error message shall be returned. The GraphQL error should also contain the location where the error occurred. 

## Throw Exception 
Implement the throwing of the exception in the ``RouteController``. The following figure shows how to throw the ``RouteException`` exception if the ``route`` is not found using the ``flight number``. 

```
@QueryMapping
public Route route(String flightNumber) {
	Optional<Route> route = routeRepository.findByFlightNumber(flightNumber);	
   	if(route.isEmpty()) 
    		//TODO
    	else 
    		return route.get();	
    }
```

<details>
	<summary>Compelte snippet</summary>.
	
```
@QueryMapping
public Route route(String flightNumber) {
	Optional<route> route = routeRepository.findByFlightNumber(flightNumber);	
   	if(route.isEmpty()) 
    		throw new RouteException("Hello this is a Route Exception");
    	else 
    		return route.get();	
    }
```
</details>

## Implement exception resolver 
Create a custom ``DataFetcherExceptionResolver`` that catches the ``RouteException`` exception and generates an appropriate GraphQL Error. 

```
@Component
public class RouteExceptionResolver implements DataFetcherExceptionResolver {

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {

        if (exception instanceof RouteException) {
		//TODO
        }
        return Mono.empty();
    }
}
```
	
<details>
	<summary>Complete snippet</summary>.
	
```
@Component
public class RouteExceptionResolver implements DataFetcherExceptionResolver {

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {

        if (exception instanceof RouteException) {
            return Mono.fromCallable(() -> Arrays.asList(
                    GraphqlErrorBuilder.newError(environment)
                            .errorType(ErrorType.NOT_FOUND)
                            .message(exception.getMessage())
                            .build()));
        }
        return Mono.empty();
    }
}
```
</details>	

## Exception testing 
Test the error handling by entering the following query via GraphiQL.

```
query routeException {
  route(flightNumber: "LH7X02") {
    id
    flightNumber
  }
}
```

Since the flight number does not exist, an exception should be thrown.


# Test
Task 3: For the GraphQL query routes { id departure }, introduce a real test that checks if there are three routes in the returned result. 

## Add Spring Boot Test Dependency  

Please add the **Spring Boot Test Starter**. 

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>.
	<scope>test</scope>
</dependency>

```
## Add Spring GraphQL Test Dependency.  

Please add the **Spring GraphQL Test**. 

```
<dependency>
	<groupId>org.springframework.graphql</groupId>
	<artifactId>spring-graphql-test</artifactId>.
	<scope>test</scope>
</dependency>

```

## Add Spring Webflux Starter Dependency. 

Please add the **Spring Boot Weblux Starter**. 

```
<dependency>
	<groupId>springframework.boot</groupId>
	<artifactId>spring-boot-starter-webflux</artifactId>.
	<scope>test</scope>
</dependency>
```


## Implement test 

Implement and run the test as follows. The GraphQL query file ``routes.graphql`` is located at ``resources/graphql-test``. 

```
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestWebRouteQueries {

  @Autowired
  private WebGraphQlTester webGraphQlTester;

  @Test
  @DisplayName("Test to query all routes")
  public void routes() {
    
    webGraphQlTester.queryName("routes")
                    .execute()
                    .path("routes[*]")
                    .entityList(Route.class).hasSize(3);
  }
```

## Testing the test 
Run the test from the selected IDE or the console. 


# Security OAuth2 
Task 4: Introduce authentication and authorization via OAuth2/JWT. Accessing the GraphQL Web API with a valid token should only be possible. Moreover, the user should have the read-right when calling the GraphQL field routes. Therefore, use as JWT token the provided token in the request-header-graphiql-oAuth2-security.json file. 

## Security Dependency 
Add the **Spring Boot Security Starter** dependency.  

```
<dependency>
	<groupId>org.springframework.boot</groupId>.
	<artifactId>spring-boot-starter-security</artifactId>.
</dependency>

```

## Add OAuth2 Dependency. 

Add the **Spring Boot OAuth2 Starter** Dependency.  

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>.
</dependency>

```
## Add OAuth2 Properties.  
Add the following Spring properties to the ``application.yml`` file. The public key's text file is located in the ``resource/MET-INF/resources`` directory. Authentication is ensured via signature verification. 

```
spring:
  security:
    oauth2:
      resourceserver:
        jwt: 
          public-key-location: public-key.txt

```

## Security Configuration 
Add the following spring configuration. This configuration is only necessary so that you can still call the URLs ``/airline`` and ``/airline/graphiql`` without specifying a token. This is because we want to define the token first via GraphQLiQL when we make calls. 

```
@Configuration
@Profile("oauth2")
public class GraphQLOAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests().antMatchers("/airline").permitAll()
            .and()
            .authorizeRequests().antMatchers("/airline/graphiql").permitAll()
            .and()
            .authorizeRequests().anyRequest().authenticated()
			.and()
			.oauth2ResourceServer().jwt();
      } 
}
```

The following configuration is essential to enable annotations for authorization. 

```
@Configuration
@Profile("basic | oauth2")
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class GraphQLBasicMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
         
}
```

In the Spring Boot property file ``application.yml`` set the **Profile** ``oAuth2`` to enable the security configuration. 


## Authorize method  

```
@QueryMapping
@PreAuthorize("hasAuthority('SCOPE_read')")
public List<Route> routes() {
   return routeRepository.findAll();
}
```

## Security Test 

Call GraphiQL, enter the token under **Request Header**, 

```
{
  "Authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9. eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6MjE0NDA4NjQ0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJfbmFtZSI6InRvbSIsImp0aSI6ImM4N2Q5NTNjLTZlZDAtNGRlMy1hZTJlLTMwZTcwOTYyNjExNyIsImNsaWVudF9pZCI6ImZvbyJ9. vOx3WIajVeaPelFuYttvSjvOSXw5POwzQiZPxQmH6eSQTVR_YCHHzd0vh2a00g3spZ0-S7fZfkiFuNF-QJogGS-GER-B8p4c6mMrazN0x-wytMVM6xZrQbner0Uqu_uuK1vQs-gm2-2BFpydQtq-ZYicss21RSJTLK7fuH5DzHQ"
}
```

and execute the query.

```
query routes {
  routes {
    id
    flightNumber
  }
}
```
