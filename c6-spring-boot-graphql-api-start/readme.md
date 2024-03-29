## Exercise Chapter 6 GraphQL Web API 

## Reading via GraphQL Controller   

Task 1: Create a GraphQL controller through which all available Route objects can be read. It should be possible to read the entire graph, from Route to Flight to Employee. Verify your implementation with GraphiQL.

For testing the controller via GraphiQL you can use the queries in ``queries.graphql``. 
For this test use the query ``routes``. 


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
	<summary>Complete snippet</summary>.
	
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

## Writing via the GraphQL Controller   

Task 2: Extend the GraphQL controller with a mutation so that a new route can be created through it. Verify your implementation with GraphiQL.

Add the following mutation to the GraphQL controller for writing. 

For testing the controller via GraphiQL, you can use the queries in ``queries.graphql``. 
For this test, use the query ``createRoute``.

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
	<summary>Complete snippet</summary>.
	
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

## Register using the GraphQL Controller   

Task 3: Extend the GraphQL Controller to include a subscription in a way that allows registration on the event "A new route has been created". Send a route event to the client when a new route is created. Verify your implementation with GraphiQL.

Add the following subscription to the GraphQL controller. 

For testing the controller using GraphiQL, you can use the queries in ``queries.graphql``. 
For this test, use the query ``createRoute``.


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
	<summary>Complete snippet</summary>.
	
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

## Send client message via GraphQL controller    

The mutation is customized to send a message to the client when a route is created. 

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
	<summary>Complete snippet</summary>.
	
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
