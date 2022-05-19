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

package com.thinkenterprise.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Component
public class RouteExceptionResolver implements DataFetcherExceptionResolver {
  @Override
  public Mono<List<GraphQLError>> resolveException(Throwable exception,
      DataFetchingEnvironment environment) {
    if (exception instanceof RouteException) {
      return Mono.fromCallable(() -> Arrays.asList(GraphqlErrorBuilder.newError(environment)
          .errorType(ErrorType.NOT_FOUND).message(exception.getMessage()).build()));
    }
    return Mono.empty();
  }
}
