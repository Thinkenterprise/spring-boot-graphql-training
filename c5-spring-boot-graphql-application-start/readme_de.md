# Übungsaufgabe Kapitel 5 GraphQL Web-API Anwendung  

## GraphQL Starter einbinden 

Aufgabe 1: Binden Sie über das pom.xml die richtigen Starter ein, sodass sie die GraphQL Web-API über einen synchronen HTTP-Zugriff für den GraphQL Client bereitstellen können.  

```
<dependencies>
	<dependency>
		//TODO
	</dependency>
	<dependency>
		//TODO
	</dependency>
</dependencies>
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-graphql</artifactId>
	</dependency>
</dependencies>
```
</details>

## GraphQL Endpoint konfigurieren  
Aufgabe 2: Konfigurieren Sie die GraphQL Web-API-Anwendung über Spring-Properties so, dass die GraphQL Web-API über die URL localhost:8080/airline angesprochen werden kann. 

```
spring:
  graphql:
    //TODO
    graphiql:
      enabled: true
      //TODO
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
spring:
  graphql:
    path: /airline
    graphiql:
      enabled: true
      path: /airline/graphiql
```
</details>

## GraphQL Schema Location konfigurieren  
Aufgabe 3: Konfigurieren die GraphQL API-Anwendung über Spring-Properties so, dass die Schema-Datei airline.graphqls unter resource/graphql  verwendet wird. 

```
spring:
  graphql:
    schema:
      //TODO
      printer:
        enabled: true
```

<details>
	<summary>Vollständiges Snippet</summary>
	
```
spring:
  graphql:
    schema:
      locations: classpath:graphql/
      printer:
        enabled: true
```
</details>

## GraphiQL Starten 
Aufgabe 4: Starten Sie die GraphQL Web-API-Anwendung und rufen Sie GraphiQL über die URL localhost:8080/airline/graphiql im Browser auf.

```
localhost:8080/airline/graphiql 
```

## GraphQL API-Anwendung testen 

**Achtung kleine Korrektur**. Im Buch wird eine Query verwendet, die nicht funktioniert. Die hier angegebene Query funktioniert. 


Aufgabe 5: Setzen Sie die folgende GraphQL Query ab. 

```
query helloWorld {
  helloWorld
}
```
