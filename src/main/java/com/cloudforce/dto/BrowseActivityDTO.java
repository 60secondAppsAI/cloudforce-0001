package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseActivityDTO {

	private Integer ownerId;

	private Integer activityId;

	private Integer activityStatus;
	
	private Integer nextOrPrevious;
}

