# Übungsaufgabe Kapitel 6 GraphQL Web-API 

## Lesen über den GraphQL Controller   

Aufgabe 1: Erstellen Sie einen GraphQL Controller, über den alle verfügbaren Route Objekte gelesen werden können. Es soll der gesamte Graph, von Route über Flight bis Employee, ausgelesen werden können. Überprüfen Sie Ihre Implementierung mit GraphiQL.

Für das Testen des Controllers über GraphiQL können Sie die Queries unter ``queries.graphql`` verwenden. 
Für diesen Test ist die Query ``routes`` zu verwenden. 


```
@Controller
public class RouteController {
    
    @Autowired
    private RouteRepository routeRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired 
    private EmployeeRepository employeeRepository;
    
    @QueryMapping
    public List<Route> routes() {
        //TODO
    }
    
    @SchemaMapping
    public List<Flight> flights(Route route) {
    	//TODO
    }
    
    @SchemaMapping
    public List<Employee> employees(Flight flight) {
    	//TODO
    }
}
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
@Controller
public class RouteController {
    
    @Autowired
    private RouteRepository routeRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired 
    private EmployeeRepository employeeRepository;
    
    @QueryMapping
    public List<Route> routes() {
        return routeRepository.findAll();
    }
    
    @SchemaMapping
    public List<Flight> flights(Route route) {
    	return flightRepository.findByRoute(route);
    }
    
    @SchemaMapping
    public List<Employee> employees(Flight flight) {
    	return employeeRepository.findByFlight(flight);
    }
}
```
</details>

## Schreiben über den GraphQL Controller   

Aufgabe 2: Erweitern Sie den GraphQL Controller um eine Mutation so, dass über diese eine neue Route erstellt werden kann. Überprüfen Sie Ihre Implementierung mit GraphiQL.

Fügen Sie dem GraphQL Controller folgende Mutation für das Schreiben hinzu. 

Für das Testen des Controllers über GraphiQL können Sie die Queries unter ``queries.graphql`` verwenden. 
Für diesen Test ist die Query ``createRoute`` zu verwenden.

```
@Controller
public class RouteController {
  
    @MutationMapping
    public Route createRoute(@Argument String flightNumber) {  	
        //TODO	
    }
    
}
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
@Controller
public class RouteController {
  
    @MutationMapping
    public Route createRoute(@Argument String flightNumber) {  	
    	Route route = new Route();	
    	route.setFlightNumber(flightNumber);
    	return routeRepository.save(route); 	
    }
    
}
```
</details>

## Registrieren über den GraphQL Controller   

Aufgabe 3: Erweitern Sie den GraphQL Controller um eine Subscription so, dass eine Registrierung auf das Event “Es wurde eine neue Route erstellt” möglich ist. Senden Sie ein Route Event an den Client, sobald eine neue Route erstellt wurde. Überprüfen Sie Ihre Implementierung mit GraphiQL.

Fügen Sie dem GraphQL Controller folgende Subscription hinzu. 

Für das Testen des Controllers über GraphiQL können Sie die Queries unter ``queries.graphql`` verwenden. 
Für diesen Test ist die Query ``createRoute`` zu verwenden.


```
@Controller
public class RouteController {
    
    @Autowired
    private RoutePublisher routePublisher;
       
    @SubscriptionMapping
    //TODO
}
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
@Controller
public class RouteController {
    
    @Autowired
    private RoutePublisher routePublisher;
       
    @SubscriptionMapping
    public Flux<Route> registerRouteCreated() {
    	return routePublisher.getPublisher();    	
    } 
}
```
</details>

## Client über GraphQL Controller Nachricht senden    

Die Mutation wird so angepasst, dass wenn eine Route erzeugt wurde, dem Client eine Nachricht gesendet wird. 

```
@Controller
public class RouteController {
       
    @Autowired
    private RoutePublisher routePublisher;
    
    @MutationMapping
    public Route createRoute(@Argument String flightNumber) {  	
    	Route route = new Route();	
    	route.setFlightNumber(flightNumber);
    	//TODO
    	return routeRepository.save(route); 	
    }
        
}
```
    
<details>
	<summary>Vollständiges Snippet</summary>
	
```
@Controller
public class RouteController {
       
    @Autowired
    private RoutePublisher routePublisher;
    
    @MutationMapping
    public Route createRoute(@Argument String flightNumber) {  	
    	Route route = new Route();	
    	route.setFlightNumber(flightNumber);
    	routePublisher.emit(route);
    	return routeRepository.save(route); 	
    }
        
}
```
</details>
