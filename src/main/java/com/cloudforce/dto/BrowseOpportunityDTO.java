package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseOpportunityDTO {

	private Integer ownerId;

	private Integer opportunityId;

	private Integer opportunityStatus;
	
	private Integer nextOrPrevious;
}

