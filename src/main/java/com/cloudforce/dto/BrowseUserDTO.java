package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseUserDTO {

	private Integer ownerId;

	private Integer userId;

	private Integer userStatus;
	
	private Integer nextOrPrevious;
}

