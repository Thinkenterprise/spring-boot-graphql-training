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
package com.thinkenterprise;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thinkenterprise.domain.employee.Attendant;
import com.thinkenterprise.domain.employee.Employee;
import com.thinkenterprise.domain.employee.EmployeeRepository;
import com.thinkenterprise.domain.employee.EmployeeRole;
import com.thinkenterprise.domain.employee.Pilot;
import com.thinkenterprise.domain.route.Flight;
import com.thinkenterprise.domain.route.FlightRepository;
import com.thinkenterprise.domain.route.Route;
import com.thinkenterprise.domain.route.RouteRepository;

@SpringBootApplication
public class Application implements ApplicationRunner {

  @Autowired
  RouteRepository routeRepository;

  @Autowired
  FlightRepository flightRepository;

  @Autowired
  EmployeeRepository employeeRepository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    Route route1 = new Route(1L, "LH7902", "MUC", "IAH", true);
    Route route2 = new Route(2L, "LH1602", "MUC", "IBZ", false);
    Route route3 = new Route(3L, "LH4017", "FRA", "NYC", true);

    route1.setRoute(route1);
    route2.setRoute(route2);
    route3.setRoute(route3);

    Flight flight1 = new Flight(1L, route1, 120.45, LocalDate.parse("2018-06-17"));
    Flight flight2 = new Flight(2L, route2, 111.45, LocalDate.parse("2018-07-17"));
    Flight flight3 = new Flight(3L, route3, 60.45, LocalDate.parse("2018-08-17"));

    route1.getFlights().add(flight1);
    route2.getFlights().add(flight2);
    route3.getFlights().add(flight3);

    Employee employee1 =
        new Pilot(1L, flight1, "Flieger", "Fred", "P234238", EmployeeRole.PILOT, "123456");
    Employee employee2 =
        new Attendant(2L, flight1, "Stewardess", "Sarah", "S77667", EmployeeRole.ATTENDANT, 2);

    flight1.getEmployees().add(employee1);
    flight1.getEmployees().add(employee2);

    routeRepository.save(route1);
    routeRepository.save(route2);
    routeRepository.save(route3);

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    employeeRepository.save(employee1);
    employeeRepository.save(employee2);
  }
}
