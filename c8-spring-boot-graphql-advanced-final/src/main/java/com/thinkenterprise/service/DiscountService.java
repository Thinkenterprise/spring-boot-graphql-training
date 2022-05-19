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

package com.thinkenterprise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
  protected static Logger log = LoggerFactory.getLogger(DiscountService.class);

  public Map<Long, Float> discountData = new HashMap<Long, Float>() {
    private static final long serialVersionUID = 1L;
    {
      put(Long.valueOf(1l), Float.valueOf(1.00f));
      put(Long.valueOf(2l), Float.valueOf(0.89f));
      put(Long.valueOf(3l), Float.valueOf(0.90f));

    }
  };

  public Float getDiscount(Long id) {
    log.info("Discount for Flight " + id);

    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (discountData.containsKey(id))
      return discountData.get(id);
    else
      return Float.valueOf(1.00f);
  }

  public List<Float> getDiscountByIds(List<Long> ids) {
    log.debug("Discount for Flights " + ids);
    List<Float> floats = new ArrayList<Float>();

    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    for (Long id : ids) {
      if (discountData.containsKey(id))
        floats.add(discountData.get(id));
    }
    return floats;
  }


  public List<Float> getDiscountByIds(String technicalUser, List<Long> ids) {
    log.debug("Discount for Flights " + ids);
    log.debug("TechnicalUser " + technicalUser);

    List<Float> floats = new ArrayList<Float>();

    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    for (Long id : ids) {
      if (discountData.containsKey(id))
        floats.add(discountData.get(id));
    }
    return floats;
  }



}
