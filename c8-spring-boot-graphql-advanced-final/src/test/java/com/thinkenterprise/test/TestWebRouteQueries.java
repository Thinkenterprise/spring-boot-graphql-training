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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.graphql.test.tester.GraphQlTester;
import com.thinkenterprise.domain.route.Route;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestWebRouteQueries {

  @Autowired
  private GraphQlTester webGraphQlTester;

  @Test
  @DisplayName("Test to query all routes")
  public void routes() {
    webGraphQlTester.documentName("routes").execute().path("routes[*]").entityList(Route.class)
        .hasSize(3);
  }

  @Test
  @DisplayName("Query exactly one special route")
  public void route() {
    webGraphQlTester.documentName("route").variable("flightNumber", "LH7902").execute()
        .path("route.id").entity(String.class).isEqualTo("101");
  }

  @Test
  @DisplayName("Test query route with errors")
  public void routeError() {
    webGraphQlTester.documentName("route").variable("flightNumber", "L7902").execute().errors()
        .satisfy(errorList -> assertEquals(1, errorList.size()));
  }

  @Test
  @DisplayName("Create Route")
  public void createRoute() {
    webGraphQlTester.documentName("createRoute").variable("flightNumber", "LH7977").execute()
        .path("createRoute.flightNumber").entity(String.class).isEqualTo("LH7977");
  }

  @Test
  @DisplayName("Update Route over input object")
  public void updateRoute() {
    webGraphQlTester.documentName("updateRoute").variable("id", "101").variable("departure", "BER")
        .variable("destination", "MUC").execute().path("updateRoute.departure").entity(String.class)
        .isEqualTo("BER").path("updateRoute.destination").entity(String.class).isEqualTo("MUC");
  }

}
