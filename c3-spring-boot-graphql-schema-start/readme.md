# Übungsaufgabe Kapitel 3 GraphQL Schema Language 

# Domain-Modell erstellen 

Aufgabe 1: Erstellen sie das GraphQL Schema für das Domain-Modell, bestehend aus Route und 
Flight siehe Abbildung 1

## Route Objekt erstellen 
 
```
type Route {
	//TODO
}
```
<details>
	<summary>Vollständiges Snippet</summary>
	
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
</details>

## Flight Objekt erstellen 

```
scalar Date

type Flight {
	//TODO
}
```
<details>
	<summary>Vollständiges Snippet</summary>
	
```
type Flight {
	id: ID!
	price: Float!
	discount: Float!
	date: Date
}
```
</details>

## Root Operations erstellen  
Aufgabe 2: Erstellen sie die Root Operations, für die Abfrage aller Route und der Erzeugung genau einer Route. 

```
schema {
	//TODO
}
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
schema {
	query: Query
	mutation: Mutation
}
```
</details>

Root Operations für das Lesen definieren 

```
type Query { 
	//TODO
}
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
type Query { 
	routes: [Route]
}
```
</details>

Root Operation für das Schreiben definieren 

```
type Mutation {
	//TODO
}
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
type Mutation {
	createRoute(): Route
}
```
</details>

## Argumente übergeben 
Aufgabe 3: Bei der Erstellung einer Route soll als Argument die flightNumber übergeben 
werden

```
type Mutation {
	//TODO
}
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
type Mutation {
	createRoute(flightNumber: String!): Route
}
```
</details>

## Directive verwenden 
Aufgabe 4: Markieren Sie das Feld departure über eine Directive als deprecated

```
type Route {
	departure: String(reason: "Use `newField`.")
}
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
type Route {
	departure: String @deprecated(reason: "Use `newField`.")
}
```
</details>
