/*
 * Spring for GraphQL Training -- Examples of using Spring for GraphQL
 * Copyright (c) 2021-2022 Michael Schäfer <spring.thinkenterprise@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thinkenterprise.domain.employee;

import com.thinkenterprise.domain.route.Flight;

public class Attendant extends Employee {

	private int rank;

	public Attendant(Long id, Flight flight, String lastName, String firstName, String staffNumber, EmployeeRole role, int rank) {
		super(id, flight, lastName, firstName, staffNumber, role);
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}