# Übungsaufgabe Kapitel 4 GraphQL Query Language 

## Abfrage aller Routen 
Frage 1: Fragen Sie alle Routen ab.

```
query routes {
  routes {
    id
  }
}
```

## Variablen  
Frage 2: Erstellen Sie zwei Variablen für das Pagination und verwenden Sie diese

```
query route {
  route(flightNumber: "LH7902") {
    id
    flightNumber
  }
}
```

## Verschiedene Queries  
Frage 3: Fragen Sie alle Routen mit drei verschiedenen Queries ab, die unterschiedliche Felder anzeigen.


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

## Fragmente  
Frage 4: Erstellen Sie ein Fragment, über das Sie immer wiederkehrende Felder zusammenfassen. 

```
fragment routeDetails on Route {
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

## Inline Fragment 

Frage 5: Fragen Sie über ein Inline Fragment alle unterschiedlichen Employee aller Flüge ab.



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



Root Operations für das Lesen definieren 

```
type Query { 
	routes: [Route]
}
```

Root Operation für das Schreiben definieren 

```
type Mutation {
	createRoute(flightNumber: String!): Route
}
```