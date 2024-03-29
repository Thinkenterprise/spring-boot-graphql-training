# Metrics

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

        Integer-Number;

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
