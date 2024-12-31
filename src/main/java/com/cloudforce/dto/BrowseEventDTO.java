package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseEventDTO {

	private Integer ownerId;

	private Integer eventId;

	private Integer eventStatus;
	
	private Integer nextOrPrevious;
}

