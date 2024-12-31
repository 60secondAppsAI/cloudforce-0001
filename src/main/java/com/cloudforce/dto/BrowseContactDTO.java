package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseContactDTO {

	private Integer ownerId;

	private Integer contactId;

	private Integer contactStatus;
	
	private Integer nextOrPrevious;
}

