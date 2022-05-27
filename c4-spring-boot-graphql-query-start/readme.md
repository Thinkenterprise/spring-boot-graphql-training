# Übungsaufgabe Kapitel 4 GraphQL Query Language 

## Voraussetzung 
**Achtung** kleine Korrektur zur Aufgabenbeschreibung aus dem Buch. Dort wird beschrieben, dass ein JAR-File gestartet werden soll, 
dass in dem Start-Projekt liegt. Das ist allerdings nicht der Fall, sondern das JAR-File ist erst wie folgt beschrieben zu erstellen. 
Damit die GraphQL Queries ausgeführt werden können, ist zuvor eine Anwendung zu starten, welche das GraphQL API bereitstellt. 
Gehen Sie in das Projekt ``c6-spring-boot-graphql-api-final`` und bauen und starten Sie das Projekt wie folgt: 

```
mvn clean package 
mvn spring-boot:run
```
Über folgende URL können Sie dann den GraphQL Query Editor starten, mit dem Sie die Queries ausführen und testen können. 

```
localhost:8080/airline/graphiql 
```

## Abfrage aller Routen 
Aufgabe 1: Fragen Sie alle Routen ab.

```
query routes {
  routes {
    id
  }
}
```

## Variablen  
Aufgabe 2: Erstellen Sie zwei Variablen für das Pagination und verwenden Sie diese. 

**Achtung** kleine Korrektur. 

Aufgabe 2: Es soll über eine ``flightNumber`` Variabel eine konkrete Route abgefragt werden. 

```
query route {
  route(flightNumber: "LH7902") {
    id
    flightNumber
  }
}
```

## Verschiedene Queries  
Aufgabe 3: Fragen Sie alle Routen mit drei verschiedenen Queries ab, die unterschiedliche Felder anzeigen.


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
Aufgabe 4: Erstellen Sie ein Fragment, über das Sie immer wiederkehrende Felder zusammenfassen. 

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
Aufgabe 5: Fragen Sie über ein Inline Fragment alle unterschiedlichen Employee aller Flüge ab.

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
