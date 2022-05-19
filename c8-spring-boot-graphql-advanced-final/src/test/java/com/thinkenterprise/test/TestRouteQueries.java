/*
 * Spring for GraphQL Training -- Examples of using Spring for GraphQL Copyright (c) 2021-2022
 * Michael Schäfer <spring.thinkenterprise@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.thinkenterprise.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import com.thinkenterprise.controller.RouteController;
import com.thinkenterprise.domain.employee.EmployeeRepository;
import com.thinkenterprise.domain.route.FlightRepository;
import com.thinkenterprise.domain.route.Route;
import com.thinkenterprise.domain.route.RouteRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@GraphQlTest(controllers = RouteController.class, properties = "throwException=false")
@Import(TestRouteQueriesConfiguration.class)
@ActiveProfiles({"TestRouteQueries", "noSecurity"})
public class TestRouteQueries {
  @Autowired
  private GraphQlTester graphQlTester;
  @MockBean
  private RouteRepository routeRepository;
  @MockBean
  private FlightRepository flightRepository;
  @MockBean
  private EmployeeRepository employeeRepository;

  @Test
  @DisplayName("Test to query all routes")
  public void routes() {
    Route route = new Route();
    route.setId(103L);
    route.setFlightNumber("LH401");
    route.setDeparture("FRA");
    route.setDestination("NYC");

    List<Route> initialize = new ArrayList<>(List.of(route));
    Mockito.when(routeRepository.findAll()).thenReturn(initialize);
    graphQlTester.documentName("routes").execute().path("routes[*]").entityList(Route.class)
        .hasSize(1);
  }

  @Test
  @DisplayName("Query exactly one special route")
  public void route() {
    Route route = new Route();
    route.setId(101L);
    route.setFlightNumber("LH7902");
    route.setDeparture("FRA");
    route.setDestination("NYC");
    Optional<Route> optionalRoute = Optional.of(route);

    Mockito.when(routeRepository.findByFlightNumber("LH7902")).thenReturn(optionalRoute);
    graphQlTester.documentName("route").variable("flightNumber", "LH7902").execute()
        .path("route.id");
  }

  @Test
  @DisplayName("Test query route with errors")
  public void routeError() {
    Route route = new Route();
    route.setId(101L);
    route.setFlightNumber("LH7902");
    route.setDeparture("FRA");
    route.setDestination("NYC");

    List<Route> initialize = new ArrayList<>(List.of(route));
    Mockito.when(routeRepository.findAll()).thenReturn(initialize);
    graphQlTester.documentName("route").variable("flightNumber", "L7902").execute().errors()
        .satisfy(errorList -> assertEquals(1, errorList.size()));
  }

  @Test
  @DisplayName("Create Route")
  public void createRoute() {
    Route route = new Route();
    route.setId(103L);
    route.setFlightNumber("LH7977");
    route.setDeparture("FRA");
    route.setDestination("NYC");

    Mockito.when(routeRepository.save(any(Route.class))).thenReturn(route);
    graphQlTester.documentName("createRoute").variable("flightNumber", "LH7977").execute()
        .path("createRoute.flightNumber").entity(String.class).isEqualTo("LH7977");
  }

  @Test
  @DisplayName("Update Route over input object")
  public void updateRoute() {
    Route route = new Route();
    route.setId(101L);
    route.setFlightNumber("LH7977");
    route.setDeparture("BER");
    route.setDestination("MUC");

    Mockito.when(routeRepository.save(any(Route.class))).thenReturn(route);
    Mockito.when(routeRepository.findById(any(Long.class))).thenReturn(Optional.of(route));
    graphQlTester.documentName("updateRoute").variable("id", "101").variable("departure", "BER")
        .variable("destination", "MUC").execute().path("updateRoute.departure").entity(String.class)
        .isEqualTo("BER").path("updateRoute.destination").entity(String.class).isEqualTo("MUC");

  }

  
  @Test
  @DisplayName("Test register Route Creted Subscription")
  public void registerRouteCreated() {
    Flux<Route> result = graphQlTester.documentName("registerRouteCreated").executeSubscription()
        .toFlux("registerRouteCreated", Route.class);
    StepVerifier verifier =
        StepVerifier.create(result).expectNextCount(1L).thenCancel().verifyLater();

    Route route = new Route();
    route.setId(103L);
    route.setFlightNumber("LH7977");
    route.setDeparture("FRA");
    route.setDestination("NYC");

    Mockito.when(routeRepository.save(any(Route.class))).thenReturn(route);

    graphQlTester.documentName("createRoute").variable("flightNumber", "LH7977").execute()
        .path("createRoute.flightNumber").entity(String.class).isEqualTo("LH7977");

    verifier.verify();
  }
}
