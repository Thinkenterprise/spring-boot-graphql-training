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


package com.thinkenterprise.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.dataloader.ValueCache;

public class RedisCache<K, V> implements ValueCache<K, V> {

  private final Map<K, CompletableFuture<V>> cache = new HashMap<>();

  @Override
  public CompletableFuture<Void> clear() {
    cache.clear();
    return new CompletableFuture<Void>();
  }

  @Override
  public CompletableFuture<Void> delete(K key) {
    cache.remove(key);
    return new CompletableFuture<Void>();
  }

  @Override
  public CompletableFuture<V> get(K key) {
    return cache.get(key);
  }

  @Override
  public CompletableFuture<V> set(K key, V value) {
    CompletableFuture<V> completableFuture = CompletableFuture.completedFuture(value);
    cache.put(key, CompletableFuture.completedFuture(value));
    return completableFuture;
  }


}
