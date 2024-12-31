package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseGroupDTO {

	private Integer ownerId;

	private Integer groupId;

	private Integer groupStatus;
	
	private Integer nextOrPrevious;
}

