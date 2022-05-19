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

import com.thinkenterprise.publisher.RoutePublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("TestRouteQueries")
public class TestRouteQueriesConfiguration {
  @Bean
  public RoutePublisher createRoutePublisher() {
    return new RoutePublisher();
  }
}