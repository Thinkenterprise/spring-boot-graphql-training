## Exercise Chapter 4 GraphQL Query Language 

## Prerequisite 
**Attention** A minor correction to the task description from the book. There it is described that a JAR file is to be started located in the start project. But this is not the case. The JAR file has to be created first, as described below. 
To execute the GraphQL queries, an application is to be started before, which makes the GraphQL API available. 
Go to the project ``c6-spring-boot-graphql-api-final`` and build and start the project as follows: 

```
mvn clean package 
mvn spring-boot:run
```
You can then use the following URL to start the GraphQL query editor, which you can use to run and test the queries. 

```
localhost:8080/airline/graphiql 
```

## Query all routes 
Task 1: Query all routes.

```
query routes {
  //TODO
}
```

<details>
	<summary>Complete snippet</summary>.
	
```
query routes {
  routes {
    id
  }
}
```
</details>

## Variables  
Task 2: Create two variables for pagination and use them. 

**Caution** minor correction. 

Task 2: A concrete route is to be queried using a ``flightNumber`` variable. 

```
query route {
  route {
    id
    flightNumber
  }
}
```

<details>
	<summary>Complete Snippet</summary>
	
```
query route {
  route(flightNumber: "LH7902") {
    id
    flightNumber
  }
}
```
</details>

## Different queries  
Task 3: Query all routes with three different queries that display different fields.


```
query routes1 {
  routes {
    id
    flightNumber
  }
}
```

```
query routes2 {
  routes {
    id
    route {
      id
    }
  }
}
```

```
query routes {
  routes {
    id
    route {
      id
    }
    flights {
      id
      price
    }
  }
}

```

## Fragments  
Task 4: Create a fragment to summarize recurring fields. 

```
fragment routeDetails on Route {
  //TODO
}

query queryWithFragment{
  route(flightNumber: "LH7902") {
   ...routeDetails
   isDisabled
  }
}
```

<details>
	<summary> Complete snippet</summary>.
	
```
fragment routeDetails on route {
  id
  flightNumber
}

query queryWithFragment{
  route(flightNumber: "LH7902") {
   ...routeDetails
   isDisabled
  }
}
```
</details>

## Inline Fragment 
Task 5: Query all employees of all flights via an inline fragment.

```
query routes {
  routes {
    id
    flightNumber
    flights {
      id
      date
      employees {
        id
        //TODO
      }
    }
  }
}
```

<details>
	<summary>Full snippet</summary>.
	
```
query routes {
  routes {
    id
    flightNumber
    flights {
      id
      date
      employees {
        id
        ... on Attendant {
          rank
        }
        ... on Pilot {
          certificateNumber
        }
      }
    }
  }
}
```
</details>
