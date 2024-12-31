package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTaskDTO {

	private Integer ownerId;

	private Integer taskId;

	private Integer taskStatus;
	
	private Integer nextOrPrevious;
}

