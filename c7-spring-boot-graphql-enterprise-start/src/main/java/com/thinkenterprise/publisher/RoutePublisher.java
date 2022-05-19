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

package com.thinkenterprise.publisher;

import com.thinkenterprise.domain.route.Route;

import org.springframework.stereotype.Component;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
public class RoutePublisher {

  private Flux<Route> publisher;
  private FluxSink<Route> emitter;

  public RoutePublisher() {
    Flux<Route> createdPublisher = Flux.create(emitter -> {
      this.emitter = emitter;
    }, FluxSink.OverflowStrategy.BUFFER);
    ConnectableFlux<Route> connectableFlux = createdPublisher.share().publish();
    connectableFlux.connect();
    publisher = Flux.from(connectableFlux);
  }

  public void emit(Route route) {
    emitter.next(route);
  }

  public Flux<Route> getPublisher() {
    return publisher;
  }
}
