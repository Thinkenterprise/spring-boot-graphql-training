# Übungsaufgabe Kapitel 5 GraphQL Web-API Anwendung  

## GraphQL Starter einbinden 

Aufgabe 1: Binden Sie über das pom.xml die richtigen Starter ein, sodass sie die GraphQL Web-API über einen synchronen HTTP-Zugriff für den GraphQL Client bereitstellen können.  

```
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
			<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-websocket</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-graphql</artifactId>
		</dependency>
	</dependencies>
```

## GraphQL Entpoint konfigurieren  
Aufgabe 2: Konfigurieren Sie die GraphQL Web-API-Anwendung über Spring-Properties so, dass die GraphQL Web-API über die URL localhost:8080/airline angesprochen werden kann. 

```
spring:
  graphql:
    path: /airline
    graphiql:
      enabled: true
      path: /airline/graphiql
```

## GraphQL Schema Location konfigurieren  
Aufgabe 3: Konfigurieren die GraphQL API-Anwendung über Spring-Properties so, dass die Schema-Datei airline.graphqls unter resource/graphql  verwendet wird. 

```
spring:
  graphql:
    schema:
      locations: classpath:graphql/
      printer:
        enabled: true
```

## GraphiQL Starten 
Aufgabe 4: Starten Sie die GraphQL Web-API-Anwendung und rufen Sie GraphiQL über die URL localhost:8080/airline/graphiql im Browser auf.

```
localhost:8080/airline/graphiql 
```

## GraphQL API-Anwendung testen 
Aufgabe 5: Setzen Sie die folgende GraphQL Query ab. 

```
query helloWorld {
  helloWorld
}
```