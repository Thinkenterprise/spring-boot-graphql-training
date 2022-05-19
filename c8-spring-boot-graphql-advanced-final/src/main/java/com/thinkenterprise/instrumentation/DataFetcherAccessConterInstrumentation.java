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

package com.thinkenterprise.instrumentation;

import org.springframework.stereotype.Component;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecuteOperationParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldParameters;

@Component
public class DataFetcherAccessConterInstrumentation extends SimpleInstrumentation {

  public InstrumentationState createState() {
    return new DataFetcherAccessConterInstrumentationState(0);
  }

  @Override
  public InstrumentationContext<ExecutionResult> beginField(
      InstrumentationFieldParameters parameters) {
    DataFetcherAccessConterInstrumentationState state = parameters.getInstrumentationState();
    return SimpleInstrumentationContext
        .whenCompleted((data, exception) -> state.setCount(state.getCount() + 1));
  }

  @Override
  public InstrumentationContext<ExecutionResult> beginExecuteOperation(
      InstrumentationExecuteOperationParameters parameters) {
    DataFetcherAccessConterInstrumentationState state = parameters.getInstrumentationState();
    return SimpleInstrumentationContext
        .whenCompleted((data, exception) -> System.out.println("Filed Count: " + state.getCount()));
  }

  public static class DataFetcherAccessConterInstrumentationState implements InstrumentationState {

    Integer count;

    private DataFetcherAccessConterInstrumentationState(Integer count) {
      this.count = count;
    }

    public Integer getCount() {
      return count;
    }

    public void setCount(Integer count) {
      this.count = count;
    }

  }
}
