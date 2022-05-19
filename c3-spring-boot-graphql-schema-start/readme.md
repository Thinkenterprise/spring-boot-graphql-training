# Übungsaufgabe Kapitel 3 GraphQL Schema Language 

## Route Objekt erstellen 
 
```
type Route {
	id: ID!
	flightNumber: String!
	departure: String @deprecated(reason: "Use `newField`.")
	destination: String
	isDisabled: Boolean
	flights: [Flight!]
}
```

## Flight Objekt erstellen 

scalar Date

```
type Flight {
	id: ID!
	price: Float!
	discount: Float!
	date: Date
}
```

## Root Oerations erstellen  

Schema definieren 

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
	createRoute(flightNumber: String!): Route
}
```