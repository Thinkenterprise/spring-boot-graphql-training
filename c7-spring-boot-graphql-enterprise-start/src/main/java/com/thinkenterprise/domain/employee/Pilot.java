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

package com.thinkenterprise.domain.employee;

import javax.persistence.Entity;
import com.thinkenterprise.domain.route.Flight;

@Entity
public class Pilot extends Employee {

  String certificateNumber;

  public Pilot(Long id, Flight flight, String lastName, String firstName, String staffNumber,
      EmployeeRole role, String certificateNumber) {
    super(id, flight, lastName, firstName, staffNumber, role);
    this.certificateNumber = certificateNumber;
  }

  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  public String getCertificateNumber() {
    return this.certificateNumber;
  }
}
