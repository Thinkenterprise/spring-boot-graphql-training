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
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class RouteRepository {

  Map<Long, Route> data = new HashMap<Long, Route>();

  public Route save(Route route) {
    data.put(route.getId(), route);
    return route;
  }

  public List<Route> findAll() {
    return data.values().stream().collect(Collectors.toList());
  }

  public void deleteById(Long id) {
    data.remove(id);
  }

  public Optional<Route> findById(Long id) {
    Optional<Route> route = Optional.of(data.get(id));
    return route;
  }

}
