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

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import com.thinkenterprise.domain.route.Route;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestWebRouteSubscription {

	@LocalServerPort
	private int port;
	
	@Value("ws://localhost:${local.server.port}${spring.graphql.websocket.path}")
	private String baseUrl;

	@Autowired
	private GraphQlTester webGraphQlTester;
	
	@BeforeEach
	void setUp() {
		URI url = URI.create(baseUrl);
		this.webGraphQlTester = WebSocketGraphQlTester.builder(url, new ReactorNettyWebSocketClient()).build();
	}
	
	@Test
	@DisplayName("Test register Route Creted Subscription")
	public void registerRouteCreated() {
		Flux<Route> result = webGraphQlTester.documentName("registerRouteCreated").executeSubscription()
				.toFlux("registerRouteCreated", Route.class);
		StepVerifier verifier = StepVerifier.create(result).expectNextCount(1L).thenCancel().verifyLater();
		webGraphQlTester.documentName("createRoute").variable("flightNumber", "LH7977").execute()
				.path("createRoute.flightNumber").entity(String.class).isEqualTo("LH7977");

		verifier.verify();
	}
	
}
