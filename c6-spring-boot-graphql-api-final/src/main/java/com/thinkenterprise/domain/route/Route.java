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

package com.thinkenterprise.domain.route;

import java.util.HashSet;
import java.util.Set;

import com.thinkenterprise.domain.AbstractEntity;

public class Route extends AbstractEntity {

  private String flightNumber;
  private String departure;
  private String destination;
  private Boolean isDisabled;

  private Route route;

  private Set<Flight> flights = new HashSet<>();

  public Route() {
    super();
  }

  public Route(String flightNumber) {
    super();
    this.flightNumber = flightNumber;
  }

  public Route(Long id, String flightNumber, String departure, String destination,
      Boolean isDisabled) {
    super();
    this.id = id;
    this.flightNumber = flightNumber;
    this.destination = destination;
    this.departure = departure;
    this.isDisabled = isDisabled;
  }

  public void setDisabled(Boolean isDisabled) {
    this.isDisabled = isDisabled;
  }

  public Boolean getDisabled() {
    return this.isDisabled;
  }

  public String getDeparture() {
    return departure;
  }

  public void setDeparture(String departure) {
    this.departure = departure;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String number) {
    this.flightNumber = number;
  }

  public Set<Flight> getFlights() {
    return flights;
  }

  public void setFlights(Set<Flight> flights) {
    this.flights = flights;
  }

  public Boolean getIsDisabled() {
    return isDisabled;
  }

  public void setIsDisabled(Boolean isDisabled) {
    this.isDisabled = isDisabled;
  }

  public Route getRoute() {
    return route;
  }

  public void setRoute(Route parent) {
    this.route = parent;
  }

}
