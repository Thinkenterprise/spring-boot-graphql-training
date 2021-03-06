package com.thinkenterprise.graphql.directive;

import graphql.schema.DataFetcher;

import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/
public class UppercaseDirective implements SchemaDirectiveWiring {
	  
	@Override
	  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
	    
		GraphQLFieldDefinition field = env.getElement();
	    GraphQLFieldsContainer parentType = env.getFieldsContainer();

	    DataFetcher originalFetcher = env.getCodeRegistry().getDataFetcher(parentType, field);
	    DataFetcher dataFetcher = DataFetcherFactories
	        .wrapDataFetcher(originalFetcher, ((dataFetchingEnvironment, value) -> {
	          if (value instanceof String) {
	            return ((String) value).toUpperCase();
	          }
	          return value;
	        }));

	    env.getCodeRegistry().dataFetcher(parentType, field, dataFetcher);
	    return field;
	  }
}
