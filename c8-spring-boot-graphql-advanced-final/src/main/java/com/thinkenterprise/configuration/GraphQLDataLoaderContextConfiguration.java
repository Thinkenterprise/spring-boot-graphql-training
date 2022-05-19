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


package com.thinkenterprise.configuration;

import com.thinkenterprise.cache.RedisCache;
import com.thinkenterprise.properties.DataLoaderProperties;
import org.dataloader.DataLoaderOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DataLoaderProperties.class)
public class GraphQLDataLoaderContextConfiguration {

  @Bean
  public DataLoaderOptions dataLoaderOptions(DataLoaderProperties dataLoaderProperties) {

    DataLoaderOptions dataLoaderOptions = new DataLoaderOptions();

    dataLoaderOptions.setMaxBatchSize(dataLoaderProperties.getMaxBatchSize());
    dataLoaderOptions.setCachingEnabled(dataLoaderProperties.isCachingEnabled());
    dataLoaderOptions
        .setCachingExceptionsEnabled(dataLoaderProperties.isCachingExceptionsEnabled());
    dataLoaderOptions.setBatchingEnabled(dataLoaderProperties.isBatchingEnabled());

    if (dataLoaderProperties.isValueCacheEnabled())
      dataLoaderOptions.setValueCache(new RedisCache<>());

    return dataLoaderOptions;
  }
}
