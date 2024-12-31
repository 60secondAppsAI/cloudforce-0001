package com.cloudforce.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LeadSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer leadId;
	
	private String fullName;
	
	private String email;
	
	private String phoneNumber;
	
	private String status;
	
}