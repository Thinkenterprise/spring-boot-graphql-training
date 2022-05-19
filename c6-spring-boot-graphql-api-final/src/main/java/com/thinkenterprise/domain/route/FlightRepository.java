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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;


@Repository
public class FlightRepository {

  Map<Long, Flight> data = new HashMap<Long, Flight>();

  public Flight save(Flight flight) {
    data.put(flight.getId(), flight);
    return flight;
  }

  public List<Flight> findByRoute(Route route) {
    return data.values().stream().filter(flight -> flight.getRoute().getId() == route.getId())
        .collect(Collectors.toList());
  }


}
