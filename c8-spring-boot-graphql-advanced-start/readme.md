# Exercise Chapter 8 GraphQL Advanced   

# Metrics 
Task 1: Measure the time for a GraphQL request using the GraphQL metrics provided by the Spring Boot Actuator.

## Add Actuator 

Add the **Spring Boot Actuator** dependency. 

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>.
</dependency>

```

## Share metric endpoint 

```
management:
  endpoints:
    web:
      discovery:
        enabled: true
      exposure:
        include:
        - metrics
```

## Define GraphQL Metric Properties

 ```
management:
  metrics:
    graphql:
      autotime:
        enabled: true
```

## Retrieve metrics 

 ```
http://localhost:8080/actuator/metrics/graphql
```

Attention, before you see a metric, you must have performed at least one GraphQL operation, e.g., a ``query``. 


# Performance 
Task 2: The price discount of a flight Flight is determined via the DiscountService by performing a REST API request on a third-party system for each discount to be determined. This leads to many REST API requests, resulting in a long execution time overall. For this reason, the many REST API requests should be replaced with a batch call. Optimize the determination of the discount by a batch call. After optimization, check if the time for a GraphQL request has been reduced.   

## Implement Fast Controller 

Implement a performant FlightController with the name ``FastFlightController``. 

 ```
@Controller
@Profile("batch")
public class FastFlightController { 
    @Autowired 
    private EmployeeRepository employeeRepository;    
    @Autowired
    private DiscountService discountService;
    
    public FastFlightController(BatchLoaderRegistry batchLoaderRegistry) {
	//TODO               
    }
    
    @SchemaMapping
    public List<Employee> employees(Flight flight) {
    	return employeeRepository.findByFlight(flight);
    }  
    
    @SchemaMapping
    public CompletableFuture<Float> discount(Flight flight, DataLoader<Long, Float> discountBatchLoader) {
    	return discountBatchLoader.load(flight.getId());
    }
    
}

```

<details>
	<summary>Complete snippet</summary>.
	
```
@Controller
@Profile("batch")
public class FastFlightController { 
    @Autowired 
    private EmployeeRepository employeeRepository;    
    @Autowired
    private DiscountService discountService;
    
    public FastFlightController(BatchLoaderRegistry batchLoaderRegistry) {
         batchLoaderRegistry.forTypePair(Long.class, Float.class)
                 .registerBatchLoader(
                         (keys, batchEnvironment) -> Flux.fromIterable(discountService.getDiscountByIds(keys)));                        
    }
    
    @SchemaMapping
    public List<Employee> employees(Flight flight) {
    	return employeeRepository.findByFlight(flight);
    }  
    
    @SchemaMapping
    public CompletableFuture<Float> discount(Flight flight, DataLoader<Long, Float> discountBatchLoader) {
    	return discountBatchLoader.load(flight.getId());
    }
    
}
```
</details>

## Set Fast Profile 

```
 spring:
   profiles:
     active:
     - noSecurity
     - almost
```

## Testing 
Run the following query once with the ``fast`` profile and once without the ``fast`` profile.

```
query routes {
  routes {
    id
    flightNumber
    flights {
      id
      date
      discount
    }
  }
}
```

Use the ``graphql.request`` metric to measure the improvement over time.  


# Instrumentation 
Task 4: Use instrumentation to determine the number of all calls to the DataFetcher. Implement a custom instrumentation that outputs the number of calls by logging it to the console after each GraphQL request. 

## Implement Instrumentation 

```
@Component
public class DataFetcherAccessConterInstrumentation extends SimpleInstrumentation {

    public InstrumentationState createState() {
        return new DataFetcherAccessConterInstrumentationState(0);    
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginField(InstrumentationFieldParameters parameters) {
        //TODO
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecuteOperation(InstrumentationExecuteOperationParameters parameters) {
        //TODO
    }

    public static class DataFetcherAccessConterInstrumentationState implements InstrumentationState {

        Integer count;

        private DataFetcherAccessConterInstrumentationState(Integer count) {
            this.count=count;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        } 

    }
    
}

```

<details>
	<summary>Complete snippet</summary>.
	
```

@Component
public class DataFetcherAccessConterInstrumentation extends SimpleInstrumentation {

    public InstrumentationState createState() {
        return new DataFetcherAccessConterInstrumentationState(0);    
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginField(InstrumentationFieldParameters parameters) {
        DataFetcherAccessConterInstrumentationState state = parameters.getInstrumentationState();
        return SimpleInstrumentationContext.whenCompleted((data, exception) -> state.setCount(state.getCount()+1));
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecuteOperation(InstrumentationExecuteOperationParameters parameters) {
        DataFetcherAccessConterInstrumentationState state = parameters.getInstrumentationState();
        return SimpleInstrumentationContext.whenCompleted((data, exception) -> System.out.println("Filed Count: " + state.getCount()));
    }

    public static class DataFetcherAccessConterInstrumentationState implements InstrumentationState {

        Integer-Zahl;

        private DataFetcherAccessConterInstrumentationState(Integer count) {
            this.count=count;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        } 

    }
    
}
```
</details>
