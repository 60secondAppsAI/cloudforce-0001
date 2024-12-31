package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePermissionDTO {

	private Integer ownerId;

	private Integer permissionId;

	private Integer permissionStatus;
	
	private Integer nextOrPrevious;
}

