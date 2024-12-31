package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseRoleDTO {

	private Integer ownerId;

	private Integer roleId;

	private Integer roleStatus;
	
	private Integer nextOrPrevious;
}

