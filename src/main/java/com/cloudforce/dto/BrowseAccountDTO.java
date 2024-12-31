package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseAccountDTO {

	private Integer ownerId;

	private Integer accountId;

	private Integer accountStatus;
	
	private Integer nextOrPrevious;
}

