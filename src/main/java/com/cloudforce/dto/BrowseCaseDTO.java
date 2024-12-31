package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseCaseDTO {

	private Integer ownerId;

	private Integer caseId;

	private Integer caseStatus;
	
	private Integer nextOrPrevious;
}

