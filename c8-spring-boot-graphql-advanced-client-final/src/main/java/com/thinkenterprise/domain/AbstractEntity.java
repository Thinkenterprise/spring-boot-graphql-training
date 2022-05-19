package com.thinkenterprise.domain;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2022 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/


public class AbstractEntity {
	
	private Long id;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}

