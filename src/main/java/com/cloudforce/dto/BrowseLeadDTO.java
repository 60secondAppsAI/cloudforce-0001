package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseLeadDTO {

	private Integer ownerId;

	private Integer leadId;

	private Integer leadStatus;
	
	private Integer nextOrPrevious;
}

