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

package com.thinkenterprise.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.thinkenterprise.domain.employee.Employee;
import com.thinkenterprise.domain.employee.EmployeeRepository;
import com.thinkenterprise.domain.route.Flight;
import com.thinkenterprise.domain.route.FlightRepository;
import com.thinkenterprise.domain.route.Route;
import com.thinkenterprise.domain.route.RouteInput;
import com.thinkenterprise.domain.route.RouteRepository;
import com.thinkenterprise.publisher.RoutePublisher;

import reactor.core.publisher.Flux;

@Controller
public class RouteController {

	@Autowired
	private RouteRepository routeRepository;
	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private RoutePublisher routePublisher;

	private Random random = new Random();

	@QueryMapping
	public List<Route> routes() {
		return routeRepository.findAll();
	}

	@QueryMapping
	public Route route(@Argument String flightNumber) {
		Optional<Route> route = routeRepository.findByFlightNumber(flightNumber);
		return route.get();
	}

	@SchemaMapping
	public List<Flight> flights(Route route) {
		return flightRepository.findByRoute(route);
	}

	@SchemaMapping
	public List<Employee> employees(Flight flight) {
		return employeeRepository.findByFlight(flight);
	}

	@MutationMapping
	public Route createRoute(@Argument String flightNumber) {
		Route route = new Route();
		route.setId((long) random.nextInt(100));
		route.setFlightNumber(flightNumber);
		route = routeRepository.save(route);
		routePublisher.emit(route);
		return route;
	}

	@MutationMapping
	public Boolean deleteRoute(@Argument Long id) {
		routeRepository.deleteById(id);
		return true;
	}

	@SchemaMapping(typeName = "Mutation")
	public Route updateRoute(@Argument Long id, @Argument RouteInput input) {
		Route route = routeRepository.findById(id).get();
		route.setDeparture(route.getDeparture());
		route.setDestination(route.getDestination());
		return routeRepository.save(route);
	}

	@SubscriptionMapping
	public Flux<Route> registerRouteCreated() {
		return routePublisher.getPublisher();
	}
}
