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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import com.thinkenterprise.domain.AbstractEntity;
import com.thinkenterprise.domain.route.Flight;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Employee extends AbstractEntity {

  @ManyToOne
  private Flight flight;
  private String lastName;
  private String firstName;
  private String staffNumber;

  @Enumerated(EnumType.STRING)
  private EmployeeRole role;

  public Employee() {
    super();
  }

  public Employee(String staffNumber) {
    super();
    this.staffNumber = staffNumber;
  }

  public Employee(String staffNumber, String firstName, String lastName) {
    super();
    this.staffNumber = staffNumber;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Employee(Long id, Flight flight, String lastName, String firstName, String staffNumber,
      EmployeeRole role) {
    super();
    this.id = id;
    this.flight = flight;
    this.lastName = lastName;
    this.firstName = firstName;
    this.staffNumber = staffNumber;
    this.role = role;
  }

  public String getStaffNumber() {
    return staffNumber;
  }

  public void setStaffNumber(String staffNumber) {
    this.staffNumber = staffNumber;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Flight getFlight() {
    return this.flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  public void setRole(EmployeeRole role) {
    this.role = role;
  }

  public EmployeeRole getRole() {
    return this.role;
  }
}

