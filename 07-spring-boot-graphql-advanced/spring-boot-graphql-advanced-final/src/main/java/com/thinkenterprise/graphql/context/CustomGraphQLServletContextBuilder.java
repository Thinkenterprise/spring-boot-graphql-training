package com.thinkenterprise.graphql.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkenterprise.domain.route.service.DiscountService;
import com.thinkenterprise.graphql.cache.RedisCache;
import com.thinkenterprise.graphql.data.DiscountBatchLoader;

import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

public class CustomGraphQLServletContextBuilder implements GraphQLServletContextBuilder {

	protected static Logger log = LoggerFactory.getLogger(CustomGraphQLServletContextBuilder.class);

	DefaultGraphQLServletContext defaultGraphQLServletContext;
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private RedisCache redisCache;
	
	@Override
	public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		String userId = httpServletRequest.getHeader("user-id");
	    defaultGraphQLServletContext = DefaultGraphQLServletContext.createServletContext()
				    											   .with(httpServletRequest)
				    											   .with(httpServletResponse)
				    											   .with(createDataLoaderRegisty())
				    											   .build();
		
		return new CustomGraphQLServletContext(userId, defaultGraphQLServletContext);	
	}

	public DefaultGraphQLServletContext getDefaultGraphQLServletContext() {
		return defaultGraphQLServletContext;
	}

	public void setDefaultGraphQLServletContext(DefaultGraphQLServletContext defaultGraphQLServletContext) {
		this.defaultGraphQLServletContext = defaultGraphQLServletContext;
	}

	@Override
	public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
		
		return DefaultGraphQLWebSocketContext.createWebSocketContext().with(handshakeRequest)
																	  .with(session)
																	  .with(createDataLoaderRegisty())
																	  .build();
	}
	
	@Override
	public GraphQLContext build() {	
		throw new IllegalStateException("Unsupported non network build method called in CustomGraphQLServletContextBuilder");
	}
	
	public DataLoaderRegistry createDataLoaderRegisty() {
		DataLoaderOptions options = DataLoaderOptions.newOptions().setCacheMap(redisCache);
		
		return new DataLoaderRegistry().register("discount",
				DataLoader.newDataLoader(new DiscountBatchLoader(discountService), options));
	}
	
	
	
}
