# Übungsaufgabe Kapitel 6 GraphQL Web-API 

## Lesen über den GraphQL Controller   

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

## Schreiben über den GraphQL Controller   

Fügen Sie dem GraphQL Controller folgende Mutation für das Schreiben hinzu. 

Für das Testen des Controllers über GraphiQL können Sie die Queries unter ``queries.graphql`` verwenden. 
Für diesen Test ist die Query ``createRoute`` zu verwenden.
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

## Registrieren über den GraphQL Controller   

Fügen Sie dem GraphQL Controller folgende Subscription für die Anmeldung hinzu 

Für das Testen des Controllers über GraphiQL können Sie die Queries unter ``queries.graphql`` verwenden. 
Für diesen Test ist die Query ``createRoute`` zu verwenden.


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
    	routePublisher.emit(route);
    	return routeRepository.save(route); 	
    }
        
}
```
