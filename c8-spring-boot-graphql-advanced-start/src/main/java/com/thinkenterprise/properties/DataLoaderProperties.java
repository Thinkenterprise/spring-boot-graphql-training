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


package com.thinkenterprise.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "data-loader-options")
public class DataLoaderProperties {

  private boolean batchingEnabled;
  private boolean cachingEnabled;
  private boolean cachingExceptionsEnabled;
  private int maxBatchSize;
  private boolean valueCacheEnabled;

  public boolean isValueCacheEnabled() {
    return valueCacheEnabled;
  }

  public void setValueCacheEnabled(boolean valueCacheEnabled) {
    this.valueCacheEnabled = valueCacheEnabled;
  }

  public boolean isBatchingEnabled() {
    return batchingEnabled;
  }

  public void setBatchingEnabled(boolean batchingEnabled) {
    this.batchingEnabled = batchingEnabled;
  }

  public boolean isCachingEnabled() {
    return cachingEnabled;
  }

  public void setCachingEnabled(boolean cachingEnabled) {
    this.cachingEnabled = cachingEnabled;
  }

  public boolean isCachingExceptionsEnabled() {
    return cachingExceptionsEnabled;
  }

  public void setCachingExceptionsEnabled(boolean cachingExceptionsEnabled) {
    this.cachingExceptionsEnabled = cachingExceptionsEnabled;
  }

  public int getMaxBatchSize() {
    return maxBatchSize;
  }

  public void setMaxBatchSize(int maxBatchSize) {
    this.maxBatchSize = maxBatchSize;
  }
}
