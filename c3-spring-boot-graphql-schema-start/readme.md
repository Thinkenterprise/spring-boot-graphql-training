# Übungsaufgabe Kapitel 3 GraphQL Schema Language 

# Domain-Modell erstellen 

Aufgabe 1: Erstellen sie das GraphQL Schema für das Domain-Modell, bestehend aus Route und 
Flight siehe Abbildung 1

## Route Objekt erstellen 
 
```
type Route {
	id: ID!
	flightNumber: String!
	departure: String 
	destination: String
	isDisabled: Boolean
	flights: [Flight!]
}
```

## Flight Objekt erstellen 

```
scalar Date

type Flight {
	id: ID!
	price: Float!
	discount: Float!
	date: Date
}
```

## Root Operations erstellen  
Aufgabe 2: Erstellen sie die Root Operations, für die Abfrage aller Route und der Erzeugung genau einer Route. 

```
schema {
	query: Query
	mutation: Mutation
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
	createRoute(): Route
}
```

## Argumente übergeben 
Aufgabe 3: Bei der Erstellung einer Route soll als Argument die flightNumber übergeben 
werden

```
type Mutation {
	createRoute(flightNumber: String!): Route
}
```
## Directive verwenden 
Aufgabe 4: Markieren Sie das Feld departure über eine Directive als deprecated

```
type Route {
	departure: String @deprecated(reason: "Use `newField`.")
}
```

