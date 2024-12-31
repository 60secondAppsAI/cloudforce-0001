package com.cloudforce.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EventSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer eventId;
	
	private String title;
	
	private String location;
	
	private Date startDate;
	
	private Date endDate;
	
}
