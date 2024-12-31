package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseLoginHistoryDTO {

	private Integer ownerId;

	private Integer loginHistoryId;

	private Integer loginHistoryStatus;
	
	private Integer nextOrPrevious;
}

