package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseCampaignDTO {

	private Integer ownerId;

	private Integer campaignId;

	private Integer campaignStatus;
	
	private Integer nextOrPrevious;
}

