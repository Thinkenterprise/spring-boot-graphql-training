# Übungsaufgabe Kapitel 5 GraphQL Web-API Anwendung  

## GraphQL Starter einbinden 

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

## GraphQL Konfigurieren  


```
spring:
  graphql:
    path: /airline
    graphiql:
      enabled: true
      path: /airline/graphiql
    schema:
      locations: classpath:graphql/
      printer:
        enabled: true
```


## GraphiQL Starten 
Rufen Sie im Browser folgende URL auf. 

```
localhost:8080/airline/graphiql 
```

## GraphQL API-Anwendung testen 


```
query helloWorld {
  helloWorld
}
```