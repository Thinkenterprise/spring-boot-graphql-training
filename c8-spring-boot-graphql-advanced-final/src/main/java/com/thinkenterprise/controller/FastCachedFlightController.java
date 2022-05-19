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
import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;

import com.thinkenterprise.domain.employee.Employee;
import com.thinkenterprise.domain.employee.EmployeeRepository;
import com.thinkenterprise.domain.route.Flight;
import com.thinkenterprise.service.DiscountService;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

@Controller
@Profile("fast&cache")
public class FastCachedFlightController {
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private DiscountService discountService;

  public FastCachedFlightController(BatchLoaderRegistry batchLoaderRegistry,
      DataLoaderOptions dataLoaderOptions) {
    batchLoaderRegistry.<Long, Float>forName("discountBatchLoader").withOptions(dataLoaderOptions)
        .registerBatchLoader((keys, batchEnvironment) -> {
          GraphQLContext graphQLContext = batchEnvironment.getContext();
          String technicalUser = graphQLContext.get("TECHNICAL_USER");
          return Flux.fromIterable(discountService.getDiscountByIds(technicalUser, keys));
        });
  }

  @SchemaMapping
  public List<Employee> employees(Flight flight) {
    return employeeRepository.findByFlight(flight);
  }

  @SchemaMapping()
  public CompletableFuture<Float> discount(Flight flight,
      DataFetchingEnvironment dataFetchingEnvironment) {
    String context = "Some Context Information";
    return dataFetchingEnvironment.getDataLoaderRegistry()
        .<Long, Float>getDataLoader("discountBatchLoader").load(flight.getId(), context);
  }
}
