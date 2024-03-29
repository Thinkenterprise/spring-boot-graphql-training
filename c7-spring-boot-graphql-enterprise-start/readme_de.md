# Übungsaufgabe Kapitel 7 GraphQL Enterprise  

## Repositories verwenden 
Aufgabe 1: Verwenden Sie in dem GraphQL RouteController die bestehenden Repositories für den Zugriff auf die Datenbank. 

Bisher wurden **Mocks** als Datenquelle in den GraphQL Controllern verwendet. Tauschen Sie diese gegen die Repositories aus. 
Die folgende Abbildung zeigt die Verwendung des ``RouteRepositories`` für den ``RouteController`` und eine Methode ``routes``. 


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
Aufgabe 2: Für das GraphQL Root Feld route soll eine Fehlerbehandlung eingeführt werden. Immer dann, wenn für die Flugnummer keine passende Route gefunden werden konnte, soll ein GraphQL Error, mit einer passenden Fehlermeldung, zurückgegeben werden. Dabei soll der GraphQL Fehler auch den Ort beinhalten, wo der Fehler aufgetreten ist. 

## Exception werfen 
Implementieren Sie im ``RouteController`` das Werfen der Exception. Die folgende Abbildung zeigt, wie die Exception ``RouteException`` geworfen wird, sofern die ``Route`` über die ``Flugnummer`` nicht gefunden wird. 

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
	<summary>Vollständiges Snippet</summary>
	
```
@QueryMapping
public Route route(String flightNumber) {
	Optional<Route> route = routeRepository.findByFlightNumber(flightNumber);	
   	if(route.isEmpty()) 
    		throw new RouteException("Hello this is a Route Exception");
    	else 
    		return route.get();	
    }
```
</details>

## Exception Resolver implementieren 
Erstellen Sie einen eigenen ``DataFetcherExceptionResolver``, der die Exception ``RouteException`` abfängt und eine geeigneten GraphQL Error erzeugt. 

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
	<summary>Vollständiges Snippet</summary>
	
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

## Exception Testen 
Prüfen Sie die Fehlerbehandlung, indem Sie über GraphiQL folgende Query eingeben.

```
query routeException {
  route(flightNumber: "LH7X02") {
    id
    flightNumber
  }
}
```

Da die Flugnummer nicht exisitert müsste eine Exception geworfen werden.


# Test
Aufgabe 3: Führen Sie für die GraphQL Query routes { id departure } einen realen Test ein, der prüft, ob in dem gelieferten Ergebnis drei Routen enthalten sind. 

## Spring Boot Test Dependency hinzufügen  

Fügen Sie bitte den **Spring Boot Test Starter** hinzu. 

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>

```
## Spring GraphQL Test Dependency hinzufügen  

Fügen Sie bitte den **Spring GraphQL Test**  hinzu. 

```
<dependency>
	<groupId>org.springframework.graphql</groupId>
	<artifactId>spring-graphql-test</artifactId>
	<scope>test</scope>
</dependency>

```

## Spring Webflux Starter  Dependency hinzufügen 

Fügen Sie bitte den **Spring Boot Weblux Starter**  hinzu. 

```
<dependency>
	<groupId>springframework.boot</groupId>
	<artifactId>spring-boot-starter-webflux</artifactId>
	<scope>test</scope>
</dependency>
```


## Test implementieren 

Implementieren Sie den Test wie folgt und führen Sie diesen aus. Die GraphQL Query Datei ``routes.graphql`` ist unter ``resources/graphql-test`` abgelegt. 

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

## Testen des Tests 
Führen Sie den Test über die gewählte IDE oder über die Konsole aus. 



# Security OAuth2 
Aufgabe 4: Führen Sie eine Authentifizierung und Autorisierung über OAuth2/JWT ein. Es soll nur möglich sein, mit einem validen Token auf das GraphQL-Web-API zuzugreifen. Zudem soll der Benutzer beim Aufruf des GraphQL Feldes routes, das Recht read besitzen. Verwenden Sie als JWT-Token den bereitgestellten Token in der Datei request-header-graphiql-oAuth2-security.json. 

## Security Dependency 
Fügen Sie die **Spring Boot Security Starter** Dependency hinzu.  

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>

```

## OAuth2 Dependency hinzufügen 

Fügen Sie die **Spring Boot OAuth2 Starter** Dependency hinzu.  

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>

```
## OAuth2 Properties hinzufügen  
Fügen Sie die folgenden Spring Properties in der Datei ``application.yml`` hinzu. Die Textdatei mit dem Public-Key befindet sich im Verzeichnis ``resource/MET-INF/resources``. Über die Prüfung der Signatur ist die Authentifizierung sichergestellt. 

```
spring:
  security:
    oauth2:
      resourceserver:
        jwt: 
          public-key-location: public-key.txt

```

## Security Configuration 
Fügend Sie die folgende Spring Konfiguration hinzu. Diese Konfiguration ist nur notwendig, damit Sie weiterhin die URLs ``/airline`` und ``/airline/graphiql`` aufrufen können, ohne einen Token angeben zu müssen. Denn den Token möchten wir ja erst über GraphQLiQL bei unseren Aufrufen definieren. 

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

Die folgende Konfiguration ist wichtig, um Annotationen für die Autorisierung zu aktivieren. 

```
@Configuration
@Profile("basic | oauth2")
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class GraphQLBasicMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration  {
         
}
```

Setzen Sie in der Spring Boot Property Datei ``application.yml`` nocht das **Profile** ``oAuth2`` damit die Security Konfiguration aktiv wird. 


## Methode autorsieren  

```
@QueryMapping
@PreAuthorize("hasAuthority('SCOPE_read')")
public List<Route> routes() {
   return routeRepository.findAll();
}
```

## Security Test 

Rufen sie GraphiQL auf, geben Sie unter **Request Herader** den Token ein 

```
{
  "Authorization":"Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6MjE0NDA4NjQ0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJfbmFtZSI6InRvbSIsImp0aSI6ImM4N2Q5NTNjLTZlZDAtNGRlMy1hZTJlLTMwZTcwOTYyNjExNyIsImNsaWVudF9pZCI6ImZvbyJ9.vOx3WIajVeaPelFuYttvSjvOSXw5POwzQiZPxQmH6eSQTVR_YCHHzd0vh2a00g3spZ0-S7fZfkiFuNF-QJogGS-GER-B8p4c6mMrazN0x-wytMVM6xZrQbner0Uqu_uuK1vQs-gm2-2BFpydQtq-ZYicss21RSJTLK7fuH5DzHQ"
}
```

und führen Sie die Query 

```
query routes {
  routes {
    id
    flightNumber
  }
}
```

aus. 

