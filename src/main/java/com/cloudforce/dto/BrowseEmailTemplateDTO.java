package com.cloudforce.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseEmailTemplateDTO {

	private Integer ownerId;

	private Integer emailTemplateId;

	private Integer emailTemplateStatus;
	
	private Integer nextOrPrevious;
}

