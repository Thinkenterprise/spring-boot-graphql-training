# Übungsaufgabe Kapitel 8 GraphQL Advanced   

# Metriken 
Aufgabe 1: Die Zeit für einen GraphQL Request soll, über die von dem Spring Boot Actuator bereitgestellten GraphQL Metriken, gemessen werden

## Actuator hinzufügen 

Fügen Sie die **Spring Boot Actuator** Dependency hinzu. 

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

```

## Metric Endpoint freigeben 

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

## GraphQL Metric Properties definieren

 ```
management:
  metrics:
    graphql:
      autotime:
        enabled: true
```

## Metriken abrufen 

 ```
http://localhost:8080/actuator/metrics/graphql
```

Achtung, bevor Sie eine Metrik sehen, müssen sie mindestens einen GraphQL Operation z.B. einer ``query`` durchgeführt haben. 


# Performance 
Aufgabe 2: Der Preis discount eines Flugs Flight wird über den DiscountService bestimmt, indem dieser für jeden zu bestimmenden Discount ein REST-API-Request auf einem Fremdsystem durchführt. Das führt zu sehr vielen REST-API-Requests, die insgesamt zu einer sehr langen Ausführungszeit führen. Aus diesem Grund sollen die vielen REST-API-Requests durch einen Batchaufruf ersetzt werden. Optimieren Sie die Ermittlung des Discounts durch einen Batchaufruf. Prüfen Sie nach der Optimierung, ob sich die Zeit für einen GraphQL-Request reduziert hat.   

## Fast Controler implementieren 

Implementieren Sie einen performanten FlightController mit dem Namen ``FastFlightController``. 

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
    public CompletableFuture<Float>  discount(Flight flight, DataLoader<Long, Float> discountBatchLoader) {
    	return discountBatchLoader.load(flight.getId());
    }
    
}

```
## Fast Profile setzen 

```
 spring:
   profiles:
     active:
     - noSecurity
     - fast
```

## Testen 
Führen Sie die folgende Query einmal mit dem ``fast`` Profile und einmal ohne das ``fast`` Profile durch.

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

und Messen Sie über die Metrik ``graphql.request`` die zeitliche Verbesserung.  


# Instrumentation 
Aufgabe 4: Über eine Instrumentation soll die Anzahl aller Aufrufe auf DataFetcher bestimmt werden. Implementieren Sie eine eigene Instrumentation, die nach jedem GraphQL Request die Anzahl der Aufrufe über das Logging auf der Konsole ausgibt. 

## Instrumentation implementieren 

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







