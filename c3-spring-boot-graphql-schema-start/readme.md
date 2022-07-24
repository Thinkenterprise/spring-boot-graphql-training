# Exercise Chapter 3 GraphQL Schema Language 

# Create Domain Model 

Task 1: Create the GraphQL schema for the domain model, consisting of Route and Flight. See Figure 1

## Create a Route object 
 
```
type Route {
	//TODO
}
```
<details>
	<summary>Complete Snippet</summary>
	
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

## Create a Flight object 

```
scalar Date

type Flight {
	//TODO
}
```
<details>
	<summary>Complete Snippet</summary>
	
```
type Flight {
	id: ID!
	price: Float!
	discount: Float!
	date: Date
}
```
</details>

## Create Root Operations  
Task 2: Create the root operations for querying all routes and creating exactly one route. 

```
schema {
	//TODO
}
```

<details>
	<summary>Complete Snippet</summary>
	
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
	<summary>Complete Snippet</summary>
	
```
type Query { 
	routes: [Route]
}
```
</details>

Define root operation for writing 

```
type Mutation {
	//TODO
}
```

<details>
	<summary>Complete Snippet</summary>
	
```
type Mutation {
	createRoute(): Route
}
```
</details>

## Pass arguments 
Task 3: Pass the flightNumber as an argument when creating a route. 
```
type Mutation {
	//TODO
}
```

<details>
	<summary>Complete Snippet</summary>
	
```
type Mutation {
	createRoute(flightNumber: String!): Route
}
```
</details>

## Use directive 
Task 4: Mark the field departure via a directive as deprecated

```
type Route {
	departure: String(reason: "Use `newField`.")
}
```

<details>
	<summary>Complete Snippet</summary>
	
```
type Route {
	departure: String @deprecated(reason: "Use `newField`.")
}
```
</details>
