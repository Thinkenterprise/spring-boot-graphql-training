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

package com.thinkenterprise.context;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;

import graphql.GraphQL;
import reactor.core.publisher.Mono;

@Component
public class RouteWebInterceptor implements WebGraphQlInterceptor {
  @Override
  public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest webInput, Chain chain) {
    webInput.configureExecutionInput((executionInput, builder) -> {

      Map<String, String> headers = webInput.getHeaders().toSingleValueMap();
      Map<Object, Object> context = new HashMap<>();
      for (var entry : headers.entrySet()) {
        if (entry.getKey().equals("myheader"))
          context.put("myheader", entry.getValue());
      }
      context.put("databseCredentials", "myDatabaseCredentials");
      return builder.graphQLContext(context).build();
    });

    Map<Object, Object> myExtentions = new HashMap<>();
    myExtentions.put("myExtention", "myExtentionValue");
    return chain.next(webInput)
        .map(webOutput -> webOutput.transform(builder -> builder.extensions(myExtentions)));
  }
}
